package dev.orisha.kafka_tutorial.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigClientResponse {

    private String message;
    private Object data;

}
