package com.filmees.backend.repository;

import com.filmees.backend.model.Funcionario;
import com.filmees.backend.model.Recomendacao;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface RecomendacaoRepository extends JpaRepository<Recomendacao, Integer> {
    @EntityGraph(attributePaths = {"funcionario", "recomendacoesFilmes.filme"})
    List<Recomendacao> findAll();

    Optional<Recomendacao> findByFuncionario(Funcionario funcionario);

}
