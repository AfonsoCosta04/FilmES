package com.filmees.backend.controller;

import com.filmees.backend.model.TipoUtilizador;
import com.filmees.backend.repository.TipoUtilizadorRepository;
import com.filmees.backend.security.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tipos-utilizador")
@CrossOrigin(origins = "http://localhost:3000")
public class TipoUtilizadorController {

    @Autowired
    private TipoUtilizadorRepository tipoUtilizadorRepository;

    private static final Logger logger = LoggerFactory.getLogger(TipoUtilizadorController.class);


    @GetMapping
    public ResponseEntity<?> listarTodos(HttpServletRequest request) {
        logger.info("Requisição para listar todos os tipos de utilizador.");
        if (!SecurityUtil.isAdmin(request)) {
            logger.warn("Acesso negado à listagem de tipos de utilizador.");
            return ResponseEntity.status(403).body("Acesso negado.");
        }
        List<TipoUtilizador> tipos = tipoUtilizadorRepository.findAll();
        logger.info("Total de tipos de utilizador encontrados: {}", tipos.size());
        return ResponseEntity.ok(tipos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obterPorId(@PathVariable Integer id, HttpServletRequest request) {
        logger.info("Requisição para obter tipo de utilizador com ID: {}", id);
        if (!SecurityUtil.isAdmin(request)) {
            logger.warn("Acesso negado à obtenção de tipo de utilizador ID: {}", id);
            return ResponseEntity.status(403).body("Acesso negado.");
        }
        Optional<TipoUtilizador> tipo = tipoUtilizadorRepository.findById(id);
        if (tipo.isPresent()) {
            logger.info("Tipo de utilizador encontrado: {}", tipo.get().getDescricao());
            return ResponseEntity.ok(tipo.get());
        } else {
            logger.warn("Tipo de utilizador com ID {} não encontrado.", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody TipoUtilizador tipoUtilizador, HttpServletRequest request) {
        logger.info("Requisição para adicionar novo tipo de utilizador: {}", tipoUtilizador.getDescricao());
        if (!SecurityUtil.isAdmin(request)) {
            logger.warn("Acesso negado à adição de tipo de utilizador.");
            return ResponseEntity.status(403).body("Acesso negado.");
        }
        TipoUtilizador salvo = tipoUtilizadorRepository.save(tipoUtilizador);
        logger.info("Tipo de utilizador adicionado com ID: {}", salvo.getIdTipo());
        return ResponseEntity.ok(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Integer id,
                                       @RequestBody TipoUtilizador atualizado,
                                       HttpServletRequest request) {
        logger.info("Requisição para atualizar tipo de utilizador ID: {}", id);
        if (!SecurityUtil.isAdmin(request)) {
            logger.warn("Acesso negado à atualização de tipo de utilizador ID: {}", id);
            return ResponseEntity.status(403).body("Acesso negado.");
        }
        atualizado.setIdTipo(id);
        logger.info("Tipo de utilizador com ID {} atualizado com sucesso.", id);
        return ResponseEntity.ok(tipoUtilizadorRepository.save(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> apagar(@PathVariable Integer id, HttpServletRequest request) {
        logger.info("Requisição para apagar tipo de utilizador ID: {}", id);
        if (!SecurityUtil.isAdmin(request)) {
            logger.warn("Acesso negado à remoção de tipo de utilizador ID: {}", id);
            return ResponseEntity.status(403).body("Acesso negado.");
        }
        tipoUtilizadorRepository.deleteById(id);
        logger.info("Tipo de utilizador com ID {} removido com sucesso.", id);
        return ResponseEntity.ok().build();
    }
}
