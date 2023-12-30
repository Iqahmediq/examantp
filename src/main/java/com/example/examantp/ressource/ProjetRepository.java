package com.example.examantp.ressource;

import com.example.examantp.entity.Projet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjetRepository extends JpaRepository<Projet, Long> {
}
