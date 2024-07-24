package com.app.filestore.service;

import com.app.filestore.dto.FileDtoRequest;
import com.app.filestore.dto.FileDtoResponse;
import com.app.filestore.entity.FileEntity;
import com.app.filestore.mapper.FileMapper;
import com.app.filestore.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileMapper fileMapper;
    private final FileRepository fileRepository;

    @Override
    public FileDtoResponse saveFile(FileDtoRequest fileDtoRequest) {
        FileEntity entity = fileMapper.map(fileDtoRequest);
        fileRepository.save(entity);

        return new FileDtoResponse(entity.getId());
    }
}
