package com.challenge.videos.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class IntervalAwardsResponseDTO {

    private List<ProducerResponseDTO> max = new ArrayList<>();
    private List<ProducerResponseDTO> min = new ArrayList<>();

    public void addMin(ProducerResponseDTO producerResponseDTO) {
        this.min.add(producerResponseDTO);
    }

    public void addMax(ProducerResponseDTO producerResponseDTO) {
        this.max.add(producerResponseDTO);
    }
}
