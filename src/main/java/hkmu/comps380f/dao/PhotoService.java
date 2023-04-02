package hkmu.comps380f.dao;

import hkmu.comps380f.exception.PhotoNotFound;
import hkmu.comps380f.model.Attachment;
import hkmu.comps380f.model.Photo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class PhotoService {
    @Resource
    private PhotoRepository pRepo;

    @Resource
    private AttachmentRepository aRepo;

    @Transactional
    public List<Photo> getPhotos() {
        return pRepo.findAll();
    }

    @Transactional
    public List<Attachment> getAttachments() {
        return aRepo.findAll();
    }

    @Transactional
    public long createPhoto(String uploaderName, String title,
                             String description, List<MultipartFile> attachments)
            throws IOException {
        Photo photo = new Photo();
        photo.setUploaderName(uploaderName);
        photo.setTitle(title);
        photo.setDescription(description);

        for (MultipartFile filePart : attachments) {
            Attachment attachment = new Attachment();
            attachment.setName(filePart.getOriginalFilename());
            attachment.setMimeContentType(filePart.getContentType());
            attachment.setContents(filePart.getBytes());
            attachment.setPhoto(photo);
            if (attachment.getName() != null && attachment.getName().length() > 0
                    && attachment.getContents() != null
                    && attachment.getContents().length > 0) {
                photo.getAttachments().add(attachment);
            }
        }
        Photo savedPhoto = pRepo.save(photo);
        return savedPhoto.getId();
    }
}
