package bisosad.kt2.controller;

import bisosad.kt2.model.Account;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute("loggedInUser")
    public Object addUserToModel(HttpSession session) {
        return session.getAttribute("loggedInUser"); // Lấy thông tin từ session
    }
    @ModelAttribute("loggedInUsername")
    public String addLoggedInUsernameToModel(HttpSession session) {
        Account loggedInUser = (Account) session.getAttribute("loggedInUser");
        return (loggedInUser != null) ? loggedInUser.getUsername() : null;
    }
}

