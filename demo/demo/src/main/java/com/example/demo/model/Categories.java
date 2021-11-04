package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;


    private String nom ;


    private int qt ;


    @CreationTimestamp
    private Timestamp date_creation;


    @UpdateTimestamp
    private Timestamp date_modif;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categories")
    @JsonIgnore
    private List<Produits> produits;


    public Categories(String nom, int qt, List<Produits> produits) {
        this.nom = nom;
        this.qt = qt;
        this.produits = produits;
    }
}

