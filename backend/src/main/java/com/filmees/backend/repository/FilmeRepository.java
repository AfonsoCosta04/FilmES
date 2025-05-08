package com.filmees.backend.repository;

import com.filmees.backend.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Filme Repository
@Repository
public interface FilmeRepository extends JpaRepository<Filme, Integer> {
    @Query("""
    SELECT f FROM Filme f
    JOIN Aluguer a ON f.idFilme = a.filme.idFilme
    WHERE a.dataLevantamento >= CURRENT_DATE - 7
    GROUP BY f.idFilme
    ORDER BY COUNT(a.idAluguer) DESC
""")
    List<Filme> findMaisAlugadosUltimaSemana();
}
