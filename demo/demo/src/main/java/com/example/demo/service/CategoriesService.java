package com.example.demo.service;


import com.example.demo.model.Categories;
import com.example.demo.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriesService {
    @Autowired
    private CategoryRepo CatRepo ;

    public Optional <Categories> getById (int id) {
        return CatRepo.findById(id) ;
    }

    public List <Categories> getAll () {
        return CatRepo.findAll();
    }

    public void ajout(Categories categories) {
        CatRepo.save(categories) ;
    }
    public void delete (int id) {
        CatRepo.deleteById(id);
    }
    public void update(Categories categories) {
        CatRepo.save(categories);
    }

}
