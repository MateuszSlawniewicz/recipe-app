package mat.springapp.recipeapp.controllers;

import lombok.extern.slf4j.Slf4j;
import mat.springapp.recipeapp.command.IngredientCommand;
import mat.springapp.recipeapp.services.IngredientService;
import mat.springapp.recipeapp.services.RecipesService;
import mat.springapp.recipeapp.services.UnitOfMeasureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class IngredientController {

    private final RecipesService recipesService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipesService recipesService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipesService = recipesService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredients")
    public String listIngredients(@PathVariable String recipeId, Model model) {
        log.debug("Getting  ingredient list from recipe id: " + recipeId);
        model.addAttribute("recipe", recipesService.findCommandById(Long.valueOf(recipeId)));
        return "recipe/ingredient/list";
    }

    @GetMapping
    @RequestMapping("recipe/{recipeId}/ingredient/{id}/view")
    public String viewRecipeIngredient(@PathVariable String recipeId,
                                       @PathVariable String id, Model model) {
        model.addAttribute("ingredient", ingredientService.findByRecipeAndId(Long.valueOf(recipeId), Long.valueOf(id)));
        return "recipe/ingredient/view";

    }

    @GetMapping
    @RequestMapping("recipe/{recipeId}/ingredient/{id}/update")
    public String updateRecipeIngredient(@PathVariable String recipeId,
                                         @PathVariable String id, Model model) {
        model.addAttribute("ingredient", ingredientService.findByRecipeAndId(Long.valueOf(recipeId), Long.valueOf(id)));
        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
        return "recipe/ingredient/ingredientform";

    }

    @PostMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand ingredientCommand) {
        IngredientCommand savedIngredient = ingredientService.saveIngredientCommand(ingredientCommand);
        log.debug("saved recipe id: " + savedIngredient.getRecipeId());
        log.debug("saved ingredient id: " + savedIngredient.getId());
        return "redirect:/recipe/" + savedIngredient.getRecipeId() + "/ingredient/" + savedIngredient.getId() + "/view";

    }


}
