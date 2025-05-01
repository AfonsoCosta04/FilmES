package com.filmees.backend.repository;

import com.filmees.backend.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Cliente Repository
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    boolean existsByEmailCliente(String emailCliente);
    boolean existsByNumeroDeTelefone(String numeroDeTelefone);
    Cliente findByEmailCliente(String emailCliente);
}
