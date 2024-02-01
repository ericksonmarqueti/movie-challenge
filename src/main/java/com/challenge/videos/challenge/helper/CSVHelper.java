package com.challenge.videos.challenge.helper;

import com.challenge.videos.challenge.model.Movie;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static org.apache.logging.log4j.util.Strings.isNotBlank;

@Component
public class CSVHelper {

    public static List<Movie> csvToMovie(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim().withDelimiter(';'));) {

            List<Movie> movies = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                Movie movie = new Movie();
                movie.setYear(Long.parseLong(csvRecord.get(0)));
                movie.setTitle(csvRecord.get(1));
                movie.setStudios(csvRecord.get(2));
                String producers = csvRecord.get(3);
                if (isNotBlank(producers)) {
                    String[] arrayProducers = producers.replaceAll(" and ", ",")
                                                       .split(",", -1);

                    for (String producer : arrayProducers) {
                        movie.addProducer(producer.trim());
                    }
                }
                movie.setWinner(isNotBlank(csvRecord.get(4)));
                movies.add(movie);
            }

            return movies;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
}
