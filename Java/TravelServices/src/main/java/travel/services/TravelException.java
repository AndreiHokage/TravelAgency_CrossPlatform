package travel.services;

public class TravelException extends Exception{
    public TravelException(){

    }

    public TravelException(String message) {
        super(message);
    }

    public TravelException(String message, Throwable cause){
        super(message, cause);
    }
}
