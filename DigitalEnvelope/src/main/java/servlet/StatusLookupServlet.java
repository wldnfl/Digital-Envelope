package servlet;

import model.Report;
import repository.ReportRepository;

import java.io.IOException;

import exception.ReportNotFoundException;
import exception.UniqueCodeEmptyException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/lookupStatus")
public class StatusLookupServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

//	@Override
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		String uniqueCode = req.getParameter("uniqueCode");
//
//		if (uniqueCode == null || uniqueCode.trim().isEmpty()) {
//			req.setAttribute("message", "고유 코드를 입력하세요.");
//			req.getRequestDispatcher("statusResult.jsp").forward(req, resp);
//			return;
//		}
//
//		Report report = ReportRepository.getInstance().getReport(uniqueCode);
//		if (report == null) {
//			req.setAttribute("message", "해당 고유 코드로 신고를 찾을 수 없습니다.");
//			req.getRequestDispatcher("statusResult.jsp").forward(req, resp);
//			return;
//		}
//
//		req.setAttribute("reportStatus", report.isVerified() ? "검증 완료" : "검증 전");
//		req.getRequestDispatcher("statusResult.jsp").forward(req, resp);
//	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String uniqueCode = req.getParameter("uniqueCode");
			validateUniqueCode(uniqueCode);

			Report report = getReportByUniqueCode(uniqueCode);

			req.setAttribute("reportStatus", report.isVerified() ? "검증 완료" : "검증 전");
		} catch (UniqueCodeEmptyException | ReportNotFoundException e) {
			req.setAttribute("message", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("message", "알 수 없는 오류가 발생했습니다: " + e.getMessage());
		}

		req.getRequestDispatcher("statusResult.jsp").forward(req, resp);
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
