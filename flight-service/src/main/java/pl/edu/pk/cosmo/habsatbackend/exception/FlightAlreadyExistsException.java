package pl.edu.pk.cosmo.habsatbackend.exception;

public class FlightAlreadyExistsException extends Exception{
    public FlightAlreadyExistsException(String message) {
        super(message);
    }
}
