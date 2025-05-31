package servlet;

import model.Report;
import repository.ReportRepository;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/verifyEnvelope")
public class EnvelopeVerifyServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// 실제 검증 로직 자리 - 간단 예시로 통과 처리
	private String verifyEnvelope(byte[] envelope) {
		// TODO: 실제 전자봉투 서명 검증 로직 작성
		return "서명 검증 성공";
	}

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

		String verificationResult = verifyEnvelope(report.getEncryptedEnvelope());

		// 검증 성공 시 상태 변경
		if ("서명 검증 성공".equals(verificationResult)) {
			report.setVerified(true); 
			ReportRepository.getInstance().updateReport(report); // 저장소에 반영
		}

		req.setAttribute("verificationResult", verificationResult);
		req.setAttribute("reportContent", report.getReportContent());
		req.setAttribute("reportStatus", report.isVerified() ? "검증 완료" : "검증 전");

		req.getRequestDispatcher("envelopeDetail.jsp").forward(req, resp);
	}
}
