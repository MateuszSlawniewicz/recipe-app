package mat.springapp.recipeapp.services;

import mat.springapp.recipeapp.command.IngredientCommand;

public interface IngredientService {

    IngredientCommand findByRecipeAndId(Long valueOf, Long valueOf1);

    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);
}
