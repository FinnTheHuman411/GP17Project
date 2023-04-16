package hkmu.comps380f.exception;

public class CommentNotFound extends Exception {
    public CommentNotFound(long name) {
        super("Comment " + name + " does not exist.");
    }
}
