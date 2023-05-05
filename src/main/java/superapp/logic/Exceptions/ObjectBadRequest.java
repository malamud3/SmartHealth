package superapp.logic.Exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;



@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ObjectBadRequest extends RuntimeException {

    private static  final long serialVersionUID = -78739384758375947L;

    public ObjectBadRequest() {
    }

    public ObjectBadRequest(String message) {
        super(message);
    }

    public ObjectBadRequest(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectBadRequest(Throwable cause) {
        super(cause);
    }

    public ObjectBadRequest(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
