package com.challenge.videos.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class MovieProducer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name="id_movie", referencedColumnName = "id_movie")
    @Fetch(FetchMode.JOIN)
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "id_producer", referencedColumnName = "id_producer")
    @Cascade(CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private Producer producer;


}
