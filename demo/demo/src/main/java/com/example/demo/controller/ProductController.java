package com.example.demo.controller;



import com.example.demo.model.Categories;
import com.example.demo.model.Produits;
import com.example.demo.repository.CategoryRepo;
import com.example.demo.repository.ProductRepo;
import com.example.demo.service.ExportProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;

    @Autowired
    public ProductController (CategoryRepo categoryRepo, ProductRepo productRepo) {
        this.categoryRepo = categoryRepo;
        this.productRepo = productRepo;
    }


    @Autowired
    private ExportProductService exportService;

    @PostMapping(path = "/product/add")
    public Produits Add (@RequestBody Produits newProduct) {
        return productRepo.save(newProduct);
    }

    @GetMapping(path = "/product/all")
    public List<Produits> allProducts() {
        return productRepo.findAll();
    }

    @GetMapping(path = "/product/cat/{id}")
    public List<Produits> Productsbycategory(@PathVariable int id) {
        Categories  c = categoryRepo.findById(id).get();
        return productRepo.findProduitsByCategories(c);
    }

    @GetMapping("/product/{id}")
    public Optional<Produits> oneProduits (@PathVariable int id){
        return productRepo.findById(id);
    }

    @DeleteMapping("/product/delete/{id}")
    public void delete (@PathVariable int id) {
        productRepo.deleteById(id);
    }

    @PutMapping("/product/update/{id}")
    public void update (@RequestBody Produits produits, @PathVariable int id)  throws Exception {
        Produits p = productRepo.findById(id).get();
        if (p == null) {
            throw new Exception("erreur");
        } else {
            int qt = produits.getQt();
            String nom = produits.getNom();
            boolean disponible = produits.isDisponible();
            p.setDisponible(disponible);
            p.setNom(nom);
            p.setQt(qt);
            productRepo.save(p);
        }
    }

    @GetMapping("product/export/pdf")
    public ResponseEntity<InputStreamResource> exportProductsPdf() {
        List<Produits> products = (List<Produits>) productRepo.findAll();
        ByteArrayInputStream bais = exportService.productsPDFReport(products);
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-Disposition","inline; filename=products.pdf");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(bais));
    }
    @GetMapping("product/export/excel")
    public ResponseEntity<InputStreamResource> exportProductsExcel() throws IOException {
        List<Produits> products = (List<Produits>) productRepo.findAll();
        ByteArrayInputStream bais = exportService.productsExcelReport(products);
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-Disposition","inline; filename=products.xlsx");
        return ResponseEntity.ok().headers(headers).body(new InputStreamResource(bais));
    }




}
