package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.DigitalEnvelope;
import model.Report;
import repository.ReportRepository;
import util.*;

import exception.UniqueCodeEmptyException;
import exception.ReportNotFoundException;
import exception.EnvelopeVerificationException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;

@WebServlet("/verifyReport")
public class EnvelopeVerifyServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String UNIQUE_CODE = "uniqueCode";

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String uniqueCode = req.getParameter(UNIQUE_CODE);
			validateUniqueCode(uniqueCode);

			Report report = getReportByUniqueCode(uniqueCode);
			verifyEnvelopeAndSignature(report);

			setVerificationAttributes(req, "성공", report);

		} catch (UniqueCodeEmptyException | ReportNotFoundException e) {
			setVerificationAttributes(req, e.getMessage(), null);
		} catch (EnvelopeVerificationException e) {
			setVerificationAttributes(req, e.getMessage(), null);
		} catch (Exception e) {
			e.printStackTrace();
			setVerificationAttributes(req, "알 수 없는 오류가 발생했습니다: " + e.getMessage(), null);
		}

		forwardToPage(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		forwardToPage(req, resp);
	}

	private void forwardToPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("verifyReport.jsp").forward(req, resp);
	}

	private void setVerificationAttributes(HttpServletRequest req, String result, Report report) {
		req.setAttribute("verificationResult", result);
		req.setAttribute("reportContent",
				report != null && report.getReportContent() != null ? report.getReportContent() : "미제공");
		req.setAttribute("reportStatus", report != null ? (report.isVerified() ? "검증 완료" : "검증 안됨") : "미제공");
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

	private void verifyEnvelopeAndSignature(Report report) throws EnvelopeVerificationException {
		try {
			// 관리자 개인키 (전자봉투 복호화용)
			KeyManager adminKeyManager = new KeyManager(getServletContext(), UserType.ADMIN);
			PrivateKey adminPrivateKey = adminKeyManager.loadKeyPair().getPrivate();

			// 작성자 공개키 (전자서명 검증용)
			KeyManager writerKeyManager = new KeyManager(getServletContext(), UserType.REPORTER);
			PublicKey writerPublicKey = writerKeyManager.loadKeyPair().getPublic();

			DigitalEnvelope envelope = DigitalEnvelope.fromBase64(report.getEncryptedDocumentBase64(),
					report.getEncryptedSecretKeyBase64());

			// 복호화
			byte[] decryptedBytes = EnvelopeUtil.decryptEnvelope(envelope, adminPrivateKey);
			String decryptedReportContent = new String(decryptedBytes, StandardCharsets.UTF_8);

			// 전자봉투 내용과 원문 비교
			if (!decryptedReportContent.equals(report.getReportContent())) {
				throw new EnvelopeVerificationException();
			}

			// 전자서명 검증
			boolean validSignature = SignatureUtil.verifyFromBase64(
					decryptedReportContent.getBytes(StandardCharsets.UTF_8), report.getSignatureBase64(),
					writerPublicKey);

			if (!validSignature) {
				throw new EnvelopeVerificationException();
			}

			// 검증 성공 시 상태 저장
			report.setVerified(true);
			ReportRepository.getInstance().updateReport(report);

		} catch (Exception e) {
			throw new EnvelopeVerificationException(e);
		}
	}
}
