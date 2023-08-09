package recipes.recipeController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import recipes.account.Account;
import recipes.account.UserDetailsImpl;
import recipes.account.UserDetailsServiceImpl;
import recipes.recipeData.Recipe;
import recipes.recipeData.RecipeDTO;
import recipes.repository.RecipeServis;
import recipes.security.exceptions.MissingParameterException;
import recipes.security.exceptions.NotYourRecipeException;
import recipes.security.exceptions.RecipeNotFoundException;
import recipes.security.exceptions.TooManyParametersException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static recipes.recipeController.ParameterCheck.stringIsNull;

@RestController
public class RecipeController {
	@Autowired
	RecipeServis recipeServis;

	@Autowired
	UserDetailsServiceImpl accountDetailsService;

	@GetMapping("api/recipe/search")
	public ResponseEntity<Object> findRecipesByCategoryOrName(@RequestParam(required = false) String category,
																														@RequestParam(required = false) String name){
		if(stringIsNull(category) && stringIsNull(name)){
			throw new MissingParameterException("At least one parameter is required to find recipes.");
		}
		if(!stringIsNull(category) && !stringIsNull(name)){
			throw new TooManyParametersException("Only one parameter is necessary to search for recipes.");
		}
		List<RecipeDTO> recipes;
		if(category != null){
			recipes = recipeServis.findAllByCategory(category);
		} else {
			recipes = recipeServis.findAllByName(name);
		}
		return new ResponseEntity<>(recipes, HttpStatus.OK);
	}

	@GetMapping("api/recipe/{id}")
	public ResponseEntity<Object> getRecipe(@PathVariable("id") int id){
		Recipe recipe = recipeServis.findById(id);
		return new ResponseEntity<>(recipe.convertToDTO(), HttpStatus.OK);
	}
	@PostMapping("api/recipe/new")
	public ResponseEntity<Object> getRecipe(@RequestBody @Valid Recipe newRecipe, Authentication authentication){
		UserDetailsImpl details = (UserDetailsImpl) authentication.getPrincipal();
		Account currentUser = accountDetailsService.findById(details.getId());
		newRecipe.setAccount(currentUser);
		recipeServis.save(newRecipe);
		final Map<String, Object> body = new HashMap<>();
		body.put("id", newRecipe.getId());
		return new ResponseEntity<>(body, HttpStatus.OK);
	}

	@PutMapping("api/recipe/{id}")
	public ResponseEntity<Object> updateRecipe(@PathVariable("id") int id, @RequestBody @Valid Recipe updatedRecipe, Authentication authentication){
		UserDetailsImpl details = (UserDetailsImpl) authentication.getPrincipal();
		Account currentUser = accountDetailsService.findById(details.getId());
		Recipe originalRecipe = recipeServis.findById(id);
		if(originalRecipe == null) {
			throw new RecipeNotFoundException("Recipe with id: " + id + " does not exist.");
		}
		if(currentUser.getRecipes().contains(originalRecipe)){
			originalRecipe.update(updatedRecipe);
			recipeServis.save(originalRecipe);
			final Map<String, Object> body = new HashMap<>();
			body.put("result", "Recipe #" + originalRecipe.getId() + " updated.");
			return new ResponseEntity<>(body, HttpStatus.NO_CONTENT);
		} else {
			throw new NotYourRecipeException("This Recipe does not belong to you");
		}

	}

	@DeleteMapping("api/recipe/{id}")
	public ResponseEntity<Object> deleteRecipe(@PathVariable("id") int id, Authentication authentication){
		UserDetailsImpl details = (UserDetailsImpl) authentication.getPrincipal();
		Account currentUser = accountDetailsService.findById(details.getId());
		Recipe originalRecipe = recipeServis.findById(id);
		if(originalRecipe == null) {
			throw new RecipeNotFoundException("Recipe with id: " + id + " does not exist.");
		}
		if(currentUser.getRecipes().contains(originalRecipe)){
			recipeServis.deleteById(id);
			final Map<String, Object> body = new HashMap<>();
			body.put("result", "Recipe deleted.");
			return new ResponseEntity<>(body, HttpStatus.NO_CONTENT);
		} else {
			throw new NotYourRecipeException("This Recipe does not belong to you");
		}

	}
}
