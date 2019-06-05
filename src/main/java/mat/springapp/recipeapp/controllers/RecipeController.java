package mat.springapp.recipeapp.controllers;

import mat.springapp.recipeapp.services.RecipesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RecipeController {
    private final RecipesService recipesService;

    public RecipeController(RecipesService recipesService) {
        this.recipesService = recipesService;
    }

    @RequestMapping("/recipe/view/{id}")
    public String showById(@PathVariable String id, Model model) {
        model.addAttribute("recipe",recipesService.findById(new Long(id)));
            return "recipe/view";


    }

}
