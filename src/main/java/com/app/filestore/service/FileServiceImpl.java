package com.app.filestore.service;

import com.app.filestore.dto.FileDto;
import com.app.filestore.dto.request.FileCreateDtoRequest;
import com.app.filestore.dto.response.FileCreateDtoResponse;
import com.app.filestore.entity.FileEntity;
import com.app.filestore.exception.file.FileNotFoundException;
import com.app.filestore.mapper.FileMapper;
import com.app.filestore.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileMapper fileMapper;
    private final FileRepository fileRepository;

    @Transactional
    @Override
    public FileCreateDtoResponse saveFile(FileCreateDtoRequest fileCreateDtoRequest) {
        FileEntity entity = fileMapper.map(fileCreateDtoRequest);
        fileRepository.save(entity);
        log.info("Saved file: {}", entity);
        return new FileCreateDtoResponse(entity.getId());
    }

    @Transactional(readOnly = true)
    @Override
    public Page<FileDto> getFilePageSortedByCreationTime(int page, int size, String direction) {
        Sort sortByCreationTime = getSortByCreationTime(direction);
        Pageable pageable = PageRequest.of(page, size, sortByCreationTime);

        return fileRepository.findAll(pageable)
                .map(fileMapper::map);
    }

    @Transactional(readOnly = true)
    @Override
    public FileDto getFileById(Long id) {
        return fileRepository.findById(id)
                .map(fileMapper::map)
                .orElseThrow(() -> new FileNotFoundException("Failed to find file with id: " + id));
    }

    private Sort getSortByCreationTime(String direction) {
        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        return Sort.by(sortDirection, "creationTime");
    }
}
