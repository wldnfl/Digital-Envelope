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

	private static final String UNIQUE_CODE = "uniqueCode";

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// 사용자가 입력한 고유 코드 조회 및 검증
			String uniqueCode = req.getParameter(UNIQUE_CODE);
			validateUniqueCode(uniqueCode);
			// 고유 코드로 신고 내역 조회
			Report report = getReportByUniqueCode(uniqueCode);

			// 신고 내역의 검증 상태에 따라 결과 메시지 설정
			req.setAttribute("reportStatus", report.isVerified() ? "검증 완료" : "검증 전");
		} catch (UniqueCodeEmptyException | ReportNotFoundException e) {
			req.setAttribute("message", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("message", "알 수 없는 오류가 발생했습니다: " + e.getMessage());
		}

		req.getRequestDispatcher("statusResult.jsp").forward(req, resp);
	}

	// 고유 코드가 null or 빈 문자열인 경우 예외 발생
	private void validateUniqueCode(String uniqueCode) {
		if (uniqueCode == null || uniqueCode.trim().isEmpty()) {
			throw new UniqueCodeEmptyException();
		}
	}

	// Repository에서 고유 코드로 신고 내역 조회, 없으면 예외 발생
	private Report getReportByUniqueCode(String uniqueCode) {
		Report report = ReportRepository.getInstance().getReport(uniqueCode);
		if (report == null) {
			throw new ReportNotFoundException();
		}
		return report;
	}
}
