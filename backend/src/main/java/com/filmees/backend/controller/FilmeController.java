package com.filmees.backend.controller;

import com.filmees.backend.model.*;
import com.filmees.backend.repository.*;
import com.filmees.backend.security.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.text.similarity.LevenshteinDistance;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/filmes")
@CrossOrigin(origins = "http://localhost:3000")
public class FilmeController {

    @Autowired
    private FilmeRepository filmeRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> obterFilmePorId(@PathVariable Integer id) {
        Optional<Filme> filmeOptional = filmeRepository.findById(id);
        if (filmeOptional.isPresent()) {
            return ResponseEntity.ok(filmeOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Filme não encontrado.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Filme>> filtrarFilmes(
            @RequestParam(required = false) String genero,
            @RequestParam(required = false) String ano,
            @RequestParam(required = false) String duracao
    ) {
        List<Filme> filmes = filmeRepository.filtrarFilmes(genero, ano, duracao);
        return ResponseEntity.ok(filmes);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> adicionarFilme(
            @RequestPart("filme") Filme filme,
            @RequestPart(value = "imagem", required = false) MultipartFile imagem,
            HttpServletRequest request) {

        if (!SecurityUtil.isAdmin(request) && !SecurityUtil.isFuncionario(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado.");
        }

        // Guardar imagem se existir
        if (imagem != null && !imagem.isEmpty()) {
            try {
                String nomeFicheiro = UUID.randomUUID() + "_" + imagem.getOriginalFilename();
                Path pathDestino = Paths.get("C:/FilmES/filme.s/public/imagens", nomeFicheiro);
                Files.copy(imagem.getInputStream(), pathDestino, StandardCopyOption.REPLACE_EXISTING);
                filme.setFoto("imagens/" + nomeFicheiro);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao guardar imagem.");
            }
        }

        if (filme.getDisponivel() == null) filme.setDisponivel(true);
        if (filme.getPreco() == null) filme.setPreco(5.0);


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
                String imagemAntiga = existente.getFoto();
                if (imagemAntiga != null) {
                    Path pathAntigo = Paths.get("C:/FilmES/filme.s/public", imagemAntiga);
                    Files.deleteIfExists(pathAntigo);
                }

                String nomeFicheiro = UUID.randomUUID() + "_" + imagem.getOriginalFilename();
                Path pathDestino = Paths.get("C:/FilmES/filme.s/public/imagens/", nomeFicheiro);
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

        Filme filme = filmeRepository.findById(id).get();
        if (filme.getFoto() != null) {
            try {
                // Caminho completo para a pasta public/imagens do React
                Path caminhoImagem = Paths.get("C:/FilmES/filme.s/public", filme.getFoto());
                Files.deleteIfExists(caminhoImagem);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao apagar a imagem.");
            }
        }

        filmeRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/populares")
    public List<Filme> filmesPopularesUltimaSemana() {
        LocalDate dataLimite = LocalDate.now().minusDays(7);
        return filmeRepository.findMaisAlugadosUltimaSemana(dataLimite);
    }

    @PutMapping("/{id}/disponibilidade")
    public ResponseEntity<?> atualizarDisponibilidade(
            @PathVariable Integer id,
            @RequestBody Filme filmeAtualizado,
            HttpServletRequest request) {

        if (!SecurityUtil.isAdmin(request) && !SecurityUtil.isFuncionario(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado.");
        }

        Filme filme = filmeRepository.findById(id).orElse(null);
        if (filme == null) return ResponseEntity.notFound().build();

        filme.setDisponivel(filmeAtualizado.getDisponivel());
        return ResponseEntity.ok(filmeRepository.save(filme));
    }

    @GetMapping("/pesquisa")
    public ResponseEntity<?> pesquisarFilmes(@RequestParam(required = false) String termo) {
        List<Filme> todosFilmes = filmeRepository.findAll();

        if (termo == null || termo.trim().isEmpty()) {
            return ResponseEntity.ok(todosFilmes); // retorna todos
        }

        String termoLower = termo.toLowerCase();
        LevenshteinDistance distancia = new LevenshteinDistance();

        List<Filme> filmesFiltrados = todosFilmes.stream()
                .filter(filme -> {
                    String tituloLower = filme.getTitulo().toLowerCase();
                    int dist = distancia.apply(tituloLower, termoLower);
                    return tituloLower.contains(termoLower) || dist <= 2;
                })
                .toList();

        return ResponseEntity.ok(filmesFiltrados);
    }
}
