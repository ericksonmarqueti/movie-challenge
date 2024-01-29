package com.challenge.videos.challenge.config;

import com.challenge.videos.challenge.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class LoadDatabase implements CommandLineRunner {

    private final MovieService movieService;

    @Autowired
    public LoadDatabase(MovieService movieService) {
        this.movieService = movieService;
    }

    @Override
    public void run(String... args) throws Exception {
        movieService.loadMovies();
    }
}
