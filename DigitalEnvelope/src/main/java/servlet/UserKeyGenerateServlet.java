package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import repository.KeyRepository;
import util.CryptoUtil;

import java.io.IOException;
import java.security.KeyPair;

@WebServlet("/generateUserKey")
public class UserKeyGenerateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId").trim();

        if (userId.isEmpty()) {
            req.setAttribute("message", "사용자 식별자를 입력하세요.");
            req.getRequestDispatcher("/userKeyGenerate.jsp").forward(req, resp);
            return;
        }

        try {
            // 키 생성
            KeyPair keyPair = CryptoUtil.generateRSAKeyPair();

            // 키 저장소에 저장 (KeyRepository는 싱글톤 저장소)
            KeyRepository.getInstance().saveKeyPair(userId, keyPair);

            req.setAttribute("message", "사용자 키가 성공적으로 생성되었습니다. 사용자 ID: " + userId);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", "키 생성 중 오류가 발생했습니다: " + e.getMessage());
        }

        req.getRequestDispatcher("/generateUserKey.jsp").forward(req, resp);
    }
}
