package superapp.logic.Exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class DepreacatedOpterationException extends RuntimeException {
    private static final long serialVersionUID = -2519898898208382462L;

    public DepreacatedOpterationException() {
    }

    public DepreacatedOpterationException(String message) {
        super(message);
    }

    public DepreacatedOpterationException(Throwable cause) {
        super(cause);
    }

    public DepreacatedOpterationException(String message, Throwable cause) {
        super(message, cause);
    }
}
