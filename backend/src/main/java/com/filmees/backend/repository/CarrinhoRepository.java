package com.filmees.backend.repository;

import com.filmees.backend.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Carrinho Repository
@Repository
public interface CarrinhoRepository extends JpaRepository<Carrinho, Integer> {
}
