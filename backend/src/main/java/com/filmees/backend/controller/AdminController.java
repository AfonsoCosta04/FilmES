package com.filmees.backend.controller;

import com.filmees.backend.model.Admin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.filmees.backend.repository.AdminRepository;
import com.filmees.backend.security.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admins")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @GetMapping
    public ResponseEntity<?> listarAdmins(HttpServletRequest request) {
        logger.info("Pedido para listar todos os admins recebido.");
        if (!SecurityUtil.isAdmin(request)) {
            logger.warn("Acesso negado para listar admins.");
            return ResponseEntity.status(403).body("Acesso negado.");
        }
        logger.debug("Admin autorizado. A devolver lista de admins.");
        return ResponseEntity.ok(adminRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obterAdmin(@PathVariable Integer id, HttpServletRequest request) {
        logger.info("Pedido para obter admin com ID {}", id);
        if (!SecurityUtil.isAdmin(request)) {
            logger.warn("Acesso negado para obter admin com ID {}", id);
            return ResponseEntity.status(403).body("Acesso negado.");
        }
        Optional<Admin> admin = adminRepository.findById(id);
        if (admin.isPresent()) {
            logger.debug("Admin encontrado: {}", admin.get().getEmailAdmin());
        } else {
            logger.warn("Admin com ID {} não encontrado.", id);
        }
        return admin.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> adicionarAdmin(@RequestBody Admin admin, HttpServletRequest request) {
        logger.info("Pedido para adicionar novo admin: {}", admin.getEmailAdmin());
        if (!SecurityUtil.isAdmin(request)) {
            logger.warn("Acesso negado para adicionar admin.");
            return ResponseEntity.status(403).body("Acesso negado.");
        }
        try {
            admin.setPasswordAdmin(passwordEncoder.encode(admin.getPasswordAdmin()));
            Admin salvo = adminRepository.save(admin);
            logger.debug("Admin adicionado com sucesso: {}", salvo.getEmailAdmin());
            return ResponseEntity.ok(salvo);
        } catch (Exception e) {
            logger.error("Erro ao adicionar admin: {}", admin.getEmailAdmin(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao adicionar admin.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarAdmin(@PathVariable Integer id, @RequestBody Admin atualizado, HttpServletRequest request) {
        logger.info("Pedido para atualizar admin com ID {}", id);
        if (!SecurityUtil.isAdmin(request)) {
            logger.warn("Acesso negado para atualizar admin com ID {}", id);
            return ResponseEntity.status(403).body("Acesso negado.");
        }
        Admin existente = adminRepository.findById(id).orElse(null);
        if (existente == null) {
            logger.warn("Admin com ID {} não encontrado para atualização.", id);
            return ResponseEntity.notFound().build();
        }

        existente.setNomeAdmin(atualizado.getNomeAdmin());
        existente.setEmailAdmin(atualizado.getEmailAdmin());

        if (atualizado.getPasswordAdmin() != null &&
                !atualizado.getPasswordAdmin().isBlank()) {
            existente.setPasswordAdmin(
                    passwordEncoder.encode(atualizado.getPasswordAdmin())
            );
        }
        Admin salvo = adminRepository.save(existente);
        salvo.setPasswordAdmin(null);
        logger.debug("Admin com ID {} atualizado com sucesso.", id);
        return ResponseEntity.ok(salvo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> apagarAdmin(@PathVariable Integer id, HttpServletRequest request) {
        logger.info("Pedido para apagar admin com ID {}", id);
        if (!SecurityUtil.isAdmin(request)) {
            logger.warn("Acesso negado para apagar admin com ID {}", id);
            return ResponseEntity.status(403).body("Acesso negado.");
        }
        try {
            adminRepository.deleteById(id);
            logger.debug("Admin com ID {} apagado com sucesso.", id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Erro ao apagar admin com ID {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao apagar admin.");
        }
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<?> atualizarPassword(@PathVariable Integer id,
                                               @RequestBody Map<String, String> body,
                                               HttpServletRequest request) {
        logger.info("Pedido para atualizar password do admin com ID {}", id);
        Optional<Admin> adminOpt = adminRepository.findById(id);
        if (adminOpt.isEmpty()) {
            logger.warn("Admin com ID {} não encontrado para atualização de password.", id);
            return ResponseEntity.notFound().build();
        }

        Admin admin = adminOpt.get();

        if (!SecurityUtil.isProprio(request, admin.getEmailAdmin())) {
            logger.warn("Acesso negado para atualizar password do admin com ID {}", id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado.");
        }

        String atual = body.get("passwordAtual");
        String nova = body.get("novaPassword");

        if (atual == null || nova == null) {
            logger.warn("Campos obrigatórios em falta na atualização de password do admin com ID {}", id);
            return ResponseEntity.badRequest().body("Campos obrigatórios em falta.");
        }

        if (!passwordEncoder.matches(atual, admin.getPasswordAdmin())) {
            logger.warn("Password atual incorreta para admin com ID {}", id);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Password atual incorreta.");
        }

        admin.setPasswordAdmin(passwordEncoder.encode(nova));
        adminRepository.save(admin);

        logger.debug("Password do admin com ID {} atualizada com sucesso.", id);
        return ResponseEntity.ok("Password atualizada com sucesso.");
    }
}
