package com.app.filestore.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record FileDtoRequest(
        String file,
        String title,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate creationDate
) {
}
