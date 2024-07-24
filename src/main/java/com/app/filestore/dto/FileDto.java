package com.app.filestore.dto;

import java.time.LocalDate;

public record FileDto(
        String file,
        String title,
        String description,
        LocalDate creationTime
) {
}
