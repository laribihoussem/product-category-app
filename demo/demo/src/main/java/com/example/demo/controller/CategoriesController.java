package com.example.demo.controller;

import com.example.demo.model.Categories;
import com.example.demo.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CategoriesController {

    private final CategoryRepo categoryRepo ;

    @Autowired
    public CategoriesController (CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @PostMapping(path = "/categories/add")
    public Categories Add (@RequestBody Categories newCategory) {
         return categoryRepo.save(newCategory);
    }

    @DeleteMapping("/category/delete/{id}")
    public void delete (@PathVariable int id) {
        categoryRepo.deleteById(id);
    }

    @GetMapping("/category/all")
    public List <Categories> allCategories() {
        return categoryRepo.findAll();
    }

    @GetMapping("category/byid/{id}")
    public Optional <Categories> oneCategorie (@PathVariable int id){
        return categoryRepo.findById(id);
    }

    @PutMapping("/category/update/{id}")
    public void update (@RequestBody Categories categories, @PathVariable int id) throws Exception {
        Categories c = categoryRepo.findById(id).get();
        if (c == null) {
            throw new Exception("erreur");
        } else {
            int qt = categories.getQt();
            String nom = categories.getNom();
            c.setNom(nom);
            c.setQt(qt);
            categoryRepo.save(c);
        }
    }

}
