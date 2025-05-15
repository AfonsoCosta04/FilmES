package com.filmees.backend.repository;

import com.filmees.backend.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// CarrinhoFilme Repository
@Repository
public interface CarrinhoFilmeRepository extends JpaRepository<CarrinhoFilme, Integer> {
    @Query("SELECT cf FROM CarrinhoFilme cf WHERE cf.carrinho.idCarrinho = :idCarrinho")
    List<CarrinhoFilme> findByIdCarrinho(@Param("idCarrinho") Integer idCarrinho);
}
