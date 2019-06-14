package mat.springapp.recipeapp.services;

import mat.springapp.recipeapp.command.IngredientCommand;
import mat.springapp.recipeapp.converters.IngredientCommandToIngredient;
import mat.springapp.recipeapp.converters.IngredientToIngredientCommand;
import mat.springapp.recipeapp.converters.UnitOfMeasureCommandToUnitOfMeasure;
import mat.springapp.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import mat.springapp.recipeapp.model.Ingredient;
import mat.springapp.recipeapp.model.Recipe;
import mat.springapp.recipeapp.reporitories.RecipeRepository;
import mat.springapp.recipeapp.reporitories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class IngredientServiceImplTest {
    @Mock
    RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    IngredientService ingredientService;
    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;


    public IngredientServiceImplTest() {
        this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    }


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ingredientService = new IngredientServiceImpl(ingredientToIngredientCommand, recipeRepository, unitOfMeasureRepository, ingredientCommandToIngredient);
    }

    @Test
    public void findByRecipeAndId() {
    }

    @Test
    public void findByRecipeAndIdHappyPath() {
//given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(1L);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(3L);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);
        Optional<Recipe> optionalRecipe = Optional.of(recipe);
        when(recipeRepository.findById(anyLong())).thenReturn(optionalRecipe);

        //when
        IngredientCommand ingredientCommand = ingredientService.findByRecipeAndId(1L, 3L);
        //then
        assertEquals(Long.valueOf(3L), ingredientCommand.getId());
        assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());
        verify(recipeRepository, times(1)).findById(anyLong());


    }

}