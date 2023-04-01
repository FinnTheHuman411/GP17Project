package hkmu.comps380f.controller;


import hkmu.comps380f.dao.UserManagementService;
import hkmu.comps380f.exception.UserNotFound;
import hkmu.comps380f.model.PhotoUser;
import jakarta.annotation.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
@RequestMapping("/user")
public class UserManagementController {
    @Resource
    UserManagementService umService;

    @GetMapping({"", "/", "/list"})
    public String list(ModelMap model) {
        model.addAttribute("photoUsers", umService.getPhotoUsers());
        return "listUser";
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

    @GetMapping("/create")
    public ModelAndView create() {
        return new ModelAndView("addUser", "photoUser", new Form());
    }

    @PostMapping("/create")
    public String create(Form form) throws IOException {
        umService.createPhotoUser(form.getUsername(),
                form.getPassword(), form.getRoles(), form.getPhone(), form.getEmail(), "");
        return "redirect:/user/list";
    }

    @GetMapping("/register")
    public ModelAndView register() {
        return new ModelAndView("register", "photoUser", new Form());
    }

    @PostMapping("/register")
    public String register(Form form) throws IOException {
        String[] userRole = {"ROLE_USER"};
        umService.createPhotoUser(form.getUsername(),
                form.getPassword(), userRole, form.getPhone(), form.getEmail(), "");
        return "redirect:/login";
    }

    @GetMapping("/delete/{username}")
    public String deleteTicket(@PathVariable("username") String username) {
        umService.delete(username);
        return "redirect:/user/list";
    }

    @GetMapping("/myprofile")
    public String myProfile(@CurrentSecurityContext(expression="authentication.name") String username) {
            return "redirect:/user/profile/" + username;
    }
    @GetMapping("/profile/{username}")
    public String view(@PathVariable("username") String username, ModelMap model)
            throws UserNotFound {
        PhotoUser user = umService.getPhotoUser(username);
        model.addAttribute("username", username);
        model.addAttribute("user", user);
        return "userprofile";
    }
}