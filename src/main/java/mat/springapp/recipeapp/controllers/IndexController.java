package mat.springapp.recipeapp.controllers;

import lombok.extern.slf4j.Slf4j;
import mat.springapp.recipeapp.services.RecipesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
@Slf4j
@Controller
public class IndexController {

    public IndexController(RecipesService recipesService) {
        this.recipesService = recipesService;
    }

    private final RecipesService recipesService;

    @RequestMapping({"", "/", "/index"})
    public String getIndex(Model model) {
        log.debug("Getting index page");
        model.addAttribute("recipes", recipesService.getRecipes());
        return "index";
    }
}
