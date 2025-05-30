package com.filmees.backend.controller;

import com.filmees.backend.model.Admin;
import com.filmees.backend.model.Funcionario;
import com.filmees.backend.repository.FuncionarioRepository;
import com.filmees.backend.security.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.Map;
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

    private static final Logger logger = LoggerFactory.getLogger(FuncionarioController.class);

    @GetMapping
    public ResponseEntity<?> listarFuncionarios(HttpServletRequest request) {
        logger.info("Pedido para listar todos os funcionários.");
        if (!SecurityUtil.isAdmin(request)) {
            logger.info("Pedido para listar todos os funcionários.");
            return ResponseEntity.status(403).body("Acesso negado.");
        }
        logger.warn("Acesso negado ao listar funcionários - utilizador não é admin.");
        return ResponseEntity.ok(funcionarioRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obterFuncionario(@PathVariable Integer id, HttpServletRequest request) {
        logger.info("Pedido para obter funcionário com ID: {}", id);
        Optional<Funcionario> funcionario = funcionarioRepository.findById(id);
        if (!SecurityUtil.isAdmin(request) && !SecurityUtil.isProprio(request, funcionario.get().getEmailFuncionario())) {
            logger.warn("Acesso negado ao obter funcionário com ID: {} - utilizador não autorizado.", id);
            return ResponseEntity.status(403).body("Acesso negado.");
        }
        if (funcionario.isEmpty()) {
            logger.warn("Funcionário com ID {} não encontrado.", id);
            return ResponseEntity.notFound().build();
        }

        if (!SecurityUtil.isAdmin(request) && !SecurityUtil.isProprio(request, funcionario.get().getEmailFuncionario())) {
            logger.warn("Acesso negado ao obter funcionário com ID: {} - utilizador não autorizado.", id);
            return ResponseEntity.status(403).body("Acesso negado.");
        }

        logger.info("Funcionário com ID {} obtido com sucesso.", id);
        return ResponseEntity.ok(funcionario.get());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> adicionarFuncionario(
            @RequestPart("dados") Funcionario funcionario,
            @RequestPart(value = "imagem", required = false) MultipartFile imagem,
            HttpServletRequest request) {

        logger.info("Pedido para adicionar novo funcionário com email: {}", funcionario.getEmailFuncionario());
        if (!SecurityUtil.isAdmin(request)) {
            logger.warn("Acesso negado ao adicionar funcionário - utilizador não é admin.");
            return ResponseEntity.status(403).body("Acesso negado.");
        }

        if (funcionarioRepository.existsByEmailFuncionario(funcionario.getEmailFuncionario())) {
            logger.warn("Tentativa de adicionar funcionário com email já existente: {}", funcionario.getEmailFuncionario());
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
                logger.info("Imagem '{}' guardada com sucesso para o funcionário.", nomeFicheiro);
            } catch (IOException e) {
                logger.error("Erro ao guardar imagem do funcionário: {}", e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao guardar a imagem.");
            }
        } else {
            funcionario.setFoto("imagens/" + "default.jpg");
            logger.info("Sem imagem enviada - definido para 'default.jpg'.");
        }

        logger.info("Funcionário '{}' adicionado com sucesso.", funcionario.getEmailFuncionario());
        return ResponseEntity.ok(funcionarioRepository.save(funcionario));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> atualizarFuncionario(
            @PathVariable Integer id,
            @RequestPart("funcionario") Funcionario funcionarioAtualizado,
            @RequestPart(value = "imagem", required = false) MultipartFile imagem,
            HttpServletRequest request) {

        logger.info("Pedido para atualizar funcionário com ID: {}", id);
        if (!SecurityUtil.isAdmin(request) && !SecurityUtil.isProprio(request, funcionarioAtualizado.getEmailFuncionario())) {
            logger.warn("Acesso negado para atualizar funcionário com ID: {}", id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado.");
        }

        Funcionario existente = funcionarioRepository.findById(id).orElse(null);
        if (existente == null) {
            logger.warn("Funcionário com ID {} não encontrado.", id);
            return ResponseEntity.notFound().build();
        }

        if (imagem != null && !imagem.isEmpty()) {
            try {
                String nomeFicheiro = UUID.randomUUID() + "_" + imagem.getOriginalFilename();
                Path destino = Paths.get("C:/FilmES/filme.s/public/imagens", nomeFicheiro);
                Files.copy(imagem.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);
                funcionarioAtualizado.setFoto("imagens/" + nomeFicheiro);
                logger.info("Imagem '{}' atualizada com sucesso para o funcionário.", nomeFicheiro);
            } catch (IOException e) {
                logger.error("Erro ao atualizar imagem: {}", e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao guardar a imagem.");
            }
        } else {
            funcionarioAtualizado.setFoto(existente.getFoto());
            logger.info("Sem nova imagem enviada - imagem anterior mantida.");
        }
        funcionarioAtualizado.setPasswordFuncionario(existente.getPasswordFuncionario());
        funcionarioAtualizado.setIdFuncionario(id);
        funcionarioAtualizado.setIdTipo(2);

        logger.info("Funcionário com ID {} atualizado com sucesso.", id);
        return ResponseEntity.ok(funcionarioRepository.save(funcionarioAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> apagarFuncionario(@PathVariable Integer id, HttpServletRequest request) {
        logger.info("Pedido para apagar funcionário com ID: {}", id);
        if (!SecurityUtil.isAdmin(request)) {
            logger.warn("Acesso negado ao apagar funcionário - utilizador não é admin.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado.");
        }

        Funcionario funcionario = funcionarioRepository.findById(id).orElse(null);
        if (funcionario == null){
            logger.warn("Funcionário com ID {} não encontrado para exclusão.", id);
            return ResponseEntity.notFound().build();
        }

        if (funcionario.getFoto() != null && !funcionario.getFoto().contains("default.jpg")) {
            try {
                Path caminhoImagem = Paths.get("C:/FilmES/filme.s/public/", funcionario.getFoto());
                Files.deleteIfExists(caminhoImagem);
                logger.info("Imagem '{}' apagada com sucesso.", funcionario.getFoto());
            } catch (IOException e) {
                logger.error("Erro ao apagar imagem do funcionário com ID {}: {}", id, e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao apagar imagem.");
            }
        }

        funcionarioRepository.deleteById(id);
        logger.info("Funcionário com ID {} apagado com sucesso.", id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<?> atualizarPassword(@PathVariable Integer id,
                                               @RequestBody Map<String, String> body,
                                               HttpServletRequest request) {
        logger.info("Pedido para atualizar password do funcionário com ID: {}", id);
        Optional<Funcionario> funcionarioOpt = funcionarioRepository.findById(id);
        if (funcionarioOpt.isEmpty()) {
            logger.warn("Funcionário com ID {} não encontrado para atualização de password.", id);
            return ResponseEntity.notFound().build();
        }

        Funcionario funcionario = funcionarioOpt.get();

        if (!SecurityUtil.isProprio(request, funcionario.getEmailFuncionario())) {
            logger.warn("Acesso negado para atualizar password do funcionário com ID: {}", id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado.");
        }

        String atual = body.get("passwordAtual");
        String nova = body.get("novaPassword");

        if (atual == null || nova == null) {
            logger.warn("Campos obrigatórios para atualização de password em falta.");
            return ResponseEntity.badRequest().body("Campos obrigatórios em falta.");
        }

        if (!passwordEncoder.matches(atual, funcionario.getPasswordFuncionario())) {
            logger.warn("Password atual incorreta para funcionário com ID: {}", id);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Password atual incorreta.");
        }

        funcionario.setPasswordFuncionario(passwordEncoder.encode(nova));
        funcionarioRepository.save(funcionario);

        logger.info("Password do funcionário com ID {} atualizada com sucesso.", id);
        return ResponseEntity.ok("Password atualizada com sucesso.");
    }
}
