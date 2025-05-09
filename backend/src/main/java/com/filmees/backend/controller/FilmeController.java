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

        Filme existente = filmeRepository.findById(id).orElse(null);
        if (existente == null) return ResponseEntity.notFound().build();

        // Atualizar imagem se existir
        if (imagem != null && !imagem.isEmpty()) {
            try {
                String nomeFicheiro = UUID.randomUUID() + "_" + imagem.getOriginalFilename();
                Path pathDestino = Paths.get("src/main/resources/static/imagens", nomeFicheiro);
                Files.copy(imagem.getInputStream(), pathDestino, StandardCopyOption.REPLACE_EXISTING);
                atualizado.setFoto("imagens/" + nomeFicheiro);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao guardar imagem.");
            }
        } else {
            atualizado.setFoto(existente.getFoto());
        }

        // Preencher campos obrigatórios e opcionais ausentes
        atualizado.setIdFilme(id);
        if (atualizado.getTitulo() == null) atualizado.setTitulo(existente.getTitulo());
        if (atualizado.getAno() == null) atualizado.setAno(existente.getAno());
        if (atualizado.getDuracaoHoras() == null) atualizado.setDuracaoHoras(existente.getDuracaoHoras());
        if (atualizado.getDuracaoMinutos() == null) atualizado.setDuracaoMinutos(existente.getDuracaoMinutos());
        if (atualizado.getImdb() == null) atualizado.setImdb(existente.getImdb());
        if (atualizado.getRottenTomatoes() == null) atualizado.setRottenTomatoes(existente.getRottenTomatoes());
        if (atualizado.getGenero1() == null) atualizado.setGenero1(existente.getGenero1());
        if (atualizado.getGenero2() == null) atualizado.setGenero2(existente.getGenero2());
        if (atualizado.getGenero3() == null) atualizado.setGenero3(existente.getGenero3());
        if (atualizado.getAtor1() == null) atualizado.setAtor1(existente.getAtor1());
        if (atualizado.getAtor2() == null) atualizado.setAtor2(existente.getAtor2());
        if (atualizado.getAtor3() == null) atualizado.setAtor3(existente.getAtor3());
        if (atualizado.getSinopse() == null) atualizado.setSinopse(existente.getSinopse());
        if (atualizado.getPreco() == null) atualizado.setPreco(existente.getPreco());
        if (atualizado.getDisponivel() == null) atualizado.setDisponivel(existente.getDisponivel());
        if (atualizado.getIdadeRecomendada() == null) atualizado.setIdadeRecomendada(existente.getIdadeRecomendada());

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
