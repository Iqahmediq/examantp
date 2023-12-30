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
public class Bureau {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String localisation;

    @OneToMany(mappedBy = "bureau", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<ChefDeProjet> chefsDeProjet;

    // Getters and setters
}
