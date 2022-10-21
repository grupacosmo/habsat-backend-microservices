package pl.edu.pk.cosmo.habsatbackend.userservice.exception;

public class EmailTakenException extends Exception{
    public EmailTakenException(String message) {
        super(message);
    }
}
