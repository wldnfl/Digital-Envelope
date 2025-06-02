package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import util.KeyManager;
import exception.KeyCreateException;
import exception.KeyDeleteException;

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
			} else {
				req.setAttribute("keyStatus", "알 수 없는 작업입니다.");
			}
		} catch (KeyCreateException | KeyDeleteException e) {
			req.setAttribute("keyStatus", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("keyStatus", "알 수 없는 오류가 발생했습니다: " + e.getMessage());
		}

		req.getRequestDispatcher("/keyManagement.jsp").forward(req, resp);
	}

}
