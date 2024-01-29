package com.challenge.videos.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ProducerResponseDTO {

    private String producer;
    private Long interval;
    private Long previousWin;
    private Long followingWin;

}
