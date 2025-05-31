package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Report;
import repository.ReportRepository;

import java.io.IOException;

@WebServlet("/verifyReport")
public class VerifyReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uniqueCode = req.getParameter("uniqueCode");
		System.out.println("[VerifyEnvelopeServlet] uniqueCode=" + uniqueCode);

		if (uniqueCode == null || uniqueCode.trim().isEmpty()) {
			req.setAttribute("verificationResult", "코드가 없습니다.");
			req.setAttribute("reportContent", null);
			req.setAttribute("reportStatus", null);
			req.getRequestDispatcher("/verifyReport.jsp").forward(req, resp);
			return;
		}

		ReportRepository repo = ReportRepository.getInstance();
		Report report = repo.getReport(uniqueCode);

		if (report == null) {
			req.setAttribute("verificationResult", "해당 코드에 대한 신고가 없습니다.");
			req.setAttribute("reportContent", null);
			req.setAttribute("reportStatus", null);
		} else {
			// 검증 성공 처리
			report.setVerified(true); // 상태 변경

			req.setAttribute("verificationResult", "전자봉투 검증 성공");
			req.setAttribute("reportContent", report.getReportContent()); // 저장된 원문
			req.setAttribute("reportStatus", report.isVerified() ? "검증 완료" : "검증 안됨");
		}

		req.getRequestDispatcher("/verifyReport.jsp").forward(req, resp);
	}
}
