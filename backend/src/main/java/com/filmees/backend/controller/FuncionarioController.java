package com.filmees.backend.controller;

import com.filmees.backend.model.Funcionario;
import com.filmees.backend.repository.FuncionarioRepository;
import com.filmees.backend.security.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> adicionarFuncionario(
            @RequestPart("dados") Funcionario funcionario,
            @RequestPart(value = "imagem", required = false) MultipartFile imagem,
            HttpServletRequest request) {

        if (!SecurityUtil.isAdmin(request)) {
            return ResponseEntity.status(403).body("Acesso negado.");
        }

        if (funcionarioRepository.existsByEmailFuncionario(funcionario.getEmailFuncionario())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Já existe um funcionário com este email.");
        }

        funcionario.setIdTipo(2);
        funcionario.setPasswordFuncionario(passwordEncoder.encode(funcionario.getPasswordFuncionario()));

        if (imagem != null && !imagem.isEmpty()) {
            try {
                String nomeFicheiro = UUID.randomUUID() + "_" + imagem.getOriginalFilename();
                Path destino = Paths.get("C:/FilmES/filme.s/public/imagens", nomeFicheiro);
                Files.copy(imagem.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);
                funcionario.setFoto("imagens/" + nomeFicheiro);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao guardar a imagem.");
            }
        }else{
            funcionario.setFoto("imagens/" + "default.jpg");
        }

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
