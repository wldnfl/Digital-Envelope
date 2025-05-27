package servlet;

import java.io.File;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import util.KeyManager;

@WebServlet("/generateKey")
public class KeyGenerateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String keyFolderPath = getServletContext().getRealPath("/WEB-INF/keys");
		File keyFolder = new File(keyFolderPath);
		if (!keyFolder.exists())
			keyFolder.mkdirs();

		try {
			KeyManager.generateRSAKeyPair(keyFolderPath);
			req.setAttribute("message", "RSA 키 생성 완료.");
		} catch (Exception e) {
			req.setAttribute("message", "키 생성 실패: " + e.getMessage());
		}
		req.getRequestDispatcher("reportResult.jsp").forward(req, resp);
	}
}