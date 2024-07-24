package com.app.filestore.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record FileDtoRequest(
        @NotBlank(message = "file must not be blank")
        String file,
        @NotBlank(message = "title must not be blank")
        String title,
        @NotNull(message = "creationTime must not be null")
        @PastOrPresent(message = "creationTime must be in the past or in the present")
        LocalDate creationTime
) {
}
