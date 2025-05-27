package servlet;

import java.io.*;
import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Envelope;

@WebServlet("/reportList")
public class ReportListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String reportsPath = getServletContext().getRealPath("/reports");
		File folder = new File(reportsPath);
		System.out.println("reports 폴더 절대 경로: " + folder.getAbsolutePath());

		File[] files = folder.listFiles((dir, name) -> name.endsWith(".dat"));
		List<Map<String, Object>> reportList = new ArrayList<>();

		if (files != null) {
			for (File file : files) {
				try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
					Envelope envelope = (Envelope) ois.readObject();
					String signature = (String) ois.readObject();
					String title = (String) ois.readObject();
					String content = (String) ois.readObject();

					Map<String, Object> report = new HashMap<>();
					String id = file.getName().replace("report_", "").replace(".dat", "");
					report.put("id", id);
					report.put("title", title);
					report.put("date", new Date(file.lastModified()));
					reportList.add(report);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		req.setAttribute("reportList", reportList);
		req.getRequestDispatcher("reportList.jsp").forward(req, resp);
	}
}