package com.example.examantp.controller;

import com.example.examantp.entity.Bureau;
import com.example.examantp.entity.ChefDeProjet;
import com.example.examantp.ressource.BureauRepository;
import com.example.examantp.ressource.ChefDeProjetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/bureaus")  // Corrected the path to use lowercase for consistency
public class BureauController {

    @Autowired
    private BureauRepository bureauRepository;
    @Autowired

    private ChefDeProjetRepository chefDeProjetRepository;

    @GetMapping("/{bureauId}")
    public ResponseEntity<Bureau> getBureau(@PathVariable Long bureauId) {
        // Use Optional to avoid potential NoSuchElementException
        return bureauRepository.findById(bureauId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Bureau> createBureau(@RequestBody Bureau bureau) {
        // Iterate through the chefs and set the bureau for each chef
        List<ChefDeProjet> chefsToPersist = new ArrayList<>();
        if(bureau.getChefsDeProjet()!=null){

        for (ChefDeProjet chef : bureau.getChefsDeProjet()) {
            ChefDeProjet attachedChef = chefDeProjetRepository.findById(chef.getId()).orElse(null);
            if (attachedChef != null) {
                chefsToPersist.add(attachedChef);
            }
        }
        bureau.setChefsDeProjet(chefsToPersist);
        }

        Bureau savedBureau = bureauRepository.save(bureau);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBureau);
    }

    @PutMapping("/{bureauId}")
    public ResponseEntity<Bureau> updateBureau(@PathVariable Long bureauId, @RequestBody Bureau updatedBureau) {
        return bureauRepository.findById(bureauId)
                .map(bureau -> {

                    bureau.setLocalisation(updatedBureau.getLocalisation());
                    bureau.setChefsDeProjet(new ArrayList<>());

                    if (updatedBureau.getChefsDeProjet() != null) {

                        List<ChefDeProjet> chefsToPersist = new ArrayList<>();
                        for (ChefDeProjet chef : updatedBureau.getChefsDeProjet()) {
                            ChefDeProjet attachedChef = chefDeProjetRepository.findById(chef.getId()).orElse(null);
                            if (attachedChef != null) {
                                chefsToPersist.add(attachedChef);
                            }
                        }
                        bureau.setChefsDeProjet(chefsToPersist);
                    }

                    // Save the updated bureau
                    Bureau savedBureau = bureauRepository.save(bureau);

                    return ResponseEntity.ok(savedBureau);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Bureau>> getAllBureaus() {  // Corrected the method name for consistency
        List<Bureau> bureaux = bureauRepository.findAll();
        return ResponseEntity.ok(bureaux);
    }

    @DeleteMapping("/{bureauId}")
    public ResponseEntity<Void> deleteBureau(@PathVariable Long bureauId) {
        bureauRepository.deleteById(bureauId);
        return ResponseEntity.noContent().build();
    }
}
