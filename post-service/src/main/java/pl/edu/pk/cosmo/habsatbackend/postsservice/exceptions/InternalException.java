package pl.edu.pk.cosmo.habsatbackend.postsservice.exceptions;

public class InternalException extends Exception {
    public enum Code {
        POST_NOT_FOUND,
        POST_SLUG_IS_NOT_UNIQUE,
        MEDIA_NOT_FOUND,
    }

    private Code code;

    public InternalException(Code code) {
        this.code = code;
    }

    public InternalException(String message, Code code) {
        super(message);
        this.code = code;

    }

    public InternalException(Exception cause, Code code) {
        super(cause);
        this.code = code;

    }
    public InternalException(String message, Exception cause, Code code) {
        super(message, cause);
        this.code = code;
    }

    public Code getType() {
        return code;
    }
}
