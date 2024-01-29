package com.challenge.videos.challenge.repository;

import com.challenge.videos.challenge.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {

}
