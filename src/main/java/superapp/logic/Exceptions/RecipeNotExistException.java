package superapp.logic.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class RecipeNotExistException extends RuntimeException{
	
	private static final long serialVersionUID = 6357221376393556196L;

	public RecipeNotExistException() {
    }

    public RecipeNotExistException(String message) {
        super(message);
    }

    public RecipeNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public RecipeNotExistException(Throwable cause) {
        super(cause);
    }

    public RecipeNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
