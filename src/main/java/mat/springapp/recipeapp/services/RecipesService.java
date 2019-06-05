package mat.springapp.recipeapp.services;

import mat.springapp.recipeapp.command.RecipeCommand;
import mat.springapp.recipeapp.model.Recipe;

import java.util.Set;


public interface RecipesService {

    Set<Recipe> getRecipes();
    Recipe findById(Long l);

    RecipeCommand saveRecipeCommand(RecipeCommand testRecipeCommand);
}
