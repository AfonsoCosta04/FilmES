package com.filmees.backend.repository;

import com.filmees.backend.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Funcionario Repository
@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {
    boolean existsByEmailFuncionario(String emailFuncionario);
    boolean existsByTelefone(String telefone);
    Optional<Funcionario> findByEmailFuncionario(String email);
}
