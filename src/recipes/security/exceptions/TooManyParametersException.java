package recipes.security.exceptions;

public class TooManyParametersException extends RuntimeException{
	public TooManyParametersException (String msg) {
		super(msg);
	}
}
