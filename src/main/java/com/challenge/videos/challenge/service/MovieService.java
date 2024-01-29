package com.challenge.videos.challenge.service;

import com.challenge.videos.challenge.dto.IntervalAwardsResponseDTO;
import com.challenge.videos.challenge.model.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    void loadMovies();

    List<Movie> findAll();

    Optional<Movie> findById(Long id);

    Movie create(Movie newMovie);

    Movie update(Long id, Movie movie);

    void delete(Long id);

    void deleteAll();

    IntervalAwardsResponseDTO getIntervals();
}
