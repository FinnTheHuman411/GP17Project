package hkmu.comps380f.controller;

import hkmu.comps380f.dao.ImageService;
import hkmu.comps380f.dao.UserManagementService;
import hkmu.comps380f.exception.CommentNotFound;
import hkmu.comps380f.exception.ImageNotFound;
import hkmu.comps380f.exception.UserNotFound;
import hkmu.comps380f.model.Comment;
import hkmu.comps380f.model.Image;
import hkmu.comps380f.model.ImageUser;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
public class MainController {
    @Resource
    UserManagementService umService;
    @Resource
    ImageService pService;
    @GetMapping("/")
    public String homepage() {
        return "redirect:/homepage";
    }

    @GetMapping(value = {"", "/homepage", "/index","/list"})
    public String list(ModelMap model) {
        model.addAttribute("photoDatabase", pService.getImages());
        model.addAttribute("attchmentDatabase", pService.getAttachments());
        return "list";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public ModelAndView register() {return new ModelAndView("register", "imageUser", new UserManagementController.Form());}

    @PostMapping("/register")
    public String register(UserManagementController.Form form) throws IOException {
        String[] userRole = {"ROLE_USER"};
        umService.createPhotoUser(form.getUsername(),
                form.getPassword(), userRole, form.getPhone(), form.getEmail(), "Hello World!");
        return "redirect:/login";
    }


    @GetMapping("/myprofile")
    public String myProfile(Principal principal, HttpServletRequest request) {
        if ((!(request.isUserInRole("ROLE_ADMIN") || request.isUserInRole("ROLE_USER")))) {
            return "redirect:/";
        }
        return "redirect:/profile/" + principal.getName();
    }

    @GetMapping("/profile/{username}")
    public String view(@PathVariable("username") String username, ModelMap model)
            throws UserNotFound, IOException {
        ImageUser user = umService.getPhotoUser(username);
        List<Comment> comments = pService.getCommentsByUser(username);
        model.addAttribute("username", username);
        model.addAttribute("user", user);
        model.addAttribute("photoDatabase", pService.getImagesByUsername(username));
        model.addAttribute("comment", comments);
        return "userProfile";
    }

    public static class descForm{
        public String description;
        public String getDescription(){ return description; }
        public void setDescription(String description){ this.description = description; }
    }
    @GetMapping("/profile/{username}/editdesc")
    public ModelAndView editDesc(@PathVariable("username") String username,
                                 Principal principal, HttpServletRequest request) {
        if ((!request.isUserInRole("ROLE_ADMIN")
                && !principal.getName().equals(username))) {
            return new ModelAndView(new RedirectView("/", true));
        }
        return new ModelAndView("updateDesc", "descForm", new descForm());
    }

    @PostMapping("/profile/{username}/editdesc")
    public View description(@PathVariable("username") String username, descForm form, Principal principal)throws IOException {
        umService.updateDescription(username, form.getDescription());
        return new RedirectView("/profile/" + username,true);
    }

    @GetMapping("/view/{imageId}")
    public String view(@PathVariable("imageId") long imageId,
                       ModelMap model)
            throws ImageNotFound {
        Image image = pService.getImage(imageId);
        List<Comment> comments = pService.getComments(imageId);
        model.addAttribute("imageId", imageId);
        model.addAttribute("image", image);
        model.addAttribute("comment", comments);
        return "view";
    }

    public static class commentForm{
        public String text;
        public String getText(){ return text; }
        public void setText(String text){ this.text = text; }
    }
    @GetMapping("/view/{imageId}/comment")
    public ModelAndView addComment(HttpServletRequest request)
            throws IOException{
        if ((!(request.isUserInRole("ROLE_ADMIN") || request.isUserInRole("ROLE_USER")))) {
            return new ModelAndView(new RedirectView("/", true));
        }
        return new ModelAndView("addComments", "commentForm", new commentForm());
    }
    @PostMapping("/view/{imageId}/comment")
    public String comment(@PathVariable("imageId") long imageId, commentForm commentForm,
                          Principal principal, HttpServletRequest request)
            throws IOException{
        pService.createComment(principal.getName(), commentForm.getText(), imageId);
        return "redirect:/view/" + imageId;
    }

    @GetMapping("/view/{imageId}/editComment/{commentId}")
    public ModelAndView editComment(@PathVariable("commentId") long commentId, HttpServletRequest request, Principal principal)
            throws CommentNotFound{
        Comment comment = pService.getComment(commentId);
        if (!request.isUserInRole("ROLE_ADMIN") && !principal.getName().equals(comment.getUsername())) {
            return new ModelAndView(new RedirectView("/", true));
        }
        return new ModelAndView("addComments", "commentForm", new commentForm());
    }
    @PostMapping("/view/{imageId}/editComment/{commentId}")
    public String comment(@PathVariable("imageId") long imageId, @PathVariable("commentId") long commentId, commentForm commentForm,
                          Principal principal, HttpServletRequest request)
            throws IOException{
        pService.editComment(commentId, commentForm.getText());
        return "redirect:/view/" + imageId;
    }

    @GetMapping("/view/{imageId}/deleteComment/{commentId}")
    public String deleteComment(@PathVariable("imageId") long imageId, HttpServletRequest request,
                                @PathVariable("commentId")long commentId, Principal principal)
            throws CommentNotFound {
        Comment comment = pService.getComment(commentId);
        if (!request.isUserInRole("ROLE_ADMIN") && !principal.getName().equals(comment.getUsername())) {
            return "redirect:/";
        }
        pService.deleteComment(commentId);
        return "redirect:/view/" + imageId;
    }
}

