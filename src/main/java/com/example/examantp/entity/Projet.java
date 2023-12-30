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
public class Projet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private String dateDebut;

    @ManyToOne( cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "chef_de_projet_id")
    private ChefDeProjet chefDeProjet;

    @ManyToMany(mappedBy = "projets")
    private List<MembreEquipe> membresEquipe;
}
