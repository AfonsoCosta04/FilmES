package com.filmees.backend.repository;

import com.filmees.backend.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


// Carrinho Repository
@Repository
public interface CarrinhoRepository extends JpaRepository<Carrinho, Integer> {
    Optional<Carrinho> findByIdCliente(Integer idCliente);
}
