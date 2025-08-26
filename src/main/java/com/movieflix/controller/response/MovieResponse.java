package com.movieflix.controller.response;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record MovieResponse (Long id,
                             String name,
                             String description,
                             LocalDate releaseDate,
                             double rating,
                             LocalDateTime createdAt,
                             LocalDateTime updatedAt,
                             List<CategoryResponse> categories,
                             List<StreamingResponse> streamings)  {
}
