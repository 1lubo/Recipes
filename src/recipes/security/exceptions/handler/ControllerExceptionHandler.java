package recipes.security.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import recipes.security.error.ErrorMessage;
import recipes.security.exceptions.*;

import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerExceptionHandler {
	@ExceptionHandler(RecipeNotFoundException.class)
	public ResponseEntity<ErrorMessage> AccountLockException(RecipeNotFoundException exception,
																													 WebRequest request) {
		String path = request.getDescription(false).substring(request.getDescription(false).indexOf('=') + 1);
		ErrorMessage errorMessage = new ErrorMessage(
			LocalDateTime.now(),
			404,
			(HttpStatus.valueOf( 404)).getReasonPhrase(),
			exception.getMessage(),
			path);
		return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(MissingParameterException.class)
	public ResponseEntity<ErrorMessage> MissingParameterException(MissingParameterException exception,
																																WebRequest request){
		String path = request.getDescription(false).substring(request.getDescription(false).indexOf('=') + 1);
		ErrorMessage errorMessage = new ErrorMessage(
			LocalDateTime.now(),
			400,
			(HttpStatus.valueOf( 400)).getReasonPhrase(),
			exception.getMessage(),
			path);
		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(TooManyParametersException.class)
	public ResponseEntity<ErrorMessage> TooManyParametersException(TooManyParametersException exception,
																																WebRequest request){
		String path = request.getDescription(false).substring(request.getDescription(false).indexOf('=') + 1);
		ErrorMessage errorMessage = new ErrorMessage(
			LocalDateTime.now(),
			400,
			(HttpStatus.valueOf( 400)).getReasonPhrase(),
			exception.getMessage(),
			path);
		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(AccountValidationException.class)
	public ResponseEntity<ErrorMessage> AccountValidationException(AccountValidationException exception,
																																 WebRequest request){
		String path = request.getDescription(false).substring(request.getDescription(false).indexOf('=') + 1);
		ErrorMessage errorMessage = new ErrorMessage(
			LocalDateTime.now(),
			400,
			(HttpStatus.valueOf( 400)).getReasonPhrase(),
			exception.getMessage(),
			path);
		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<ErrorMessage> UserAlreadyExistsException(UserAlreadyExistsException exception,
																																 WebRequest request){
		String path = request.getDescription(false).substring(request.getDescription(false).indexOf('=') + 1);
		ErrorMessage errorMessage = new ErrorMessage(
			LocalDateTime.now(),
			400,
			(HttpStatus.valueOf( 400)).getReasonPhrase(),
			exception.getMessage(),
			path);
		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(NotYourRecipeException.class)
	public ResponseEntity<ErrorMessage> NotYourRecipeException(NotYourRecipeException exception,
																																 WebRequest request){
		String path = request.getDescription(false).substring(request.getDescription(false).indexOf('=') + 1);
		ErrorMessage errorMessage = new ErrorMessage(
			LocalDateTime.now(),
			403,
			(HttpStatus.valueOf( 403)).getReasonPhrase(),
			exception.getMessage(),
			path);
		return new ResponseEntity<>(errorMessage, HttpStatus.FORBIDDEN);
	}
}
