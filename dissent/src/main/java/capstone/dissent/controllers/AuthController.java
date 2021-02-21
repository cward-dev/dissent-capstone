package capstone.dissent.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "security/login";
    }

    @GetMapping("/login-err")
    public String loginErr(Model model) {
        model.addAttribute("loginError", true);
        return "security/login";
    }

}
