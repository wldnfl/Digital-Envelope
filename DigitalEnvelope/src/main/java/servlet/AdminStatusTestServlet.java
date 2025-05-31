package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Report;
import repository.ReportRepository;

import java.io.IOException;
import java.util.Collection;

@WebServlet("/adminStatusTest")
public class AdminStatusTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Collection<Report> reports = ReportRepository.getInstance().getAllReports();
		req.setAttribute("reports", reports);
		req.getRequestDispatcher("/adminStatusTest.jsp").forward(req, resp);
	}
}
