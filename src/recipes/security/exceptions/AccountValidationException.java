package recipes.security.exceptions;

public class AccountValidationException extends RuntimeException{
	public AccountValidationException(String msg) {
		super(msg);
	}
}
