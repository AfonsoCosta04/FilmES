package com.filmees.backend.repository;

import com.filmees.backend.model.ListaDesejos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
public interface ListaDesejosRepository extends JpaRepository<ListaDesejos, Integer> {

    List<ListaDesejos> findByCliente_IdCliente(Integer idCliente);

    Optional<ListaDesejos> findByCliente_IdClienteAndFilme_IdFilme(Integer idCliente, Integer idFilme);

    void deleteByCliente_IdClienteAndFilme_IdFilme(Integer idCliente, Integer idFilme);
}


