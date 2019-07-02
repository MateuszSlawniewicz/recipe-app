package mat.springapp.recipeapp.services;

import mat.springapp.recipeapp.command.IngredientCommand;

public interface IngredientService {

    IngredientCommand findByRecipeAndId(Long recipeId, Long ingredientId);

    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);

    void deleteById(Long recipeId, Long ingredientId);
}
