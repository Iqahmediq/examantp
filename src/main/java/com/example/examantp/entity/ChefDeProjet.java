package com.example.examantp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class ChefDeProjet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @OneToMany(mappedBy = "chefDeProjet", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Projet> projets;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "bureau_id")
    private Bureau bureau;

}
