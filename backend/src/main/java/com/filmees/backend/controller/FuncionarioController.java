package com.filmees.backend.controller;

import com.filmees.backend.model.Funcionario;
import com.filmees.backend.repository.FuncionarioRepository;
import com.filmees.backend.security.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/funcionarios")
@CrossOrigin(origins = "http://localhost:3000")
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<?> listarFuncionarios(HttpServletRequest request) {
        if (!SecurityUtil.isAdmin(request)) {
            return ResponseEntity.status(403).body("Acesso negado.");
        }
        return ResponseEntity.ok(funcionarioRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obterFuncionario(@PathVariable Integer id, HttpServletRequest request) {
        if (!SecurityUtil.isAdmin(request)) {
            return ResponseEntity.status(403).body("Acesso negado.");
        }
        Optional<Funcionario> funcionario = funcionarioRepository.findById(id);
        return funcionario.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> adicionarFuncionario(@RequestBody Funcionario funcionario, HttpServletRequest request) {
        if (!SecurityUtil.isAdmin(request)) {
            return ResponseEntity.status(403).body("Acesso negado.");
        }

        if (funcionarioRepository.existsByEmailFuncionario(funcionario.getEmailFuncionario())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Já existe um funcionário com este email.");
        }

        funcionario.setIdTipo(2);
        funcionario.setPasswordFuncionario(passwordEncoder.encode(funcionario.getPasswordFuncionario()));
        return ResponseEntity.ok(funcionarioRepository.save(funcionario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarFuncionario(@PathVariable Integer id,
                                                  @RequestBody Funcionario funcionarioAtualizado,
                                                  HttpServletRequest request) {
        if (!SecurityUtil.isAdmin(request)) {
            return ResponseEntity.status(403).body("Acesso negado.");
        }
        if (!funcionarioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        funcionarioAtualizado.setPasswordFuncionario(passwordEncoder.encode(funcionarioAtualizado.getPasswordFuncionario()));
        funcionarioAtualizado.setIdFuncionario(id);
        return ResponseEntity.ok(funcionarioRepository.save(funcionarioAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> apagarFuncionario(@PathVariable Integer id, HttpServletRequest request) {
        if (!SecurityUtil.isAdmin(request)) {
            return ResponseEntity.status(403).body("Acesso negado.");
        }
        if (!funcionarioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        funcionarioRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
