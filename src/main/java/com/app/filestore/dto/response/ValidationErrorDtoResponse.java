package com.app.filestore.dto.response;

import java.util.List;

public record ValidationErrorDtoResponse(List<String> messages) {
}
