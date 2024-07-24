package com.app.filestore.service;

import com.app.filestore.dto.FileDto;
import com.app.filestore.dto.request.FileCreateDtoRequest;
import com.app.filestore.dto.response.FileCreateDtoResponse;
import org.springframework.data.domain.Page;

public interface FileService {

    FileCreateDtoResponse saveFile(FileCreateDtoRequest fileCreateDtoRequest);
    Page<FileDto> getFilePageSortedByCreationTime(int page, int size, String direction);
    FileDto getFileById(Long id);
}
