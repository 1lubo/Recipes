package recipes.recipeData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import recipes.account.Account;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	@NotBlank
	String name;

	@NotBlank
	String category;

	Date date;
	@NotBlank
	String description;
	@ElementCollection(fetch = FetchType.EAGER)
	@NotEmpty
	@Size(min = 1)
	List<String> ingredients;
	@ElementCollection(fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@NotEmpty
	@Size(min = 1)
	List<String> directions;

	@ManyToOne(fetch = FetchType.LAZY)
	private Account account;

	public RecipeDTO convertToDTO(){ return new RecipeDTO(name, category, date, description, ingredients, directions); }
	public void update(Recipe newRecipe) {
		name = newRecipe.getName();
		category = newRecipe.getCategory();
		description = newRecipe.getDescription();
		ingredients = newRecipe.getIngredients();
		directions = newRecipe.getDirections();
	}

	public void setAccount(Account account) {
		this.account = account;
	}

}
