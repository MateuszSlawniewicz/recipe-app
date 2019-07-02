package mat.springapp.recipeapp.services;

import lombok.extern.slf4j.Slf4j;
import mat.springapp.recipeapp.command.IngredientCommand;
import mat.springapp.recipeapp.converters.IngredientCommandToIngredient;
import mat.springapp.recipeapp.converters.IngredientToIngredientCommand;
import mat.springapp.recipeapp.model.Ingredient;
import mat.springapp.recipeapp.model.Recipe;
import mat.springapp.recipeapp.reporitories.RecipeRepository;
import mat.springapp.recipeapp.reporitories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository, IngredientCommandToIngredient ingredientCommandToIngredient) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
    }

    @Override
    public IngredientCommand findByRecipeAndId(Long recipeId, Long ingredientId) {
        Optional<Recipe> byId = recipeRepository.findById(Long.valueOf(recipeId));
        if (!byId.isPresent()) {
            log.error("recipe not found. Id: " + recipeId);
        }
        Recipe recipe = byId.get();

        Optional<IngredientCommand> ingrCommand = recipe.getIngredients().stream()
                .filter(e -> e.getId().equals(ingredientId))
                .map(ingredientToIngredientCommand::convert)
                .findFirst();
        if (!ingrCommand.isPresent()) {
            log.error("ingredient id not found. Id: " + ingredientId);
        }
        return ingrCommand.get();
    }


    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());

        if (!recipeOptional.isPresent()) {

            //todo toss error if not found!
            log.error("Recipe not found for id: " + command.getRecipeId());
            return new IngredientCommand();
        } else {
            Recipe recipe = recipeOptional.get();
            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
                    .findFirst();

            if (ingredientOptional.isPresent()) {
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(command.getDescription());
                ingredientFound.setAmount(command.getAmount());
                ingredientFound.setUnitOfMeasure(unitOfMeasureRepository
                        .findById(command.getUnitOfMeasure().getId())
                        .orElseThrow(() -> new RuntimeException("UOM NOT FOUND"))); //todo address this
            } else {
                //add new Ingredient
                Ingredient ingredient = ingredientCommandToIngredient.convert(command);
                ingredient.setRecipe(recipe);
                recipe.addIngredient(ingredient);
            }
            Recipe savedRecipe = recipeRepository.save(recipe);
            Optional<Ingredient> optionalIngredient = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
                    .findFirst();
            if (!optionalIngredient.isPresent()) {
                optionalIngredient = savedRecipe.getIngredients().stream()
                        .filter(e -> e.getDescription().equals(command.getDescription()))
                        .filter(e -> e.getAmount().equals(command.getAmount()))
                        .filter(e -> e.getUnitOfMeasure().getId().equals(command.getUnitOfMeasure().getId()))
                        .findFirst();
            }
            return ingredientToIngredientCommand.convert(optionalIngredient.get());
        }

    }

    @Override
    public void deleteById(Long recipeId, Long ingredientId) {
        log.debug("deleting ingredient id: " + ingredientId);
        Optional<Recipe> optionalRecipe = recipeRepository.findById(Long.valueOf(recipeId));
        if (optionalRecipe.isPresent()) {
            Recipe recipe = optionalRecipe.get();
            log.debug("found recipe id: " + recipe.getId());

            Optional<Ingredient> optionalIngredient = recipe.getIngredients().stream()
                    .filter(e -> e.getId().equals(ingredientId))
                    .findFirst();

            if (optionalIngredient.isPresent()) {
                log.debug("found ingredient");
                Ingredient ingreientToDelete = optionalIngredient.get();
                ingreientToDelete.setRecipe(null);
                recipe.getIngredients().remove(optionalIngredient.get());
                recipeRepository.save(recipe);
            }
        } else {
            log.debug("Recipe not found");
        }
    }


}
