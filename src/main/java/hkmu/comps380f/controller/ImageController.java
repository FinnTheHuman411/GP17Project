package hkmu.comps380f.controller;

import hkmu.comps380f.dao.PhotoService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/photo")
public class ImageController {
    @Resource
    private PhotoService pService;

    @GetMapping(value = {"", "/list"})
    public String list(ModelMap model) {
        //model.addAttribute("photoDatabase", pService.getPhotos());
        return "list";
    }
}
