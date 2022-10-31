package pl.edu.pk.cosmo.habsatbackend.userservice.exception;

public class PasswordMismatchException extends Exception{
    public PasswordMismatchException(String message) {
        super(message);
    }
}
