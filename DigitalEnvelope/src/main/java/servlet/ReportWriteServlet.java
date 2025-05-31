package servlet;

import model.Report;
import repository.ReportRepository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/reportWrite")
public class ReportWriteServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// 간단히 고유 코드 생성 (UUID)
	private String generateUniqueCode() {
		return UUID.randomUUID().toString().substring(0, 8); // 8자리 코드
	}

	// 간단 전자봉투 생성 예시 (원문 내용을 바이트 배열로 변환)
	private byte[] createEnvelope(String reportContent) {
		// 실제론 암호화 등 해야 하지만 여기선 단순 바이트 변환으로 대체
		return reportContent.getBytes(StandardCharsets.UTF_8);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String reportContent = req.getParameter("reportContent");

		if (reportContent == null || reportContent.trim().isEmpty()) {
			req.setAttribute("error", "신고 내용을 입력해주세요.");
			req.getRequestDispatcher("reportWrite.jsp").forward(req, resp);
			return;
		}

		String uniqueCode = generateUniqueCode();
		byte[] envelope = createEnvelope(reportContent);

		Report report = new Report(uniqueCode, reportContent, envelope);
		ReportRepository.getInstance().saveReport(report);

		req.setAttribute("uniqueCode", uniqueCode);
		req.getRequestDispatcher("reportSuccess.jsp").forward(req, resp);
	}

	// 신고 작성 폼 요청 시 (GET)
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("reportWrite.jsp").forward(req, resp);
	}
}
