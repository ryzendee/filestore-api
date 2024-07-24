package com.app.filestore.unit.service;

import com.app.filestore.dto.FileDtoRequest;
import com.app.filestore.dto.FileDtoResponse;
import com.app.filestore.entity.FileEntity;
import com.app.filestore.mapper.FileMapper;
import com.app.filestore.repository.FileRepository;
import com.app.filestore.service.FileServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FileServiceTest {

    @InjectMocks
    private FileServiceImpl fileService;

    @Mock
    private FileMapper fileMapper;

    @Mock
    private FileRepository fileRepository;

    @DisplayName("Save file: should save file and return file response")
    @Test
    void saveFile_noCondition_shouldSaveAndReturnFileResponse() {
        FileDtoRequest dtoRequest = new FileDtoRequest("sadXZcvklnsd", "title", LocalDate.now());
        FileEntity fileEntity = new FileEntity(1L, dtoRequest.file(), dtoRequest.title(), dtoRequest.creationTime());

        when(fileMapper.map(dtoRequest))
                .thenReturn(fileEntity);
        when(fileRepository.save(fileEntity))
                .thenReturn(fileEntity);

        FileDtoResponse fileDtoResponse = fileService.saveFile(dtoRequest);

        verify(fileMapper).map(dtoRequest);
        verify(fileRepository).save(fileEntity);
        assertThat(fileDtoResponse.id()).isEqualTo(fileEntity.getId());
    }
}
