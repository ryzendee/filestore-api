package com.app.filestore.service;

import com.app.filestore.dto.FileDto;
import com.app.filestore.dto.FileDtoRequest;
import com.app.filestore.dto.FileDtoResponse;
import com.app.filestore.entity.FileEntity;
import com.app.filestore.exception.file.FileNotFoundException;
import com.app.filestore.mapper.FileMapper;
import com.app.filestore.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileMapper fileMapper;
    private final FileRepository fileRepository;

    @Transactional
    @Override
    public FileDtoResponse saveFile(FileDtoRequest fileDtoRequest) {
        FileEntity entity = fileMapper.map(fileDtoRequest);
        fileRepository.save(entity);

        return new FileDtoResponse(entity.getId());
    }

    @Transactional(readOnly = true)
    @Override
    public FileDto getFileById(Long id) {
        return fileRepository.findById(id)
                .map(fileMapper::map)
                .orElseThrow(() -> new FileNotFoundException("Failed to find file with id: " + id));
    }
}
