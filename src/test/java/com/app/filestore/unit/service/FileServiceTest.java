package com.app.filestore.unit.service;

import com.app.filestore.dto.FileDto;
import com.app.filestore.dto.FileDtoRequest;
import com.app.filestore.dto.FileDtoResponse;
import com.app.filestore.entity.FileEntity;
import com.app.filestore.exception.file.FileNotFoundException;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

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
        FileDtoRequest dtoRequest = new FileDtoRequest("sadXZcvklnsd", "title", "descr", LocalDate.now());
        FileEntity fileEntity = new FileEntity(1L, dtoRequest.file(), dtoRequest.title(), dtoRequest.description(), dtoRequest.creationTime());

        when(fileMapper.map(dtoRequest))
                .thenReturn(fileEntity);
        when(fileRepository.save(fileEntity))
                .thenReturn(fileEntity);

        FileDtoResponse fileDtoResponse = fileService.saveFile(dtoRequest);

        verify(fileMapper).map(dtoRequest);
        verify(fileRepository).save(fileEntity);
        assertThat(fileDtoResponse.id()).isEqualTo(fileEntity.getId());
    }

    @DisplayName("Get file by id: should return file")
    @Test
    void getFileById_existsId_shouldReturnExpectedFileDto() {
        Long id = 1L;
        FileEntity entity = new FileEntity(id, "cxvlews", "title", "descr", LocalDate.now());
        FileDto expectedDto = new FileDto(entity.getFile(), entity.getTitle(), entity.getDescription(), entity.getCreationTime());

        when(fileRepository.findById(id))
                .thenReturn(Optional.of(entity));
        when(fileMapper.map(entity))
                .thenReturn(expectedDto);

        FileDto actualDto = fileService.getFileById(id);

        verify(fileRepository).findById(id);
        verify(fileMapper).map(entity);
        assertThat(actualDto).isEqualTo(expectedDto);
    }

    @DisplayName("Get file by id: throw FileNotFoundEx")
    @Test
    void getFileById_nonExistsId_shouldThrowFileNotFoundEx() {
        Long id = 1L;

        when(fileRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> fileService.getFileById(id))
                .isInstanceOf(FileNotFoundException.class);

        verify(fileRepository).findById(id);
        verify(fileMapper, never()).map(any(FileEntity.class));
    }
}
