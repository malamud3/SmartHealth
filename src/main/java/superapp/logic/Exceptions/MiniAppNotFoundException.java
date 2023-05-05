package superapp.logic.Exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)

public class MiniAppNotFoundException extends RuntimeException {
    private static  final long serialVersionUID = -5739472394723565647L;

    public MiniAppNotFoundException() {
    }

    public MiniAppNotFoundException(String message) {
        super(message);
    }

    public MiniAppNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MiniAppNotFoundException(Throwable cause) {
        super(cause);
    }

    public MiniAppNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
