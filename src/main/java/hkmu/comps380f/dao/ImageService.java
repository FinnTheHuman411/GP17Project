package hkmu.comps380f.dao;

import hkmu.comps380f.exception.AttachmentNotFound;
import hkmu.comps380f.exception.CommentNotFound;
import hkmu.comps380f.exception.ImageNotFound;
import hkmu.comps380f.exception.UserNotFound;
import hkmu.comps380f.model.Attachment;
import hkmu.comps380f.model.Comment;
import hkmu.comps380f.model.Image;
import hkmu.comps380f.model.ImageUser;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class ImageService {
    @Resource
    private ImageRepository pRepo;
    @Resource
    private ImageUserRepository puRepo;
    @Resource
    private CommentRepository cRepo;

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

    @Transactional(rollbackFor = ImageNotFound.class)
    public void delete(long id) throws ImageNotFound {
        Image deletedImage = pRepo.findById(id).orElse(null);
        if (deletedImage == null) {
            throw new ImageNotFound(id);
        }
        pRepo.delete(deletedImage);
    }

    @Transactional(rollbackFor = AttachmentNotFound.class)
    public void deleteAttachment(long imageId, UUID attachmentId)
            throws ImageNotFound, AttachmentNotFound {
        Image image = pRepo.findById(imageId).orElse(null);
        if (image == null) {
            throw new ImageNotFound(imageId);
        }
        if (image.getAttachments().size() > 1){
            for (Attachment attachment : image.getAttachments()) {
                if (attachment.getId().equals(attachmentId)) {
                    image.deleteAttachment(attachment);
                    pRepo.save(image);
                    return;
                }
            }
            throw new AttachmentNotFound(attachmentId);
        }
    }

    @Transactional
    public long createImage(String username, String title,
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

    @Transactional(rollbackFor = ImageNotFound.class)
    public void updateImage(long id, String subject,
                             String body, List<MultipartFile> attachments)
            throws IOException, ImageNotFound {
        Image updatedImage = pRepo.findById(id).orElse(null);
        if (updatedImage == null) {
            throw new ImageNotFound(id);
        }
        updatedImage.setTitle(subject);
        updatedImage.setDescription(body);

        for (MultipartFile filePart : attachments) {
            Attachment attachment = new Attachment();
            attachment.setName(filePart.getOriginalFilename());
            attachment.setMimeContentType(filePart.getContentType());
            attachment.setContents(filePart.getBytes());
            attachment.setPhoto(updatedImage);
            if (attachment.getName() != null && attachment.getName().length() > 0
                    && attachment.getContents() != null
                    && attachment.getContents().length > 0) {
                updatedImage.getAttachments().add(attachment);
            }
        }
        pRepo.save(updatedImage);
    }

    @Transactional
    public List<Comment> getComments(long imageId) {
        List<Comment> comments = cRepo.findByImageId(imageId);
        return comments;
    }
    @Transactional
    public List<Comment> getCommentsByUser(String username) {
        List<Comment> comments = cRepo.findByUsername(username);
        return comments;
    }
    @Transactional
    public Comment getComment(long id)
            throws CommentNotFound {
        Comment comment = cRepo.findById(id).orElse(null);
        if (comment == null) {
            throw new CommentNotFound(id);
        }
        return comment;
    }

    @Transactional
    public void createComment(String username, String text,
                           long imageId) throws IOException {
        Image image = pRepo.findById(imageId).orElse(null);
        ImageUser user = puRepo.findById(username).orElse(null);
        if (user == null){
            throw new RuntimeException("User " + username + " not found.");
        }
        if (image == null){
            throw new RuntimeException("Ticket "+ imageId + " not found.");
        }
        Comment comment = new Comment();
        comment.setImage(image);
        comment.setImageUser(user);
        comment.setText(text);

        Comment savedComment = cRepo.save(comment);
        image.getComments().add(savedComment);
    }

    @Transactional
    public void editComment(long commentId, String text) throws IOException {
        Comment editedComment = cRepo.findById(commentId).orElse(null);
        editedComment.setText(text);
        cRepo.save(editedComment);
    }

    @Transactional
    public void deleteComment(long id){
        Comment deletingComment = cRepo.findById(id).orElse(null);
        if (deletingComment == null){
            throw new RuntimeException("Comment " + id + " not found.");
        }
        deletingComment.getImage().getComments().remove(deletingComment);
        cRepo.delete(deletingComment);
    }
}
