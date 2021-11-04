package com.example.demo.repository;

import com.example.demo.model.Categories;
import com.example.demo.model.Produits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Produits,Integer> {
    /*@Query(value = "SELECT * FROM  produits p where p.category_id =: category_id", nativeQuery = true)*/
    /*List<Produits> findProduitsByCategoriesID(int category_id);*/
    List<Produits> findProduitsByCategories(Categories categories );
}
