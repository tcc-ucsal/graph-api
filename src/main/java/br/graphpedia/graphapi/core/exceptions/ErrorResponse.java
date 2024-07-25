package br.graphpedia.graphapi.core.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorResponse {

    private int status;
    private String message;
    private Throwable cause;
    private LocalDateTime timestamp;

}
