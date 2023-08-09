package recipes.security.exceptions;

public class RecipeNotFoundException extends RuntimeException{
	public RecipeNotFoundException (String msg) {
		super(msg);
	}
}
