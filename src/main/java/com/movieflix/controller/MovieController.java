package com.movieflix.controller;

import com.movieflix.controller.request.MovieRequest;
import com.movieflix.controller.response.MovieResponse;
import com.movieflix.entity.movies.Movie;
import com.movieflix.mapper.MovieMapper;
import com.movieflix.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movieflix/movies")
@RequiredArgsConstructor
public class MovieController {

    MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }


    @PostMapping("/criarMovie")
    public ResponseEntity<MovieResponse> criarMovie(@RequestBody MovieRequest movieRequest) {
        Movie movieCriado = movieService.criarFilme(MovieMapper.toMovie(movieRequest));
        return ResponseEntity.ok(MovieMapper.toMovieResponse(movieCriado));
    }







}
