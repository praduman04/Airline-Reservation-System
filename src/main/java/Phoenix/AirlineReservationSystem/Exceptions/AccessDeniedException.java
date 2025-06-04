package Phoenix.AirlineReservationSystem.Exceptions;

public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException(String msg) { super(msg);
    }
}
