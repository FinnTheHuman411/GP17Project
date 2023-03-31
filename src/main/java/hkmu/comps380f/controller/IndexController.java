package hkmu.comps380f.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/index")
    public String dynamicindex(ModelMap map) {
        map.addAttribute("hello", "Welcome to COMPS380F Spring Lecture !");
        return "myindex";
    }
}

