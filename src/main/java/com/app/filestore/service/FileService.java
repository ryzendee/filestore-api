package com.app.filestore.service;

import com.app.filestore.dto.FileDto;
import com.app.filestore.dto.FileDtoRequest;
import com.app.filestore.dto.FileDtoResponse;
import org.springframework.data.domain.Page;

public interface FileService {

    FileDtoResponse saveFile(FileDtoRequest fileDtoRequest);
    Page<FileDto> getFilePageSortedByCreationTime(int page, int size, String direction);
    FileDto getFileById(Long id);
}
