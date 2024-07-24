package com.app.filestore.controller;

import com.app.filestore.dto.FileDtoRequest;
import com.app.filestore.dto.FileDtoResponse;
import com.app.filestore.service.FileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping
    public FileDtoResponse saveFile(@Valid FileDtoRequest fileDtoRequest) {
        return fileService.saveFile(fileDtoRequest);
    }
}
