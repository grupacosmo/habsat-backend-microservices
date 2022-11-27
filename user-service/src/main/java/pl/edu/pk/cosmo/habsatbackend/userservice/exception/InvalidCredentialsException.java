package pl.edu.pk.cosmo.habsatbackend.userservice.exception;

public class InvalidCredentialsException extends Exception{
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
