package hkmu.comps380f.controller;

import hkmu.comps380f.dao.ImageService;
import hkmu.comps380f.exception.AttachmentNotFound;
import hkmu.comps380f.exception.CommentNotFound;
import hkmu.comps380f.exception.ImageNotFound;
import hkmu.comps380f.model.Comment;
import hkmu.comps380f.model.Image;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/image")
public class ImageController {
    @Resource
    private ImageService pService;

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

        public void setDescription(String description) {
            this.description = description;
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
        long photoId = pService.createImage(principal.getName(),
                form.getTitle(), form.getDescription(), form.getAttachments());
        return new RedirectView("/view/" + photoId, true);
    }

    @GetMapping("/delete/{imageId}")
    public String deleteImage(@PathVariable("imageId") long imageId)
            throws ImageNotFound {
        pService.delete(imageId);
        return "redirect:/";
    }

    @GetMapping("/{imageId}/delete/{attachment:.+}")
    public String deleteAttachment(@PathVariable("imageId") long imageId,
                                   @PathVariable("attachment") UUID attachmentId)
            throws ImageNotFound, AttachmentNotFound {
        pService.deleteAttachment(imageId, attachmentId);
        return "redirect:/view/" + imageId;
    }

    @GetMapping("/edit/{imageId}")
    public ModelAndView showEdit(@PathVariable("imageId") long imageId,
                                 Principal principal, HttpServletRequest request)
            throws ImageNotFound {
        Image image = pService.getImage(imageId);
        if (image == null
                || (!request.isUserInRole("ROLE_ADMIN")
                && !principal.getName().equals(image.getUsername()))) {
            return new ModelAndView(new RedirectView("/", true));
        }

        ModelAndView modelAndView = new ModelAndView("update");
        modelAndView.addObject("image", image);

        Form photoForm = new Form();
        photoForm.setTitle(image.getTitle());
        photoForm.setDescription(image.getDescription());
        modelAndView.addObject("photoForm", photoForm);

        return modelAndView;
    }

    @PostMapping("/edit/{imageId}")
    public String edit(@PathVariable("imageId") long imageId, Form form,
                       Principal principal, HttpServletRequest request)
            throws IOException, ImageNotFound {
        Image image = pService.getImage(imageId);
        if (image == null
                || (!request.isUserInRole("ROLE_ADMIN")
                && !principal.getName().equals(image.getUsername()))) {
            return "redirect:/ticket/list";
        }

        pService.updateImage(imageId, form.getTitle(),
                form.getDescription(), form.getAttachments());
        return "redirect:/view/" + imageId;
    }
}
