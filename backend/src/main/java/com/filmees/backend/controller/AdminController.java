package com.filmees.backend.controller;

import com.filmees.backend.model.Admin;
import com.filmees.backend.repository.AdminRepository;
import com.filmees.backend.security.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/admins")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

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
        atualizado.setIdAdmin(id);
        return ResponseEntity.ok(adminRepository.save(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> apagarAdmin(@PathVariable Integer id, HttpServletRequest request) {
        if (!SecurityUtil.isAdmin(request)) {
            return ResponseEntity.status(403).body("Acesso negado.");
        }
        adminRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
