package bisosad.kt2.HTTP_Session;

import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @ModelAttribute("httpSession")
    public HttpSession addHttpSession(HttpSession httpSession) {
        return httpSession;
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**") // Áp dụng cho tất cả endpoint
                .excludePathPatterns("/login", "/register", "/css/**", "/js/**", "/images/**"); // Loại trừ các URL không cần kiểm tra
    }
}

