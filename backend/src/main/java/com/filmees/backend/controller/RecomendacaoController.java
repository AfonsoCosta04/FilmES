package com.filmees.backend.controller;

import com.filmees.backend.model.Funcionario;
import com.filmees.backend.repository.FuncionarioRepository;
import com.filmees.backend.security.JwtUtil;
import com.filmees.backend.service.RecomendacaoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recomendacoes")
@CrossOrigin(origins = "http://localhost:3000")
public class RecomendacaoController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private RecomendacaoService recomendacaoService;

    @PostMapping
    @PreAuthorize("hasAuthority('FUNCIONARIO')")
    public ResponseEntity<?> recomendarFilmes(@RequestBody List<Integer> filmes, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Token ausente ou inválido.");
        }

        token = token.substring(7); // remover "Bearer "

        String email = jwtUtil.extractUsername(token); // sub = email
        Funcionario funcionario = funcionarioRepository.findByEmailFuncionario(email)
                .orElse(null);

        if (funcionario == null) {
            return ResponseEntity.status(404).body("Funcionário não encontrado.");
        }

        recomendacaoService.guardarRecomendacao(funcionario.getIdFuncionario(), filmes);
        return ResponseEntity.ok("Recomendação guardada com sucesso.");
    }

    @GetMapping
    public ResponseEntity<?> listarRecomendacoes() {
        return ResponseEntity.ok(recomendacaoService.listarRecomendacoes());
    }
}
