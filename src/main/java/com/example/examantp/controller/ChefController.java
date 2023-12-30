package com.example.examantp.controller;

import com.example.examantp.entity.Bureau;
import com.example.examantp.entity.ChefDeProjet;
import com.example.examantp.ressource.BureauRepository;
import com.example.examantp.ressource.ChefDeProjetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chefs")
public class ChefController {

    @Autowired
    private ChefDeProjetRepository chefDeProjetRepository;
@Autowired
private BureauRepository bureauRepository;

    @GetMapping("/{chefId}")
    public ResponseEntity<ChefDeProjet> getChef(@PathVariable Long chefId) {
        return chefDeProjetRepository.findById(chefId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ChefDeProjet> createChef(@RequestBody ChefDeProjet chef) {
        ChefDeProjet savedChef = chefDeProjetRepository.save(chef);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedChef);
    }

    @PutMapping("/{chefId}")
    public ResponseEntity<ChefDeProjet> updateChef(@PathVariable Long chefId, @RequestBody ChefDeProjet updatedChef) {
        ChefDeProjet existingChef = chefDeProjetRepository.findById(chefId).orElse(null);

        if (existingChef == null) {
            return ResponseEntity.notFound().build();
        }

        // Update chef information
        existingChef.setNom(updatedChef.getNom());

        // Check if the updatedChef has a Bureau reference
        if (updatedChef.getBureau() != null && updatedChef.getBureau().getId() != null) {
            Bureau updatedBureau = bureauRepository.findById(updatedChef.getBureau().getId()).orElse(null);

            if (updatedBureau == null) {
                // Handle the case where the updated Bureau is not found
                return ResponseEntity.badRequest().build();
            }

            // Update the Bureau reference
            existingChef.setBureau(updatedBureau);
        } else {
            // Handle the case where the Bureau reference is not provided in the request
            return ResponseEntity.badRequest().build();
        }

        // Save the updated ChefDeProjet
        ChefDeProjet savedChef = chefDeProjetRepository.save(existingChef);
        return ResponseEntity.ok(savedChef);
    }


    @GetMapping("/all")
    public ResponseEntity<List<ChefDeProjet>> getAllChefs() {
        List<ChefDeProjet> chefs = chefDeProjetRepository.findAll();
        return ResponseEntity.ok(chefs);
    }

    @DeleteMapping("/{chefId}")
    public ResponseEntity<Void> deleteChef(@PathVariable Long chefId) {
        chefDeProjetRepository.deleteById(chefId);
        return ResponseEntity.noContent().build();
    }
}
