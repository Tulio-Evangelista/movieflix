package com.movieflix.service;

import com.movieflix.entity.category.Category;
import com.movieflix.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;


    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }


    public Category criarCategory(Category category) {
        return categoryRepository.save(category);
    }

    public void deletarCategory(Long id) {
        categoryRepository.deleteById(id);
    }


    public List<Category> listarPorId(Long id) {
       Optional<Category> categoryPresente = categoryRepository.findById(id);
        if (categoryPresente.isPresent()) {
            return categoryPresente.stream().toList();

        }return null;
    }

    public List<Category> listarPorNome(String name) {
        return categoryRepository.findAll().stream()
                .filter(category -> category.getName().equalsIgnoreCase(name))
                .toList();
    }


    public Category alterarCategoriaPorId(Long id, Category category) {
        return categoryRepository.findById(id)
                .map(existingCategory -> {
                    existingCategory.setName(category.getName());
                    return categoryRepository.save(existingCategory);
                })
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada com o ID: " + id));
    }
}
