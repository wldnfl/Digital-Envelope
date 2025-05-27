package servlet;

import util.*;
import model.Envelope;
import java.io.*;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/reportVerify")
public class ReportVerifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("envelopeId");
		String reportsPath = getServletContext().getRealPath("/reports");
		File file = new File(reportsPath, "report_" + id + ".dat");

		try {
			Envelope envelope;
			String signature, title, content;

			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
				envelope = (Envelope) ois.readObject();
				signature = (String) ois.readObject();
				title = (String) ois.readObject();
				content = (String) ois.readObject();
			}

			String keyFolderPath = getServletContext().getRealPath("/WEB-INF/keys");
			KeyPair rsaKeys = KeyManager.loadRSAKeyPair(keyFolderPath);

			Cipher rsaCipher = Cipher.getInstance("RSA");
			rsaCipher.init(Cipher.DECRYPT_MODE, rsaKeys.getPrivate());
			byte[] aesKeyBytes = rsaCipher.doFinal(envelope.getEncryptedAESKey());
			SecretKey aesKey = new SecretKeySpec(aesKeyBytes, "AES");

			Cipher aesCipher = Cipher.getInstance("AES");
			aesCipher.init(Cipher.DECRYPT_MODE, aesKey);
			byte[] decryptedReportBytes = aesCipher.doFinal(envelope.getEncryptedReport());
			String decryptedReport = new String(decryptedReportBytes);

			boolean verified = SignatureUtil.verifySignature(decryptedReport, signature, rsaKeys.getPublic());

			req.setAttribute("title", title);
			req.setAttribute("content", content);
			req.setAttribute("verified", verified);
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("message", "검증 오류: " + e.getMessage());
		}

		req.getRequestDispatcher("reportVerify.jsp").forward(req, resp);
	}
}