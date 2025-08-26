package com.movieflix.service;

import com.movieflix.entity.movies.Movie;
import com.movieflix.repository.MovieRepository;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }


    public Movie criarFilme(Movie movie) {
        return movieRepository.save(movie);
    }



}
