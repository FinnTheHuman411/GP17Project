package hkmu.comps380f.controller;

import hkmu.comps380f.dao.PhotoService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/photo")
public class PhotoController {
    @Resource
    private PhotoService pService;

    @GetMapping(value = {"", "/list"})
    public String list(ModelMap model) {
        model.addAttribute("photoDatabase", pService.getPhotos());
        model.addAttribute("attchmentDatabase", pService.getAttachments());
        return "list";
    }

    @GetMapping("/upload")
    public ModelAndView create() {
        return new ModelAndView("upload", "photoForm", new Form());
    }

    public static class Form {
        private String title;
        private String description;
        private List<MultipartFile> attachments;

        // Getters and Setters of customerName, subject, body, attachments
        public String getTitle() {
            return title;
        }

        public void setTitle(String subject) {
            this.title = subject;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String body) {
            this.description = body;
        }

        public List<MultipartFile> getAttachments() {
            return attachments;
        }

        public void setAttachments(List<MultipartFile> attachments) {
            this.attachments = attachments;
        }
    }

    @PostMapping("/upload")
    public View create(Form form, Principal principal) throws IOException {
        long photoId = pService.createPhoto(principal.getName(),
                form.getTitle(), form.getDescription(), form.getAttachments());
        return new RedirectView("/photo/view/" + photoId, true);
    }
}
