package com.filmees.backend.controller;

import com.filmees.backend.model.*;
import com.filmees.backend.repository.*;
import com.filmees.backend.security.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/filmes")
@CrossOrigin(origins = "http://localhost:3000")
public class FilmeController {

    @Autowired
    private FilmeRepository filmeRepository;

    @GetMapping
    public List<Filme> listarFilmes() {
        return filmeRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Filme> obterFilme(@PathVariable Integer id) {
        return filmeRepository.findById(id);
    }

    @PostMapping
    public ResponseEntity<?> adicionarFilme(@RequestBody Filme filme, HttpServletRequest request) {
        if (!SecurityUtil.isAdmin(request) && !SecurityUtil.isFuncionario(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado.");
        }
        return ResponseEntity.ok(filmeRepository.save(filme));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarFilme(@PathVariable Integer id, @RequestBody Filme atualizado, HttpServletRequest request) {
        if (!SecurityUtil.isAdmin(request) && !SecurityUtil.isFuncionario(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado.");
        }
        if (!filmeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        atualizado.setIdFilme(id);
        return ResponseEntity.ok(filmeRepository.save(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> apagarFilme(@PathVariable Integer id, HttpServletRequest request) {
        if (!SecurityUtil.isAdmin(request) && !SecurityUtil.isFuncionario(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado.");
        }
        filmeRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
