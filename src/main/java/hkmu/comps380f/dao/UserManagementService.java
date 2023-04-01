package hkmu.comps380f.dao;


import hkmu.comps380f.exception.UserNotFound;
import hkmu.comps380f.model.PhotoUser;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserManagementService {
    @Resource
    private PhotoUserRepository puRepo;

    @Transactional
    public List<PhotoUser> getPhotoUsers() {
        return puRepo.findAll();
    }

    @Transactional
    public PhotoUser getPhotoUser(String name)
            throws UserNotFound {
        PhotoUser user = puRepo.findById(name).orElse(null);
        if (user == null) {
            throw new UserNotFound(name);
        }
        return user;
    }

    @Transactional
    public void delete(String username) {
        PhotoUser photoUser = puRepo.findById(username).orElse(null);
        if (photoUser == null) {
            throw new UsernameNotFoundException("User '" + username + "' not found.");
        }
        puRepo.delete(photoUser);
    }

    @Transactional
    public void createPhotoUser(String username, String password, String[] roles, String phone, String email, String description) {
        PhotoUser user = new PhotoUser(username, password, roles, phone, email, description);
        puRepo.save(user);
    }
}