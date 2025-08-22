package com.movieflix.controller;


import com.movieflix.controller.request.CategoryRequest;
import com.movieflix.controller.response.CategoryResponse;
import com.movieflix.entity.category.Category;
import com.movieflix.mapper.CategoryMapper;
import com.movieflix.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;


@RestController()
@RequestMapping("/movieflix/categories")
public class CategoryController {


    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }



    @GetMapping("/ListaCompleta")
    public ResponseEntity<List<CategoryResponse>> getAllCategories(){
       List<CategoryResponse> lista = categoryService.findAllCategories().stream()
                .map(category -> CategoryMapper.toCategoryResponse(category))
                .toList();
        return ResponseEntity.ok(lista);
    }

    @PostMapping("/CriarCategoria")
    public ResponseEntity <CategoryResponse> CriarCategoria(@RequestBody CategoryRequest categoryRequest) {
        Category category = CategoryMapper.toCategory(categoryRequest);
        Category  categoryCriado  = categoryService.criarCategory(category);
        return  ResponseEntity.status(HttpStatus.CREATED).body(CategoryMapper.toCategoryResponse(categoryCriado));
    }

    @DeleteMapping("/DeletarCategoria/{id}")
    public void deletarCategoria(@PathVariable Long id) {
        categoryService.deletarCategory(id);

    }

    @GetMapping("/listarPorId/{id}")
    public ResponseEntity <List<CategoryResponse>> listarPorId(@PathVariable Long id) {
        Optional<Category> categoryPresente = categoryService.listarPorId(id).stream().findFirst();
        if (categoryPresente == null || categoryPresente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }return ResponseEntity.ok(categoryService.listarPorId(id).stream()
                .map(category -> CategoryMapper.toCategoryResponse(category))
                .toList());
    }

    @GetMapping("/listarPorNome/{nome}")
    public ResponseEntity <List<CategoryResponse>> listarPorNome(@PathVariable("nome") String name) {
        List<Category> category = categoryService.listarPorNome(name);
        if(category != null || category.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }return ResponseEntity.ok(categoryService.listarPorNome(name).stream()
                .map(cat -> CategoryMapper.toCategoryResponse(cat))
                .toList());

    }

    @PutMapping("/alterarCategoriaPorId/{id}")
    public ResponseEntity <CategoryResponse> alterarCategoriaPorId(@PathVariable Long id,@RequestBody Category category) {
        Category categoryAlterar = categoryService.alterarCategoriaPorId(id, category);
        CategoryResponse categoryAlterada = CategoryMapper.toCategoryResponse(categoryAlterar);
        return ResponseEntity.ok(categoryAlterada);
    }




}
