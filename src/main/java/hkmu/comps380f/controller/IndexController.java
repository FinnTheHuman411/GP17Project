package hkmu.comps380f.controller;

import hkmu.comps380f.dao.PhotoService;
import hkmu.comps380f.dao.UserManagementService;
import hkmu.comps380f.exception.UserNotFound;
import hkmu.comps380f.model.ImageUser;
import jakarta.annotation.Resource;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
public class IndexController {
    @Resource
    UserManagementService umService;
    @Resource
    PhotoService pService;

    @GetMapping({"/", "/index", "/list"})
    public String list(ModelMap model) {
        model.addAttribute("photoDatabase", pService.getPhotos());
        return "list";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    public static class Form {
        private String username;
        private String password;
        private String description;
        private String phone;
        private String email;
        private String[] roles;

        // getters and setters for all properties
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String[] getRoles() {
            return roles;
        }

        public void setRoles(String[] roles) {
            this.roles = roles;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    @GetMapping("/register")
    public ModelAndView register() {
        return new ModelAndView("register", "imageUser", new UserManagementController.Form());
    }

    @PostMapping("/register")
    public String register(UserManagementController.Form form) throws IOException {
        String[] userRole = {"ROLE_USER"};
        umService.createPhotoUser(form.getUsername(),
                form.getPassword(), userRole, form.getPhone(), form.getEmail(), "");
        return "redirect:/login";
    }

    @GetMapping("/profile/{username}")
    public String view(@PathVariable("username") String username, ModelMap model)
            throws UserNotFound, IOException {
        ImageUser user = umService.getPhotoUser(username);
        model.addAttribute("username", username);
        model.addAttribute("user", user);
        model.addAttribute("photoDatabase", pService.getPhoto(username));
        return "userprofile";
    }
}

