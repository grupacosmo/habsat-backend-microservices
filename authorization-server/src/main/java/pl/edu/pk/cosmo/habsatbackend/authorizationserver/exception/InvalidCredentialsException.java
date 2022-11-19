package pl.edu.pk.cosmo.habsatbackend.authorizationserver.exception;

public class InvalidCredentialsException extends Exception{
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
