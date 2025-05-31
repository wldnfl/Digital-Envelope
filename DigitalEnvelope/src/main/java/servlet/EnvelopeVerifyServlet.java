package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.Report;
import repository.ReportRepository;
import util.*;

import exception.UniqueCodeEmptyException;
import exception.ReportNotFoundException;
import exception.EnvelopeVerificationException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;

@WebServlet("/verifyEnvelope")
public class EnvelopeVerifyServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String uniqueCode = req.getParameter("uniqueCode");
			validateUniqueCode(uniqueCode);

			Report report = getReportByUniqueCode(uniqueCode);

			verifyEnvelope(report);

			req.setAttribute("verificationResult", "전자봉투 검증 성공");
			req.setAttribute("report", report);

		} catch (UniqueCodeEmptyException | ReportNotFoundException e) {
			req.setAttribute("verificationResult", e.getMessage());
		} catch (EnvelopeVerificationException e) {
			req.setAttribute("verificationResult", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("verificationResult", "알 수 없는 오류가 발생했습니다: " + e.getMessage());
		}

		req.getRequestDispatcher("verifyReport.jsp").forward(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("verifyReport.jsp").forward(req, resp);
	}

	private void validateUniqueCode(String uniqueCode) throws UniqueCodeEmptyException {
		if (uniqueCode == null || uniqueCode.trim().isEmpty()) {
			throw new UniqueCodeEmptyException();
		}
	}

	private Report getReportByUniqueCode(String uniqueCode) throws ReportNotFoundException {
		Report report = ReportRepository.getInstance().getReport(uniqueCode);
		if (report == null) {
			throw new ReportNotFoundException();
		}
		return report;
	}

	private void verifyEnvelope(Report report) throws EnvelopeVerificationException {
		try {
			KeyManager keyManager = new KeyManager(getServletContext());
			KeyPair keyPair = keyManager.getOrCreateKeyPair();

			EnvelopeUtil.DigitalEnvelope envelope = EnvelopeUtil.DigitalEnvelope
					.fromBase64(report.getEncryptedDocumentBase64(), report.getEncryptedSecretKeyBase64());

			byte[] decryptedBytes = EnvelopeUtil.decryptEnvelope(envelope, keyPair.getPrivate());
			String decryptedReportContent = new String(decryptedBytes, StandardCharsets.UTF_8);

			if (!decryptedReportContent.equals(report.getReportContent())) {
				throw new EnvelopeVerificationException("내용 불일치");
			}

			report.setVerified(true);
			ReportRepository.getInstance().updateReport(report);

		} catch (Exception e) {
			throw new EnvelopeVerificationException(e.getMessage());
		}
	}
}
