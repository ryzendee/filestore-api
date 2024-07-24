package com.app.filestore.service;

import com.app.filestore.dto.FileDtoRequest;
import com.app.filestore.dto.FileDtoResponse;

public interface FileService {

    FileDtoResponse saveFile(FileDtoRequest fileDtoRequest);
}
