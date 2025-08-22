package com.movieflix.controller;


import com.movieflix.entity.category.Category;
import com.movieflix.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController()
@RequestMapping("/movieflix/categories")
public class CategoryController {


    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }



    @GetMapping("/ListaCompleta")
    public List<Category> getAllCategories(){

        return categoryService.findAllCategories();
    }

    @PostMapping("/CriarCategoria")
    public Category CriarCategoria(@RequestBody Category category) {

        return categoryService.criarCategory(category);
    }

    @DeleteMapping("/DeletarCategoria/{id}")
    public void deletarCategoria(@PathVariable Long id) {
        categoryService.deletarCategory(id);

    }

    @GetMapping("/listarPorId/{id}")
    public List<Category> listarPorId(@PathVariable Long id) {
        return categoryService.listarPorId(id);
    }

    @GetMapping("/listarPorNome/{nome}")
    public List<Category> listarPorNome(@PathVariable String nome) {
        return categoryService.listarPorNome(nome);
    }
    @PutMapping("/alterarCategoriaPorId/{id}")
    public Category alterarCategoriaPorId(@PathVariable Long id, Category category) {
        return   categoryService.alterarCategoriaPorId(id, category);
    }

}
