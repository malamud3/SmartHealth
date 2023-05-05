package superapp.logic.Exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)

public class UserBadRequest extends RuntimeException {

    private static  final long serialVersionUID = -723461723645453947L;

    public UserBadRequest() {
    }

    public UserBadRequest(String message) {
        super(message);
    }

    public UserBadRequest(String message, Throwable cause) {
        super(message, cause);
    }

    public UserBadRequest(Throwable cause) {
        super(cause);
    }

    public UserBadRequest(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
