package com.filmees.backend.repository;

import com.filmees.backend.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Cliente Repository
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    boolean existsByEmailCliente(String emailCliente);
    boolean existsByNumeroDeTelefone(String numeroDeTelefone);
    boolean existsByContribuinte(String contribuinte);
    Optional<Cliente> findByEmailCliente(String email);
}
