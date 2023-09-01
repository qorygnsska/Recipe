package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.example.demo.entity.Love;

import com.example.demo.entity.Recipe;
import com.example.demo.repository.LoveRepository;
import com.example.demo.repository.RecipeRepository;
import java.util.List;

@Controller
public class RankingController {

    private final RecipeRepository recipeRepository;
    private final LoveRepository loveRepository; // Add LoveRepository

    @Autowired
    public RankingController(LoveRepository loveRepository, RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
        this.loveRepository = loveRepository; // Initialize LoveRepository
    }

    @GetMapping("/rank")
    public String getTopRecipes(Model model, @RequestParam(required = false, defaultValue = "0") int page,
                                @RequestParam(required = false) String categoryName,
                                @RequestParam(required = false) String period) { // Add period parameter
        int pageSize = 20; // 페이지당 레시피 수

        List<Recipe> topLove;
        if (categoryName == null || categoryName.isEmpty()) {
            if ("d".equals(period)) {
                topLove = recipeRepository.findByOrderByDailyLoveDesc(); // Use appropriate method names
            } else if ("w".equals(period)) {
                topLove = recipeRepository.findByOrderByWeeklyLoveDesc();
            } else if ("m".equals(period)) {
                topLove = recipeRepository.findByOrderByMonthlyLoveDesc();
            } else {
                topLove = recipeRepository.findByOrderByTotalLoveDesc();
            }
        } else {
            if ("d".equals(period)) {
                topLove = recipeRepository.findByCategoryNameOrderByDailyLoveDesc(categoryName);
            } else if ("w".equals(period)) {
                topLove = recipeRepository.findByCategoryNameOrderByWeeklyLoveDesc(categoryName);
            } else if ("m".equals(period)) {
                topLove = recipeRepository.findByCategoryNameOrderByMonthlyLoveDesc(categoryName);
            } else {
                topLove = recipeRepository.findByCategoryNameOrderByTotalLoveDesc(categoryName);
            }
            model.addAttribute("selectedCategory", categoryName);
        }

        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, topLove.size());

        List<Recipe> pagedRecipes = topLove.subList(startIndex, endIndex);
        model.addAttribute("topLove", pagedRecipes);
        model.addAttribute("currentPage", page);

        return "rank";
    }
}
