package com.filmees.backend.repository;

import com.filmees.backend.model.Aluguer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AluguerRepository extends JpaRepository<Aluguer, Integer> {
    List<Aluguer> findByCliente_IdCliente(Integer idCliente);

    List<Aluguer> findByFilmes_IdFilme(Integer idFilme);

    long countByCliente_IdClienteAndEstadoIn(Integer idCliente, List<String> estados);

    List<Aluguer> findByCliente_IdClienteAndEstadoIn(Integer idCliente, List<String> estados);
}
