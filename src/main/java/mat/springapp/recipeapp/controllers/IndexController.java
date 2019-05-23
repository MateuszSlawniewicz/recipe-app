package mat.springapp.recipeapp.controllers;

import mat.springapp.recipeapp.services.RecipesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    public IndexController(RecipesService recipesService) {
        this.recipesService = recipesService;
    }

    private final RecipesService recipesService;

    @RequestMapping({"", "/", "/index"})
    public String getIndex(Model model) {
        model.addAttribute("recipes", recipesService.getRecipes());
        return "index";
    }
}
