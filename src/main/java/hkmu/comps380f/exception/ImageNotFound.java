package hkmu.comps380f.exception;

public class ImageNotFound extends Exception {
    public ImageNotFound(long id) {
        super("Post " + id + " does not exist.");
    }
}
