package com.example.examantp.controller;

import com.example.examantp.entity.ChefDeProjet;
import com.example.examantp.entity.Projet;
import com.example.examantp.ressource.ChefDeProjetRepository;
import com.example.examantp.ressource.ProjetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projets")
public class ProjetController {

    @Autowired
    private ProjetRepository projetRepository;
    @Autowired

    private ChefDeProjetRepository chefDeProjetRepository;

    @GetMapping("/{projetId}")
    public ResponseEntity<Projet> getProjet(@PathVariable Long projetId) {
        Optional<Projet> optionalProjet = projetRepository.findById(projetId);
        return optionalProjet.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Projet> createProjet(@RequestBody Projet projet) {
        // Ensure that the ChefDeProjet is attached (not detached)
        ChefDeProjet attachedChef = chefDeProjetRepository.findById(projet.getChefDeProjet().getId()).orElse(null);
        if (attachedChef != null) {
            projet.setChefDeProjet(attachedChef);
        } else {
            // Handle the case where the ChefDeProjet is not found
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Projet savedProjet = projetRepository.save(projet);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProjet);
    }

    @PutMapping("/{projetId}")
    public ResponseEntity<Projet> updateProjet(@PathVariable Long projetId, @RequestBody Projet updatedProjet) {
        Optional<Projet> optionalProjet = projetRepository.findById(projetId);
        return optionalProjet.map(projet -> {
            projet.setNom(updatedProjet.getNom());
            Projet savedProjet = projetRepository.save(projet);
            return ResponseEntity.ok(savedProjet);
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Projet>> getAllProjets() {
        List<Projet> projets = projetRepository.findAll();
        return ResponseEntity.ok(projets);
    }

    @DeleteMapping("/{projetId}")
    public ResponseEntity<Void> deleteProjet(@PathVariable Long projetId) {
        projetRepository.deleteById(projetId);
        return ResponseEntity.noContent().build();
    }
}
