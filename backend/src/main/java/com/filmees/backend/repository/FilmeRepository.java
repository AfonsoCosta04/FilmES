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
    List<Filme> findByTituloContainingIgnoreCase(String titulo);
    @Query("""
                SELECT f FROM Filme f 
                JOIN Aluguer a ON f.idFilme = a.filme.idFilme 
                WHERE a.dataLevantamento >= :dataLimite 
                GROUP BY f.idFilme 
                ORDER BY COUNT(a.filme.idFilme) DESC
            """)
    List<Filme> findMaisAlugadosUltimaSemana(@Param("dataLimite") LocalDate dataLimite);

    @Query(value = """
                SELECT * FROM filme
                WHERE (:genero IS NULL OR LOWER(genero1) = LOWER(:genero)
                                       OR LOWER(genero2) = LOWER(:genero)
                                       OR LOWER(genero3) = LOWER(:genero))
                  AND (
                    :ano IS NULL OR 
                    (:ano = 'older' AND ano < 2020) OR 
                    (:ano <> 'older' AND ano = CAST(:ano AS UNSIGNED))
                  )
                  AND (
                    :duracao IS NULL OR
                    (:duracao = '-90min' AND (duracao_horas * 60 + duracao_minutos) < 90) OR
                    (:duracao = '90min-120min' AND (duracao_horas * 60 + duracao_minutos) BETWEEN 90 AND 120) OR
                    (:duracao = '+120min' AND (duracao_horas * 60 + duracao_minutos) > 120)
                  )
            """, nativeQuery = true)
    List<Filme> filtrarFilmes(
            @Param("genero") String genero,
            @Param("ano") String ano,
            @Param("duracao") String duracao
    );


}

