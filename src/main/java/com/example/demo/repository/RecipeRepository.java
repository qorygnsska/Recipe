package com.example.demo.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

	@Query("SELECT r.title FROM Recipe r WHERE r.recipe_id = :recipeId")
    String findTitleByRecipeId(Long recipeId);
	@Query("SELECT r.main_photo FROM Recipe r WHERE r.recipe_id = :recipeId")
    String findMainPhotoByRecipeId(Long recipeId);

	List<Recipe> findByCategoryNameOrderByTotalLoveDesc(String categoryName);
	List<Recipe> findTop10ByOrderByTotalLoveDesc();
	//List<Recipe> findByTitleDesc(String title);
	//List<Recipe> findTop10ByOrderByCreate_DateDesc();

}
	