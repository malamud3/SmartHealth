package superapp.logic.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class CommandNotFoundException extends RuntimeException {

	
	private static final long serialVersionUID = -8247917019578921273L;

	public CommandNotFoundException() {
		super();
	}

	public CommandNotFoundException(String message) {
		super(message);
	}

	public CommandNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public CommandNotFoundException(Throwable cause) {
		super(cause);
	}

	public CommandNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}


