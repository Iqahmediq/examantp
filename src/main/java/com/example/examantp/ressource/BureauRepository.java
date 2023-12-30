package com.example.examantp.ressource;

import com.example.examantp.entity.Bureau;
import com.example.examantp.entity.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BureauRepository extends JpaRepository<Bureau, Long> {
}
