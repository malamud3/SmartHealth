package superapp.logic.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)

public class CommandBadRequest extends RuntimeException {

	private static final long serialVersionUID = 1685194973449924446L;

	public CommandBadRequest() {
    }

    public CommandBadRequest(String message) {
        super(message);
    }

    public CommandBadRequest(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandBadRequest(Throwable cause) {
        super(cause);
    }

    public CommandBadRequest(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
