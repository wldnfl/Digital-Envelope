package servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.KeyPair;

import javax.crypto.SecretKey;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Envelope;
import util.EnvelopeCreator;
import util.KeyManager;
import util.SignatureUtil;

@WebServlet("/reportWrite")
public class ReportWriteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String title = req.getParameter("title");
			String content = req.getParameter("content");
			String reportText = title + "\n" + content;

			String keyFolderPath = getServletContext().getRealPath("/WEB-INF/keys");

			KeyPair rsaKeys = KeyManager.loadRSAKeyPair(keyFolderPath);
			SecretKey aesKey = KeyManager.generateAESKey(keyFolderPath);

			Envelope envelope = EnvelopeCreator.createEnvelope(reportText, aesKey, rsaKeys.getPublic());
			String signature = SignatureUtil.generateSignature(reportText, rsaKeys.getPrivate());

			String reportsPath = getServletContext().getRealPath("/reports");
			File folder = new File(reportsPath);
			if (!folder.exists())
				folder.mkdirs();

			int reportId = folder.list().length + 1;
			File file = new File(folder, "report_" + reportId + ".dat");

			try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
				oos.writeObject(envelope);
				oos.writeObject(signature);
				oos.writeObject(title);
				oos.writeObject(content);
			}

			req.setAttribute("message", "신고가 안전하게 접수되었습니다.");
		} catch (Exception e) {
			req.setAttribute("message", "오류 발생: " + e.getMessage());
		}
		req.getRequestDispatcher("reportResult.jsp").forward(req, resp);
	}
}