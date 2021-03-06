package mat.springapp.recipeapp.services;

import mat.springapp.recipeapp.converters.RecipeCommandToRecipe;
import mat.springapp.recipeapp.converters.RecipeToRecipeCommand;
import mat.springapp.recipeapp.exceptions.NotFoundException;
import mat.springapp.recipeapp.model.Recipe;
import mat.springapp.recipeapp.reporitories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class RecipesServiceImplTest {

    RecipesServiceImpl recipesService;
    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;
    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;
    @Mock
    RecipeRepository recipeRepository;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        recipesService = new RecipesServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    public void getRecipeByIdTest() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        Recipe recipeReturned = recipesService.findById(1L);

        assertNotNull("Null recipe returned", recipeReturned);
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    public void getRecipes() {
        Recipe recipe = new Recipe();
        Set<Recipe> recipesSet = newHashSet(recipe);
        when(recipeRepository.findAll()).thenReturn(recipesSet);
        Set<Recipe> recipes = recipesService.getRecipes();
        assertEquals(1, recipes.size());
        verify(recipeRepository, times(1)).findAll();

    }

    @Test
    public void deleteRecipe() {
        Long toDelete = 2L;
        recipeRepository.deleteById(toDelete);
        verify(recipeRepository, times(1)).deleteById(anyLong());
    }


    @Test(expected = NotFoundException.class)
    public void getRecipeByIdNotFoundTest() throws Exception{

        Optional<Recipe> optionalRecipe = Optional.empty();
        when(recipeRepository.findById(anyLong())).thenReturn(optionalRecipe);
        Recipe recipe = recipesService.findById(1L);


    }

}