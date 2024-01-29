package com.challenge.videos.challenge.helper;

import antlr.StringUtils;
import com.challenge.videos.challenge.model.Movie;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
                movie.setProducers(csvRecord.get(3));
                movie.setWinner(Strings.isNotBlank(csvRecord.get(4)));
                movies.add(movie);
            }

            return movies;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
}
