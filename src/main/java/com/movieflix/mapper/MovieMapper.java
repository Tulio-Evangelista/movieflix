package com.movieflix.mapper;


import com.movieflix.controller.request.MovieRequest;
import com.movieflix.controller.response.CategoryResponse;
import com.movieflix.controller.response.MovieResponse;
import com.movieflix.controller.response.StreamingResponse;
import com.movieflix.entity.Streaming.Streaming;
import com.movieflix.entity.category.Category;
import com.movieflix.entity.movies.Movie;
import lombok.experimental.UtilityClass;
import org.apache.coyote.Request;

import java.util.List;

@UtilityClass
public class MovieMapper {



    public static Movie toMovie(MovieRequest request) {

        List<Category> categories = request.categories().stream()
                .map(categoryId -> Category.builder().id(categoryId).build())
                .toList();

        List<Streaming> streamings = request.streamings().stream()
                .map(streamingId -> Streaming.builder().id(streamingId).build())
                .toList();

        return Movie.builder()
                .name(request.name())
                .description(request.description())
                .releaseDate(request.releaseDate())
                .rating(request.rating())
                .categories(categories)
                .streamings(streamings)
                .build();

    }

    public static MovieResponse toMovieResponse(Movie movie) {
        List<CategoryResponse> categories = movie.getCategories()
                .stream()
                .map(Category -> CategoryMapper.toCategoryResponse(Category))
                .toList();

        List<StreamingResponse> streamings = movie.getStreamings().stream()
                .map(Streaming -> StreamingMapper.toStreamingResponse(Streaming))
                .toList();

        return MovieResponse.builder()
                .id(movie.getId())
                .name(movie.getName())
                .description(movie.getDescription())
                .releaseDate(movie.getReleaseDate())
                .rating(movie.getRating())
                .createdAt(movie.getCreatedAt())
                .updatedAt(movie.getUpdatedAt())
                .categories(categories)
                .streamings(streamings)
                .build();
    }

}
