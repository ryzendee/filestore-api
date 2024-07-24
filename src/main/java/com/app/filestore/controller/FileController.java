package com.app.filestore.controller;

import com.app.filestore.dto.FileDto;
import com.app.filestore.dto.response.FileCreateDtoResponse;
import com.app.filestore.service.FileService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {

    private static final int MIN_PAGE = 0;
    private static final int MIN_PAGE_SIZE = 1;

    private final FileService fileService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FileCreateDtoResponse saveFile(@Valid @RequestBody com.app.filestore.dto.request.FileCreateDtoResponse fileCreateDtoResponse) {
        return fileService.saveFile(fileCreateDtoResponse);
    }

    @GetMapping
    public Page<FileDto> getFilePage(@RequestParam(defaultValue = "0") @Min(MIN_PAGE) int page,
                                     @RequestParam(defaultValue = "20") @Min(MIN_PAGE_SIZE) int size,
                                     @RequestParam(defaultValue = "asc") String direction) {

        if (!StringUtils.equalsAny(direction, "asc", "desc")) {
            throw new IllegalArgumentException("Direction should be equal to asc or desc. Actual value : " + direction);
        }

        return fileService.getFilePageSortedByCreationTime(page, size, direction);
    }

    @GetMapping("/{id}")
    public FileDto getFileById(@PathVariable Long id) {
        return fileService.getFileById(id);
    }
}
