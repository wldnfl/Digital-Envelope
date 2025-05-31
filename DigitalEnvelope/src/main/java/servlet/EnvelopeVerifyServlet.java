package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.Report;
import repository.ReportRepository;
import util.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;

@WebServlet("/verifyEnvelope")
public class EnvelopeVerifyServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uniqueCode = req.getParameter("uniqueCode");

		if (uniqueCode == null || uniqueCode.trim().isEmpty()) {
			req.setAttribute("verificationResult", "고유 코드가 입력되지 않았습니다.");
			req.getRequestDispatcher("envelopeDetail.jsp").forward(req, resp);
			return;
		}

		Report report = ReportRepository.getInstance().getReport(uniqueCode);

		if (report == null) {
			req.setAttribute("verificationResult", "해당 고유 코드로 전자봉투를 찾을 수 없습니다.");
			req.getRequestDispatcher("envelopeDetail.jsp").forward(req, resp);
			return;
		}

		try {
			KeyManager keyManager = new KeyManager(getServletContext());
			KeyPair keyPair = keyManager.getOrCreateKeyPair();

			// Envelope 객체 복원
			EnvelopeUtil.DigitalEnvelope envelope = EnvelopeUtil.DigitalEnvelope
					.fromBase64(report.getEncryptedDocumentBase64(), report.getEncryptedSecretKeyBase64());

			// 복호화 시도
			byte[] decryptedBytes = EnvelopeUtil.decryptEnvelope(envelope, keyPair.getPrivate());
			String decryptedReportContent = new String(decryptedBytes, StandardCharsets.UTF_8);

			// 원본 내용과 비교하여 검증 (단순 문자열 비교)
			if (decryptedReportContent.equals(report.getReportContent())) {
				report.setVerified(true);
				ReportRepository.getInstance().updateReport(report);
				req.setAttribute("verificationResult", "전자봉투 검증 성공");
			} else {
				req.setAttribute("verificationResult", "전자봉투 검증 실패: 내용 불일치");
			}

		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("verificationResult", "전자봉투 검증 중 오류: " + e.getMessage());
		}

		req.setAttribute("report", report);
		req.getRequestDispatcher("envelopeDetail.jsp").forward(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("envelopeDetail.jsp").forward(req, resp);
	}
}
