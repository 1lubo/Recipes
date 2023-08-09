package recipes.security.exceptions;

public class MissingParameterException extends RuntimeException{
	public MissingParameterException (String msg) {
		super(msg);
	}
}
