package com.movieflix.controller;

import com.movieflix.controller.request.MovieRequest;
import com.movieflix.controller.response.MovieResponse;
import com.movieflix.controller.response.StreamingResponse;
import com.movieflix.entity.movies.Movie;
import com.movieflix.mapper.MovieMapper;
import com.movieflix.mapper.StreamingMapper;
import com.movieflix.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movieflix/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {

        this.movieService = movieService;
    }


    @PostMapping("/criarMovie")
    public ResponseEntity<MovieResponse> criarMovie(@RequestBody MovieRequest movieRequest) {
        Movie movieCriado = movieService.criarFilme(MovieMapper.toMovie(movieRequest));
        return ResponseEntity.ok(MovieMapper.toMovieResponse(movieCriado));
    }

    @GetMapping("/listarMovies")
    public ResponseEntity<List<MovieResponse>> listarTodosStreamings(){
        List<MovieResponse> lista = movieService.listarTodos().stream()
                .map(movie -> MovieMapper.toMovieResponse(movie))
                .toList();
        return ResponseEntity.ok(lista);
    }

    @DeleteMapping("/DeletarMovie/{id}")
    public void deletarMovie(@PathVariable Long id) {
        movieService.deletarPorId(id);
    }

    @GetMapping("/listarPorId/{id}")
    public ResponseEntity <List<MovieResponse>> listarPorId(@PathVariable Long id) {
        List<MovieResponse> lista = movieService.listarPorId(id).stream()
                .map(movie -> MovieMapper.toMovieResponse(movie))
                .toList();
        if (lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/buscarPorNome/{name}")
    public ResponseEntity<List<MovieResponse>> buscarPorNome(@PathVariable String name) {
        List<Movie> movies = movieService.buscarPorNome(name);
        if (movies.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movies.stream()
                .map(movie -> MovieMapper.toMovieResponse(movie))
                .toList());
    }

    @GetMapping("/buscarPorCategoria/{categoryName}")
    public ResponseEntity<List<MovieResponse>> buscarPorCategoria(@PathVariable String categoryName) {
        List<Movie> movies = movieService.buscarPorCategoria(categoryName);
        if (movies.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movies.stream()
                .map(movie -> MovieMapper.toMovieResponse(movie))
                .toList());
    }

    @PutMapping("/atualizarMovie/{id}")
    public ResponseEntity<MovieResponse> atualizarMovie(@PathVariable Long id, @RequestBody MovieRequest movieRequest) {
        Movie movieAtualizado = movieService.atualizarFilmePorId(id, MovieMapper.toMovie(movieRequest));
        if (movieAtualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(MovieMapper.toMovieResponse(movieAtualizado));
    }

    @GetMapping("/listarMoviesPorStreaming/{streaming}")
    public ResponseEntity<List<MovieResponse>> listarMoviesPorStreaming(@PathVariable String streaming) {
        List<MovieResponse> lista = movieService.buscarPorStreaming(streaming).stream()
                .map(movie -> MovieMapper.toMovieResponse(movie))
                .toList();
        if (lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }




}
