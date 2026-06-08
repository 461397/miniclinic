package tw.edu.fju.miniclinic.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginRequiredInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String loggedInDoctorId = (String) session.getAttribute("loggedInDoctorId");

        if (loggedInDoctorId != null) {
            return true; // 已登入，放行
        }

        // 未登入，判斷是 API 還是網頁請求
        if (request.getRequestURI().startsWith("/api/")) {
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"error\":\"請先登入\"}");
        } else {
            response.sendRedirect("/login");
        }
        return false;
    }
}