package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Report;
import repository.ReportRepository;

import java.io.IOException;

import exception.ReportNotFoundException;
import exception.UniqueCodeEmptyException;

@WebServlet("/verifyReport")
public class VerifyReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String uniqueCode = req.getParameter("uniqueCode");
			validateUniqueCode(uniqueCode);

			Report report = getReportByUniqueCode(uniqueCode);

			// 검증 성공 처리
			report.setVerified(true); // 상태 변경

			req.setAttribute("verificationResult", "전자봉투 검증 성공");
			req.setAttribute("reportContent", report.getReportContent()); // 저장된 원문
			req.setAttribute("reportStatus", report.isVerified() ? "검증 완료" : "검증 안됨");

		} catch (UniqueCodeEmptyException | ReportNotFoundException e) {
			req.setAttribute("verificationResult", e.getMessage());
			req.setAttribute("reportContent", null);
			req.setAttribute("reportStatus", null);
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("verificationResult", "알 수 없는 오류가 발생했습니다: " + e.getMessage());
			req.setAttribute("reportContent", null);
			req.setAttribute("reportStatus", null);
		}
		req.getRequestDispatcher("/verifyReport.jsp").forward(req, resp);
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
}