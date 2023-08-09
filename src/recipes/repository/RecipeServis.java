package recipes.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipes.recipeData.RecipeDTO;
import recipes.security.exceptions.RecipeNotFoundException;
import recipes.recipeData.Recipe;

import java.util.Date;
import java.util.List;

@Service
public class RecipeServis {
	@Autowired
	RecipeRepository repository;

	public void save(Recipe recipe){
		recipe.setDate(new Date());
		repository.save(recipe);
	}

	public Recipe findById(long id){
		Recipe recipe = repository.findById(id);
		if(recipe == null){
			throw new RecipeNotFoundException("Recipe with id: " + id + " does not exist.");
		}
		return recipe;
	}

	public void deleteById(long id){
		Recipe recipe = repository.findById(id);
		if(recipe == null){
			throw new RecipeNotFoundException("Recipe with id: " + id + " does not exist.");
		}
		repository.deleteById(id);
	}
	public List<RecipeDTO> findAllByCategory(String category) {
		return repository.findByCategoryIgnoreCaseOrderByDateDesc(category).stream().map(Recipe::convertToDTO).toList();}
	public List<RecipeDTO> findAllByName(String category) {
		return repository.findAllByNameContainingIgnoreCaseOrderByDateDesc(category).stream().map(Recipe::convertToDTO).toList();}
}
