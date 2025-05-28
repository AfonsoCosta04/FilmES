package com.filmees.backend.controller;

import com.filmees.backend.model.Admin;
import com.filmees.backend.model.Cliente;
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

    @GetMapping
    public ResponseEntity<?> listarAdmins(HttpServletRequest request) {
        if (!SecurityUtil.isAdmin(request)) {
            return ResponseEntity.status(403).body("Acesso negado.");
        }
        return ResponseEntity.ok(adminRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obterAdmin(@PathVariable Integer id, HttpServletRequest request) {
        if (!SecurityUtil.isAdmin(request)) {
            return ResponseEntity.status(403).body("Acesso negado.");
        }
        Optional<Admin> admin = adminRepository.findById(id);
        return admin.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> adicionarAdmin(@RequestBody Admin admin, HttpServletRequest request) {
        if (!SecurityUtil.isAdmin(request)) {
            return ResponseEntity.status(403).body("Acesso negado.");
        }
        admin.setPasswordAdmin(passwordEncoder.encode(admin.getPasswordAdmin()));
        return ResponseEntity.ok(adminRepository.save(admin));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarAdmin(@PathVariable Integer id, @RequestBody Admin atualizado, HttpServletRequest request) {
        if (!SecurityUtil.isAdmin(request)) {
            return ResponseEntity.status(403).body("Acesso negado.");
        }
        Admin existente = adminRepository.findById(id).orElse(null);
        if (existente == null) {
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
        return ResponseEntity.ok(salvo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> apagarAdmin(@PathVariable Integer id, HttpServletRequest request) {
        if (!SecurityUtil.isAdmin(request)) {
            return ResponseEntity.status(403).body("Acesso negado.");
        }
        adminRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<?> atualizarPassword(@PathVariable Integer id,
                                               @RequestBody Map<String, String> body,
                                               HttpServletRequest request) {
        Optional<Admin> adminOpt = adminRepository.findById(id);
        if (adminOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Admin admin = adminOpt.get();

        if (!SecurityUtil.isProprio(request, admin.getEmailAdmin())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado.");
        }

        String atual =body.get("passwordAtual");
        String nova = body.get("novaPassword");

        if (atual == null || nova == null) {
            return ResponseEntity.badRequest().body("Campos obrigatórios em falta.");
        }

        if (!passwordEncoder.matches(atual, admin.getPasswordAdmin())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Password atual incorreta.");
        }

        admin.setPasswordAdmin(passwordEncoder.encode(nova));
        adminRepository.save(admin);

        return ResponseEntity.ok("Password atualizada com sucesso.");
    }
}
