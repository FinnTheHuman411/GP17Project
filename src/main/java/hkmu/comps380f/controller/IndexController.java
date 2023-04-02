package hkmu.comps380f.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    public String homepage() {
        return "redirect:/image/list";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}

