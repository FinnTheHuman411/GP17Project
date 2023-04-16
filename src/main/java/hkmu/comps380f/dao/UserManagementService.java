package hkmu.comps380f.dao;


import hkmu.comps380f.exception.UserNotFound;
import hkmu.comps380f.model.ImageUser;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserManagementService {
    @Resource
    private ImageUserRepository puRepo;

    @Transactional
    public List<ImageUser> getPhotoUsers() {
        return puRepo.findAll();
    }

    @Transactional
    public ImageUser getPhotoUser(String name)
            throws UserNotFound {
        ImageUser user = puRepo.findById(name).orElse(null);
        if (user == null) {
            throw new UserNotFound(name);
        }
        return user;
    }

    @Transactional
    public void delete(String username) {
        ImageUser imageUser = puRepo.findById(username).orElse(null);
        if (imageUser == null) {
            throw new UsernameNotFoundException("User '" + username + "' not found.");
        }
        puRepo.delete(imageUser);
    }

    @Transactional
    public void createPhotoUser(String username, String password, String[] roles, String phone, String email, String description) {
        ImageUser user = new ImageUser(username, password, roles, phone, email, description);
        puRepo.save(user);
    }
}