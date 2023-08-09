package recipes.security.exceptions;

public class UserAlreadyExistsException extends RuntimeException{
	public UserAlreadyExistsException(String msg) { super(msg);}
}
