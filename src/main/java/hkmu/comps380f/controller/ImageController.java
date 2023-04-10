package hkmu.comps380f.controller;

import hkmu.comps380f.dao.PhotoService;
import jakarta.annotation.Resource;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/image")
public class ImageController {
    @Resource
    private PhotoService pService;

    @GetMapping("/myprofile")
    public String myProfile(@CurrentSecurityContext(expression="authentication.name") String username) {
        return "redirect:/profile/" + username;
    }

    @GetMapping("/upload")
    public String create() {return "upload";}

    @PostMapping("/upload")
    public String uploadImage(ModelMap model, @RequestParam("image") MultipartFile file, @CurrentSecurityContext(expression="authentication.name") String username) throws IOException {
        pService.createImage(file.getOriginalFilename(), file.getBytes(),username);
        return "redirect:/";
    }
}
