package com.example.demo.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Produits {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

   @ManyToOne
    @JoinColumn(name = "id_category", nullable = false)
    private Categories categories;

    private String nom ;

    private int qt ;

    private boolean disponible ;

    @CreationTimestamp
    private Timestamp date_creation;

    @UpdateTimestamp
    private Timestamp date_modif ;

    public Produits(Categories categories, String nom, int qt, boolean disponible) {
        this.categories = categories;
        this.nom = nom;
        this.qt = qt;
        this.disponible = disponible;
    }
}
