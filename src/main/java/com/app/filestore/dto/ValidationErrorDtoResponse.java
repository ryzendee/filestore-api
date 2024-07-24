package com.app.filestore.dto;

import java.util.List;

public record ValidationErrorDtoResponse(List<String> messages) {
}
