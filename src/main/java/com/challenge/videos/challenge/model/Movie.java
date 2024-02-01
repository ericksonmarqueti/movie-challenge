package com.challenge.videos.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static org.apache.logging.log4j.util.Strings.isNotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_movie")
    private long id;

    @Column(name = "year")
    private long year;

    @Column(name = "title")
    private String title;

    @Column(name = "studios")
    private String studios;

    @Column(name = "winner")
    private boolean winner;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovieProducer> producers = new ArrayList<>();

    public void addProducer(String s) {
        if (isNotBlank(s)) {
            Producer producer = new Producer();
            producer.setName(s);

            MovieProducer movieProducer = new MovieProducer();
            movieProducer.setMovie(this);
            movieProducer.setProducer(producer);
            producers.add(movieProducer);
        }
    }

}
