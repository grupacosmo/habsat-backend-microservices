package pl.edu.pk.cosmo.habsatbackend.authorizationserver.exception;

public class InvalidSessionID extends Exception{
    public InvalidSessionID(String message) {
        super(message);
    }
}
