package hkmu.comps380f.exception;

public class UserNotFound extends Exception {
    public UserNotFound(String name) {
        super("User " + name + " does not exist.");
    }
}
