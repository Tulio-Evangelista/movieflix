package com.movieflix.service;

import com.movieflix.entity.Streaming.Streaming;
import com.movieflix.entity.category.Category;
import com.movieflix.entity.movies.Movie;
import com.movieflix.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final CategoryService categoryService;
    private final StreamingService streamingService;

    public MovieService(MovieRepository movieRepository, CategoryService categoryService, StreamingService streamingService) {

        this.movieRepository = movieRepository;
        this.categoryService = categoryService;
        this.streamingService = streamingService;
    }




    public Movie criarFilme(Movie movie) {
       movie.setCategories(this.buscarCategorias(movie.getCategories()));
        movie.setStreamings(this.buscarStreaming(movie.getStreamings()));
        return movieRepository.save(movie);
    }



    private List<Category> buscarCategorias(List<Category> categories) {
        List<Category> categoriasEncontradas = new ArrayList<>();
        categories.forEach(category ->{
        List<Category>  lista  = categoryService.listarPorId(category.getId());
        if (!lista.isEmpty()) {
                categoriasEncontradas.addAll(lista);
          }
        });
        return categoriasEncontradas;
    }

    private List<Streaming> buscarStreaming(List<Streaming> streamings) {
        List<Streaming> streamingsEncontrados = new ArrayList<>();
        streamings.forEach(streaming ->{
            List<Streaming>  lista  = streamingService.listarPorId(streaming.getId());
            if (!lista.isEmpty()) {
                streamingsEncontrados.addAll(lista);
            }
        });
        return streamingsEncontrados;
    }

    public Optional<Movie> listarPorId(Long id) {
        return movieRepository.findById(id);
    }

    public List<Movie> listarTodos() {
        return movieRepository.findAll();
    }

    public void deletarPorId(Long id) {
        movieRepository.deleteById(id);
    }

    public Movie atualizarFilmePorId(Long id, Movie movieAtualizado) {
        return movieRepository.findById(id)
                .map(movie -> {
                    movie.setName(movieAtualizado.getName());
                    movie.setDescription(movieAtualizado.getDescription());
                    movie.setReleaseDate(movieAtualizado.getReleaseDate());
                    movie.setRating(movieAtualizado.getRating());
                    movie.setCategories(this.buscarCategorias(movieAtualizado.getCategories()));
                    movie.setStreamings(this.buscarStreaming(movieAtualizado.getStreamings()));
                    return movieRepository.save(movie);
                })
                .orElseThrow(() -> new RuntimeException("Filme não encontrado com id: " + id));
    }

    public List<Movie> buscarPorNome(String nome) {
        return movieRepository.findByNameContainingIgnoreCase(nome);
    }

    public List<Movie> buscarPorRating(double rating) {
        return movieRepository.findAll().stream()
                .filter(movie -> movie.getRating() >= rating)
                .toList();
    }

    public List<Movie> buscarPorCategoria(String Categoryname) {
        return movieRepository.findAll().stream()
                        .filter(movie -> movie.getCategories().stream()
                        .anyMatch(category -> category.getName().equalsIgnoreCase(Categoryname)))
                .toList();
    }

    public List<Movie> buscarPorStreaming(String name) {
        return movieRepository.findAll().stream()
                .filter(movie -> movie.getStreamings().stream()
                        .anyMatch(streaming -> streaming.getName().equalsIgnoreCase(name)))
                .toList();
    }
}
