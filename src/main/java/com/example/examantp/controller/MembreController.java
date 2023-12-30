package com.example.examantp.controller;

import com.example.examantp.entity.MembreEquipe;
import com.example.examantp.ressource.MembreEquipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/membres")
public class MembreController {

    @Autowired
    private MembreEquipeRepository membreEquipeRepository;

    @GetMapping("/{membreId}")
    public ResponseEntity<MembreEquipe> getMembre(@PathVariable Long membreId) {
        return membreEquipeRepository.findById(membreId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MembreEquipe> createMembre(@RequestBody MembreEquipe membre) {
        MembreEquipe savedMembre = membreEquipeRepository.save(membre);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMembre);
    }

    @PutMapping("/{membreId}")
    public ResponseEntity<MembreEquipe> updateMembre(@PathVariable Long membreId, @RequestBody MembreEquipe updatedMembre) {
        return membreEquipeRepository.findById(membreId)
                .map(membre -> {
                    membre.setNom(updatedMembre.getNom());
                    MembreEquipe savedMembre = membreEquipeRepository.save(membre);
                    return ResponseEntity.ok(savedMembre);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<MembreEquipe>> getAllMembres() {
        List<MembreEquipe> membres = membreEquipeRepository.findAll();
        return ResponseEntity.ok(membres);
    }

    @DeleteMapping("/{membreId}")
    public ResponseEntity<Void> deleteMembre(@PathVariable Long membreId) {
        membreEquipeRepository.deleteById(membreId);
        return ResponseEntity.noContent().build();
    }
}
