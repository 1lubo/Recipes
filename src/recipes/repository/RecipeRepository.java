package recipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import recipes.recipeData.Recipe;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
	Recipe save(Recipe recipe);
	Recipe findById(long id);

	@Override
	void deleteById(Long id);

	List<Recipe> findByCategoryIgnoreCaseOrderByDateDesc(String category);
	List<Recipe> findAllByNameContainingIgnoreCaseOrderByDateDesc(String name);

}
