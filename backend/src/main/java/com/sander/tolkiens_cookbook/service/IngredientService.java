package com.sander.tolkiens_cookbook.service;

import com.sander.tolkiens_cookbook.repository.IngredientDAO;
import com.sander.tolkiens_cookbook.dto.IngredientDTO;
import com.sander.tolkiens_cookbook.mapper.IngredientMapper;
import com.sander.tolkiens_cookbook.model.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IngredientService {

    @Autowired
    private IngredientDAO ingredientDAO;

    public List<IngredientDTO> getAllIngredients() {
        return ingredientDAO.findAll().stream()
                .map(IngredientMapper::toDTO)
                .collect(Collectors.toList());
    }

    public IngredientDTO getIngredientById(int id) {
        Optional<Ingredient> ingredientOpt = ingredientDAO.findById(id);
        return ingredientOpt.map(ingredient -> new IngredientDTO(
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getCategory().toString(),
                ingredient.isVegetarian()
        )).orElse(null); // TODO: Throw an exception if not found
    }

    public Ingredient getIngredientByName(String name) {
        return ingredientDAO.findByName(name);
    }

    public Ingredient createIngredient(Ingredient ingredient) {
        return ingredientDAO.save(ingredient);
    }

    public void deleteIngredient(int id) {
        ingredientDAO.deleteById(id);
    }
}
