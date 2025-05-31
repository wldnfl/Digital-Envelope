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
		System.out.println("[VerifyReportServlet] uniqueCode=" + uniqueCode);

		if (uniqueCode != null) {
			ReportRepository repo = ReportRepository.getInstance();
			Report report = repo.getReport(uniqueCode);
			if (report != null) {
				report.setVerified(true);
				System.out.println("[VerifyReportServlet] 상태 변경 완료: 검증 완료");
			} else {
				System.out.println("[VerifyReportServlet] 해당 코드의 신고 없음");
			}
		} else {
			System.out.println("[VerifyReportServlet] uniqueCode가 null");
		}

		resp.sendRedirect(req.getContextPath() + "/adminStatusTest");
	}

}
