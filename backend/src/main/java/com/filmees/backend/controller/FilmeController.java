package com.filmees.backend.controller;

import com.filmees.backend.model.*;
import com.filmees.backend.repository.*;
import com.filmees.backend.security.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public ResponseEntity<?> obterFilmePorId(@PathVariable Integer id) {
        Optional<Filme> filmeOptional = filmeRepository.findById(id);
        if (filmeOptional.isPresent()) {
            return ResponseEntity.ok(filmeOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Filme não encontrado.");
        }
    }

    @PostMapping
    public ResponseEntity<?> adicionarFilme(@RequestBody Filme filme, HttpServletRequest request) {
        if (!SecurityUtil.isAdmin(request) && !SecurityUtil.isFuncionario(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado.");
        }
        return ResponseEntity.ok(filmeRepository.save(filme));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> editarFilme(
            @PathVariable Integer id,
            @RequestPart("filme") Filme atualizado,
            @RequestPart(value = "imagem", required = false) MultipartFile imagem,
            HttpServletRequest request) {

        if (!SecurityUtil.isAdmin(request) && !SecurityUtil.isFuncionario(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado.");
        }

        if (!filmeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        if (imagem != null && !imagem.isEmpty()) {
            try {
                String nomeFicheiro = UUID.randomUUID() + "_" + imagem.getOriginalFilename();
                Path pathDestino = Paths.get("src/main/resources/static/imagens", nomeFicheiro);
                Files.copy(imagem.getInputStream(), pathDestino, StandardCopyOption.REPLACE_EXISTING);
                atualizado.setFoto("imagens/" + nomeFicheiro);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao guardar imagem.");
            }
        }

        atualizado.setIdFilme(id);
        return ResponseEntity.ok(filmeRepository.save(atualizado));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> apagarFilme(@PathVariable Integer id, HttpServletRequest request) {
        if (!SecurityUtil.isAdmin(request) && !SecurityUtil.isFuncionario(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado.");
        }
        if (!filmeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        filmeRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/populares")
    public List<Filme> filmesPopularesUltimaSemana() {
        LocalDate dataLimite = LocalDate.now().minusDays(7);
        return filmeRepository.findMaisAlugadosUltimaSemana(dataLimite);
    }

}
