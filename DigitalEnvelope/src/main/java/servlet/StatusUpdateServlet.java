//package servlet;
//
//import model.Report;
//import repository.ReportRepository;
//
//import java.io.IOException;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.*;
//
//@WebServlet("/updateStatus")
//public class StatusUpdateServlet extends HttpServlet {
//
//	private static final long serialVersionUID = 1L;
//
//	@Override
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		String uniqueCode = req.getParameter("uniqueCode");
//		String newStatus = req.getParameter("newStatus");
//
//		if (uniqueCode == null || uniqueCode.trim().isEmpty() || newStatus == null || newStatus.trim().isEmpty()) {
//			req.setAttribute("message", "필수 입력값이 누락되었습니다.");
//			req.getRequestDispatcher("statusUpdate.jsp").forward(req, resp);
//			return;
//		}
//
//		Report report = ReportRepository.getInstance().getReport(uniqueCode);
//
//		if (report == null) {
//			req.setAttribute("message", "해당 고유 코드로 신고를 찾을 수 없습니다.");
//			req.getRequestDispatcher("statusUpdate.jsp").forward(req, resp);
//			return;
//		}
//
//		ReportRepository.getInstance().updateStatus(uniqueCode, newStatus);
//
//		// 변경 후 다시 상태 변경 페이지 보여주기
//		req.setAttribute("uniqueCode", uniqueCode);
//		req.setAttribute("currentStatus", newStatus);
//		req.setAttribute("message", "상태가 성공적으로 변경되었습니다.");
//
//		req.getRequestDispatcher("statusUpdate.jsp").forward(req, resp);
//	}
//
//	// 상태 변경 페이지 진입 시 고유코드 파라미터로 상태 읽어오기(GET)
//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		String uniqueCode = req.getParameter("uniqueCode");
//
//		if (uniqueCode == null || uniqueCode.trim().isEmpty()) {
//			req.setAttribute("message", "고유 코드를 입력해주세요.");
//			req.getRequestDispatcher("statusUpdate.jsp").forward(req, resp);
//			return;
//		}
//
//		Report report = ReportRepository.getInstance().getReport(uniqueCode);
//
//		if (report == null) {
//			req.setAttribute("message", "해당 고유 코드로 신고를 찾을 수 없습니다.");
//			req.getRequestDispatcher("statusUpdate.jsp").forward(req, resp);
//			return;
//		}
//
//		req.setAttribute("uniqueCode", uniqueCode);
//		req.setAttribute("currentStatus", report.getStatus());
//		req.getRequestDispatcher("statusUpdate.jsp").forward(req, resp);
//	}
//}
