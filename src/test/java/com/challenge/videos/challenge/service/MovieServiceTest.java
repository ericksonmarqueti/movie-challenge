package com.challenge.videos.challenge.service;

import com.challenge.videos.challenge.dto.IntervalAwardsResponseDTO;
import com.challenge.videos.challenge.dto.ProducerResponseDTO;
import com.challenge.videos.challenge.helper.CSVHelper;
import com.challenge.videos.challenge.model.Movie;
import com.challenge.videos.challenge.repository.MovieRepository;
import com.challenge.videos.challenge.service.impl.MovieServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    @InjectMocks
    private MovieServiceImpl movieService;

    @Mock
    private MovieRepository movieRepository;

    @Test
    void shouldCallCorrectly() {
        List<Movie> movies = null;

        try {
            File csvFile = new File("src/main/resources/movielist.csv");
            InputStream targetStream = new FileInputStream(csvFile);;
            movies = CSVHelper.csvToMovie(targetStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        when(movieService.findAll())
                .thenReturn(movies);

        IntervalAwardsResponseDTO intervals = movieService.getIntervals();

        assertProducer("Allan Carr", 1, 1980, 1981, intervals.getMin().get(0));
        assertProducer("Bo Derek", 6, 1984, 1990, intervals.getMax().get(0));
        assertProducer("Allan Carr", 4, 1980, 1984, intervals.getMax().get(1));
    }

    private static void assertProducer(String producer, int interval, int previousWin, int followingWin, ProducerResponseDTO producerDTO) {
        // assert producer's data
        assertEquals(producer, producerDTO.getProducer());
        assertEquals(interval, producerDTO.getInterval());
        assertEquals(previousWin, producerDTO.getPreviousWin());
        assertEquals(followingWin, producerDTO.getFollowingWin());
    }
}
