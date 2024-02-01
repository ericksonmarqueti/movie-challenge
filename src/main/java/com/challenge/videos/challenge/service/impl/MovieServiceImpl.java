package com.challenge.videos.challenge.service.impl;

import com.challenge.videos.challenge.dto.IntervalAwardsResponseDTO;
import com.challenge.videos.challenge.dto.ProducerResponseDTO;
import com.challenge.videos.challenge.helper.CSVHelper;
import com.challenge.videos.challenge.model.Movie;
import com.challenge.videos.challenge.model.MovieProducer;
import com.challenge.videos.challenge.repository.MovieRepository;
import com.challenge.videos.challenge.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository repository;

    @Override
    public void loadMovies() {
        File csvFile = new File("src/main/resources/movielist.csv");
        try {
            InputStream targetStream = new FileInputStream(csvFile);;
            List<Movie> movies = CSVHelper.csvToMovie(targetStream);
            repository.saveAll(movies);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Movie> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Movie> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Movie create(Movie newMovie) {
        return repository.save(newMovie);
    }

    @Override
    public Movie update(Long id, Movie movie) {
        Optional<Movie> savedMovie = findById(id);
        if (savedMovie.isPresent()) {
            Movie updatedMovie = savedMovie.get();
            updatedMovie.setYear(movie.getYear());
            updatedMovie.setTitle(movie.getTitle());
            updatedMovie.setStudios(movie.getStudios());
            updatedMovie.setProducers(movie.getProducers());
            updatedMovie.setWinner(movie.isWinner());
            repository.save(updatedMovie);
            return updatedMovie;
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public IntervalAwardsResponseDTO getIntervals() {
        IntervalAwardsResponseDTO responseDTO = new IntervalAwardsResponseDTO();

        Map<String, LinkedList<Long>> winners = new HashMap<>();

        List<Movie> movies = findAll()
                .stream()
                .filter(m -> m.isWinner())
                .collect(Collectors.toList());

        for (Movie movie : movies) {
            for (MovieProducer movieProducer : movie.getProducers()) {
                String name = movieProducer.getProducer().getName();
                List<Long> wins = winners.get(name);
                if (wins != null) {
                    wins.add(movie.getYear());
                } else {
                    winners.put(name, new LinkedList<>(Arrays.asList(movie.getYear())));
                }
            }
        }

        for (Map.Entry<String, LinkedList<Long>> entry : winners.entrySet()) {
            List<Long> years = entry.getValue();
            Collections.sort(years);
            if (years.size() > 1) {
                List<Long> consecutiveYears = extractConsecutiveYears(years);
                if (consecutiveYears != null && !consecutiveYears.isEmpty()) {
                    responseDTO.addMin(createDTO(entry, consecutiveYears));
                }
                responseDTO.addMax(createDTO(entry, years));
            }
        }

        responseDTO.setMax(Arrays.asList(responseDTO.getMax().stream().max(Comparator.comparing(ProducerResponseDTO::getInterval)).get()));

        return responseDTO;
    }

    private ProducerResponseDTO createDTO(Map.Entry<String, LinkedList<Long>> entry, List<Long> years) {
        Long min = Collections.min(years);
        Long max = Collections.max(years);
        Long interval = max - min;

        ProducerResponseDTO dto = new ProducerResponseDTO();
        dto.setProducer(entry.getKey());
        dto.setInterval(interval);
        dto.setPreviousWin(min);
        dto.setFollowingWin(max);
        return dto;
    }

    private List<Long> extractConsecutiveYears(List<Long> years) {
        Set<Long> consecutiveYears = new HashSet<>();
        for (int i = 0; i < years.size() - 1; i++) {
            Long current = years.get(i);
            Long next = years.get(i + 1);
            if (current == next - 1) {
                consecutiveYears.add(current);
                consecutiveYears.add(next);
            }
        }
        return consecutiveYears.stream().collect(Collectors.toList());
    }
}

