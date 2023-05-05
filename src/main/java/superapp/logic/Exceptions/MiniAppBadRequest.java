package superapp.logic.Exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)

public class MiniAppBadRequest extends RuntimeException{

    private static  final long serialVersionUID = -7871235645453947L;

    public MiniAppBadRequest() {
    }

    public MiniAppBadRequest(String message) {
        super(message);
    }

    public MiniAppBadRequest(String message, Throwable cause) {
        super(message, cause);
    }

    public MiniAppBadRequest(Throwable cause) {
        super(cause);
    }

    public MiniAppBadRequest(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
