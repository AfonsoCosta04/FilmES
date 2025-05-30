package com.filmees.backend.controller;

import com.filmees.backend.model.*;
import com.filmees.backend.repository.*;
import com.filmees.backend.security.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.text.similarity.FuzzyScore;
import org.apache.commons.text.similarity.JaroWinklerSimilarity;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.*;

@RestController
@RequestMapping("/api/filmes")
@CrossOrigin(origins = "http://localhost:3000")
public class FilmeController {

    @Autowired
    private FilmeRepository filmeRepository;

    private static final Logger logger = LoggerFactory.getLogger(FilmeController.class);


    @GetMapping("/{id}")
    public ResponseEntity<?> obterFilmePorId(@PathVariable Integer id) {
        logger.info("Pedido para obter filme com ID: {}", id);
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
        logger.info("Filtrando filmes com genero: {}, ano: {}, duracao: {}", genero, ano, duracao);
        List<Filme> filmes = filmeRepository.filtrarFilmes(genero, ano, duracao);
        return ResponseEntity.ok(filmes);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> adicionarFilme(
            @RequestPart("filme") Filme filme,
            @RequestPart(value = "imagem", required = false) MultipartFile imagem,
            HttpServletRequest request) {

        if (!SecurityUtil.isAdmin(request) && !SecurityUtil.isFuncionario(request)) {
            logger.warn("Tentativa de adicionar filme sem permissão.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado.");
        }

        // Guardar imagem se existir
        if (imagem != null && !imagem.isEmpty()) {
            try {
                String nomeFicheiro = UUID.randomUUID() + "_" + imagem.getOriginalFilename();
                Path pathDestino = Paths.get("C:/FilmES/filme.s/public/imagens", nomeFicheiro);
                Files.copy(imagem.getInputStream(), pathDestino, StandardCopyOption.REPLACE_EXISTING);
                filme.setFoto("imagens/" + nomeFicheiro);
                logger.info("Imagem do filme guardada em: {}", pathDestino);
            } catch (IOException e) {
                logger.error("Erro ao guardar imagem do filme.", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao guardar imagem.");
            }
        }

        if (filme.getDisponivel() == null) filme.setDisponivel(true);
        if (filme.getPreco() == null) filme.setPreco(5.0);


        logger.info("Filme adicionado: {}", filme.getTitulo());
        return ResponseEntity.ok(filmeRepository.save(filme));
    }


    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> editarFilme(
            @PathVariable Integer id,
            @RequestPart("filme") Filme atualizado,
            @RequestPart(value = "imagem", required = false) MultipartFile imagem,
            HttpServletRequest request) {

        if (!SecurityUtil.isAdmin(request) && !SecurityUtil.isFuncionario(request)) {
            logger.warn("Tentativa de editar filme sem permissão.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado.");
        }

        Filme existente = filmeRepository.findById(id).orElse(null);
        if (existente == null) {
            logger.warn("Filme com ID {} não encontrado para edição.", id);
            return ResponseEntity.notFound().build();
        }

        // Atualizar imagem se existir
        if (imagem != null && !imagem.isEmpty()) {
            try {
                String imagemAntiga = existente.getFoto();
                if (imagemAntiga != null) {
                    Path pathAntigo = Paths.get("C:/FilmES/filme.s/public", imagemAntiga);
                    Files.deleteIfExists(pathAntigo);
                    logger.info("Imagem antiga do filme removida: {}", pathAntigo);
                }

                String nomeFicheiro = UUID.randomUUID() + "_" + imagem.getOriginalFilename();
                Path pathDestino = Paths.get("C:/FilmES/filme.s/public/imagens/", nomeFicheiro);
                Files.copy(imagem.getInputStream(), pathDestino, StandardCopyOption.REPLACE_EXISTING);
                atualizado.setFoto("imagens/" + nomeFicheiro);
                logger.info("Nova imagem do filme guardada em: {}", pathDestino);
            } catch (IOException e) {
                logger.error("Erro ao guardar nova imagem do filme.", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao guardar imagem.");
            }
        } else {
            logger.info("Foto do filme atualizada: {}", atualizado.getTitulo());
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

        logger.info("Filme atualizado: {}", atualizado.getTitulo());
        return ResponseEntity.ok(filmeRepository.save(atualizado));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> apagarFilme(@PathVariable Integer id, HttpServletRequest request) {
        logger.info("Pedido para apagar o filme com ID: {}", id);
        if (!SecurityUtil.isAdmin(request) && !SecurityUtil.isFuncionario(request)) {
            logger.warn("Acesso negado para apagar o filme com ID: {} - utilizador não autorizado", id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado.");
        }
        if (!filmeRepository.existsById(id)) {
            logger.warn("Filme com ID {} não encontrado para apagar.", id);
            return ResponseEntity.notFound().build();
        }

        Filme filme = filmeRepository.findById(id).get();
        if (filme.getFoto() != null) {
            try {
                // Caminho completo para a pasta public/imagens do React
                Path caminhoImagem = Paths.get("C:/FilmES/filme.s/public", filme.getFoto());
                Files.deleteIfExists(caminhoImagem);
                logger.info("Imagem '{}' do filme com ID {} apagada com sucesso.", filme.getFoto(), id);
            } catch (IOException e) {
                logger.error("Erro ao apagar a imagem '{}' do filme com ID {}: {}", filme.getFoto(), id, e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao apagar a imagem.");
            }
        }

        filmeRepository.deleteById(id);
        logger.info("Filme com ID {} apagado com sucesso.", id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/populares")
    public List<Filme> filmesPopularesUltimaSemana() {
        LocalDate dataLimite = LocalDate.now().minusDays(7);
        logger.info("Pedido para listar os filmes mais populares desde {}", dataLimite);

        logger.info("Pedido para listar os filmes mais populares desde {}", dataLimite);
        return filmeRepository.findMaisAlugadosUltimaSemana(dataLimite);
    }

    @PutMapping("/{id}/disponibilidade")
    public ResponseEntity<?> atualizarDisponibilidade(
            @PathVariable Integer id,
            @RequestBody Filme filmeAtualizado,
            HttpServletRequest request) {

        logger.info("Pedido para atualizar disponibilidade do filme com ID: {}", id);
        if (!SecurityUtil.isAdmin(request) && !SecurityUtil.isFuncionario(request)) {
            logger.warn("Acesso negado para atualizar disponibilidade do filme com ID: {} - utilizador não autorizado", id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado.");
        }

        Filme filme = filmeRepository.findById(id).orElse(null);
        if (filme == null) {
            logger.warn("Filme com ID {} não encontrado para atualizar disponibilidade.", id);
            return ResponseEntity.notFound().build();
        }

        filme.setDisponivel(filmeAtualizado.getDisponivel());
        logger.info("Disponibilidade do filme com ID {} atualizada para {}", id, filmeAtualizado.getDisponivel());
        return ResponseEntity.ok(filmeRepository.save(filme));
    }

    @GetMapping("/pesquisa")
    public ResponseEntity<?> pesquisarFilmes(@RequestParam(required = false) String termo) {
        logger.info("Pedido de pesquisa de filmes com termo: '{}'", termo);
        List<Filme> todosFilmes = filmeRepository.findAll();

        if (termo == null || termo.trim().isEmpty()) {
            logger.info("Termo de pesquisa vazio ou nulo - retornando todos os filmes.");
            return ResponseEntity.ok(todosFilmes);
        }

        String termoLower = termo.toLowerCase();
        LevenshteinDistance distancia = new LevenshteinDistance();
        JaroWinklerSimilarity jaro = new JaroWinklerSimilarity();

        List<Filme> filmesFiltrados = todosFilmes.stream()
                .filter(filme -> {
                    String tituloLower = filme.getTitulo().toLowerCase();

                    // Verificação direta
                    if (tituloLower.contains(termoLower)) return true;

                    String[] palavras = tituloLower.split("\\s+");
                    for (String palavra : palavras) {
                        if (palavra.length() < 4) continue; // Ignora palavras curtas

                        int dist = distancia.apply(palavra, termoLower);
                        double jaroSim = jaro.apply(palavra, termoLower);

                        if (dist <= 1 || jaroSim >= 0.88) return true;

                        // Verifica também substrings de palavras longas
                        if (palavra.length() >= termoLower.length() + 2) {
                            for (int i = 0; i <= palavra.length() - termoLower.length(); i++) {
                                String sub = palavra.substring(i, Math.min(i + termoLower.length() + 2, palavra.length()));
                                if (sub.length() < 4) continue;

                                dist = distancia.apply(sub, termoLower);
                                jaroSim = jaro.apply(sub, termoLower);
                                if (dist <= 1 || jaroSim >= 0.88) return true;
                            }
                        }
                    }

                    // Verificar o título completo apenas se tiver 4+ letras
                    if (tituloLower.length() >= 4) {
                        int distTotal = distancia.apply(tituloLower, termoLower);
                        double jaroTotal = jaro.apply(tituloLower, termoLower);
                        return distTotal <= 1 || jaroTotal >= 0.88;
                    }

                    return false;
                })
                .toList();

        logger.info("Foram encontrados {} filmes correspondentes ao termo '{}'", filmesFiltrados.size(), termo);
        return ResponseEntity.ok(filmesFiltrados);
    }
}
