package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.DigitalEnvelope;
import model.Report;
import repository.ReportRepository;
import util.*;

import javax.crypto.SecretKey;
import exception.EmptyReportContentException;
import exception.KeyNotExistException;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;

@WebServlet("/reportWrite")
public class ReportWriteServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private String generateUniqueCode() {
		return java.util.UUID.randomUUID().toString().substring(0, 8);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String reportContent = req.getParameter("reportContent");

		try {
			if (reportContent == null || reportContent.trim().isEmpty()) {
				throw new EmptyReportContentException();
			}

			KeyManager keyManager = new KeyManager(getServletContext());

			if (!keyManager.isKeyPairExist()) {
				throw new KeyNotExistException();
			}

			KeyPair keyPair = keyManager.loadKeyPair();

			SecretKeyManager secretKeyManager = new SecretKeyManager(getServletContext());
			SecretKey secretKey = secretKeyManager.getOrCreateKey();

			DigitalEnvelope envelope = EnvelopeUtil.createEnvelope(reportContent.getBytes(StandardCharsets.UTF_8),
					keyPair.getPublic(), secretKey);

			String encryptedDocumentBase64 = envelope.getEncryptedDocumentBase64();
			String encryptedSecretKeyBase64 = envelope.getEncryptedSecretKeyBase64();

			String uniqueCode = generateUniqueCode();

			byte[] signature = SignatureUtil.sign(reportContent.getBytes(StandardCharsets.UTF_8), keyPair.getPrivate());
			String signatureBase64 = Base64Util.encode(signature);

			Report report = new Report(uniqueCode, reportContent, encryptedDocumentBase64, encryptedSecretKeyBase64,
					signatureBase64);
			ReportRepository.getInstance().saveReport(report);

			req.setAttribute("uniqueCode", uniqueCode);
			req.getRequestDispatcher("/reportSuccess.jsp").forward(req, resp);

		} catch (EmptyReportContentException e) {
			req.setAttribute("error", e.getMessage());
			req.getRequestDispatcher("/reportWrite.jsp").forward(req, resp);
		} catch (KeyNotExistException e) {
			resp.setContentType("text/html; charset=UTF-8");
			PrintWriter out = resp.getWriter();
			out.println("<script>alert('" + e.getMessage() + "'); history.back();</script>");
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("error", "전자봉투 생성 중 오류가 발생했습니다: " + e.getMessage());
			req.getRequestDispatcher("/reportWrite.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		KeyManager keyManager = new KeyManager(getServletContext());
		if (!keyManager.isKeyPairExist()) {
			resp.setContentType("text/html; charset=UTF-8");
			PrintWriter out = resp.getWriter();
			out.println("<script>alert('키가 없습니다. 키를 먼저 생성해주세요.'); history.back();</script>");
			out.flush();
			return;
		}
		req.getRequestDispatcher("/reportWrite.jsp").forward(req, resp);
	}
}
