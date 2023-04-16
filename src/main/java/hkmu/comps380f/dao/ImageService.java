package hkmu.comps380f.dao;

import hkmu.comps380f.exception.ImageNotFound;
import hkmu.comps380f.model.Attachment;
import hkmu.comps380f.model.Image;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ImageService {
    @Resource
    private ImageRepository pRepo;

    @Resource
    private AttachmentRepository aRepo;

    @Transactional
    public List<Image> getImages() {
        return pRepo.findAll();
    }
    @Transactional
    public List<Image> getImagesByUsername(String username) throws IOException {
        List<Image> image = pRepo.findByUsername(username);
        return image;
    }
    @Transactional
    public Image getImage(long id)
            throws ImageNotFound {
        Image image = pRepo.findById(id).orElse(null);
        if (image == null) {
            throw new ImageNotFound(id);
        }
        return image;
    }

    @Transactional
    public List<Attachment> getAttachments() {
        return aRepo.findAll();
    }

    @Transactional
    public long createPhoto(String username, String title,
                             String description, List<MultipartFile> attachments)
            throws IOException {
        Image image = new Image();
        image.setUsername(username);
        image.setTitle(title);
        image.setDescription(description);

        for (MultipartFile filePart : attachments) {
            Attachment attachment = new Attachment();
            attachment.setName(filePart.getOriginalFilename());
            attachment.setMimeContentType(filePart.getContentType());
            attachment.setContents(filePart.getBytes());
            attachment.setPhoto(image);
            if (attachment.getName() != null && attachment.getName().length() > 0
                    && attachment.getContents() != null
                    && attachment.getContents().length > 0) {
                image.getAttachments().add(attachment);
            }
        }
        Image savedImage = pRepo.save(image);
        return savedImage.getId();
    }
}
