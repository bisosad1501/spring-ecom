package bisosad.kt2.HTTP_Session;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        String uri = request.getRequestURI();

        // Loại trừ các URL không cần kiểm tra
        if (uri.equals("/accounts/login") || uri.equals("/accounts/register")) {
            return true; // Không kiểm tra với trang login hoặc đăng ký
        }

        if (session == null || session.getAttribute("loggedInUser") == null) {
            response.sendRedirect("/accounts/login"); // Redirect nếu chưa đăng nhập
            return false;
        }

        return true; // Tiếp tục xử lý nếu đã đăng nhập
    }

}

