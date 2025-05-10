package com.filmees.backend.repository;

import com.filmees.backend.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

// Filme Repository
@Repository
public interface FilmeRepository extends JpaRepository<Filme, Integer> {
    @Query("""
    SELECT f FROM Filme f 
    JOIN Aluguer a ON f.idFilme = a.filme.idFilme 
    WHERE a.dataLevantamento >= :dataLimite 
    GROUP BY f.idFilme 
    ORDER BY COUNT(a.idFilme) DESC
""")
    List<Filme> findMaisAlugadosUltimaSemana(@Param("dataLimite") LocalDate dataLimite);
}
