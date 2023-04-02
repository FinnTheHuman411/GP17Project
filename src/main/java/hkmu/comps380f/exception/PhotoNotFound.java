package hkmu.comps380f.exception;

public class PhotoNotFound extends Exception {
    public PhotoNotFound(long id) {
        super("Photo " + id + " does not exist.");
    }
}
