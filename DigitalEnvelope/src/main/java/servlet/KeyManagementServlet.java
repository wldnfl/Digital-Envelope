package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import util.KeyManager;

import java.io.IOException;

@WebServlet("/keyManagement")
public class KeyManagementServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		KeyManager keyManager = new KeyManager(getServletContext());
		req.setAttribute("keyStatus", keyManager.getKeyStatus());
		req.getRequestDispatcher("/keyManagement.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		KeyManager keyManager = new KeyManager(getServletContext());
		String action = req.getParameter("action");

		try {
			if ("create".equals(action)) {
				keyManager.createKeyPair();
				req.setAttribute("keyStatus", "새 키를 성공적으로 생성했습니다.");
			} else if ("delete".equals(action)) {
				keyManager.deleteKeyPair();
				req.setAttribute("keyStatus", "키가 삭제되었습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("keyStatus", "오류 발생: " + e.getMessage());
		}
		req.getRequestDispatcher("/keyManagement.jsp").forward(req, resp);
	}
}
