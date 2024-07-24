package com.app.filestore.controller;

import com.app.filestore.dto.FileDtoRequest;
import com.app.filestore.dto.FileDtoResponse;
import com.app.filestore.service.FileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FileDtoResponse saveFile(@Valid @RequestBody FileDtoRequest fileDtoRequest) {
        return fileService.saveFile(fileDtoRequest);
    }
}
