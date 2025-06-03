package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import util.KeyManager;
import util.UserType;
import exception.KeyCreateException;
import exception.KeyDeleteException;

import java.io.IOException;

@WebServlet("/keyManagement")
public class KeyManagementServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private enum Action {
		CREATE("create"), DELETE("delete");

		private final String value;

		Action(String value) {
			this.value = value;
		}

		public static Action fromString(String value) {
			for (Action action : Action.values()) {
				if (action.value.equalsIgnoreCase(value)) {
					return action;
				}
			}
			return null;
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp, null);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String actionParam = req.getParameter("action");
		Action action = Action.fromString(actionParam);
		processRequest(req, resp, action);
	}

	private void processRequest(HttpServletRequest req, HttpServletResponse resp, Action action)
			throws ServletException, IOException {
		String typeParam = req.getParameter("type");
		if (typeParam == null) {
			req.setAttribute("keyStatus", "UserType을 지정해주세요.");
			req.getRequestDispatcher("/keyManagement.jsp").forward(req, resp);
			return;
		}

		UserType userType;
		try {
			userType = UserType.valueOf(typeParam.toUpperCase());
		} catch (IllegalArgumentException e) {
			req.setAttribute("keyStatus", "유효하지 않은 UserType입니다.");
			req.getRequestDispatcher("/keyManagement.jsp").forward(req, resp);
			return;
		}

		KeyManager keyManager = new KeyManager(getServletContext(), userType);

		if (action != null) {
			try {
				switch (action) {
				case CREATE:
					keyManager.createKeyPair();
					req.setAttribute("keyStatus", userType + "용 새 키를 성공적으로 생성했습니다.");
					break;
				case DELETE:
					keyManager.deleteKeyPair();
					req.setAttribute("keyStatus", userType + "용 키가 삭제되었습니다.");
					break;
				}
			} catch (KeyCreateException | KeyDeleteException e) {
				req.setAttribute("keyStatus", e.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				req.setAttribute("keyStatus", "알 수 없는 오류가 발생했습니다: " + e.getMessage());
			}
		} else if (action == null && req.getMethod().equalsIgnoreCase("GET")) {
			req.setAttribute("keyStatus", keyManager.getKeyStatus());
		} else if (action == null && req.getMethod().equalsIgnoreCase("POST")) {
			req.setAttribute("keyStatus", "알 수 없는 작업입니다.");
		}

		req.getRequestDispatcher("/keyManagement.jsp").forward(req, resp);
	}
}
