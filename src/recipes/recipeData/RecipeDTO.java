package recipes.recipeData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDTO {
	String name;
	String category;
	Date date;
	String description;
	List<String> ingredients;
	List<String> directions;
}
