package hkmu.comps380f.dao;

import hkmu.comps380f.model.Image;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
public class PhotoService {
    @Resource
    PhotoRepository imgRepo;

    @Transactional
    public void createImage(String filename, byte[] data, String username){
        Image image = new Image(filename, data, username);
        imgRepo.save(image);
    }

    @Transactional
    public List<Image> getPhotos(){
        return imgRepo.findAll();
    }

    @Transactional
    public List<Image> getPhoto(String username) throws IOException {
        List<Image> image = imgRepo.findByUsername(username);
        return image;
    }
}
