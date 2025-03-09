package com.sander.tolkiens_cookbook.service;

import com.sander.tolkiens_cookbook.dto.IngredientDTO;
import com.sander.tolkiens_cookbook.entity.Ingredient;
import com.sander.tolkiens_cookbook.mapper.IngredientMapper;
import com.sander.tolkiens_cookbook.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IngredientService {
    @Autowired
    private IngredientRepository ingredientRepository;

    public List<IngredientDTO> getAllIngredients() {
        List<Ingredient> ingredients = ingredientRepository.findAll();
        return ingredients.stream()
                .map(IngredientMapper::toDTO) // Map each ingredient to its DTO
                .collect(Collectors.toList());
    }

    public IngredientDTO getIngredientById(int id) {
        Optional<Ingredient> ingredientOpt = ingredientRepository.findById(id);
        if (ingredientOpt.isPresent()) {
            Ingredient ingredient = ingredientOpt.get();

            return new IngredientDTO(
                    ingredient.getId(),
                    ingredient.getName(),
                    ingredient.getCategory().toString(),
                    ingredient.isVegetarian()
            );
        }
        return null; // ToDo: throw an exception if not found
    }

    public Ingredient getIngredientByName(String name) {
        return ingredientRepository.findByName(name);
    }

    public Ingredient createIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    public void deleteIngredient(int id) {
        ingredientRepository.deleteById(id);
    }
}