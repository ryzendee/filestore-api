package com.app.filestore.unit.controller;

import com.app.filestore.controller.FileController;
import com.app.filestore.dto.FileDto;
import com.app.filestore.dto.request.FileCreateDtoRequest;
import com.app.filestore.dto.response.FileCreateDtoResponse;
import com.app.filestore.exception.file.FileNotFoundException;
import com.app.filestore.service.FileService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Named.named;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.*;

@WebMvcTest(FileController.class)
@ExtendWith(MockitoExtension.class)
class FileControllerTest {

    private static final String BASE_URL = "/api/v1/files";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileService fileService;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
        RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @DisplayName("Save file: should save file and return id with status created")
    @Test
    void saveFile_validRequest_statusCreated() {
        FileCreateDtoRequest dtoRequest = new FileCreateDtoRequest("asdkxzcWiksadm", "test-title", "descr", LocalDate.now());
        FileCreateDtoResponse expectedResponse = new FileCreateDtoResponse(1L);
        int expectedId = expectedResponse.id().intValue();

        when(fileService.saveFile(dtoRequest))
                .thenReturn(expectedResponse);

        given()
                .contentType(ContentType.JSON)
                .body(dtoRequest)
                .when()
                .post(BASE_URL)
                .then()
                .status(HttpStatus.CREATED)
                .body("id", equalTo(expectedId));

        verify(fileService).saveFile(dtoRequest);
    }

    @DisplayName("Save file: invalid file and then status bad request")
    @MethodSource("getArgsForInvalidFile")
    @ParameterizedTest
    void saveFile_invalidFile_statusBadRequest(String invalidFile) {
        FileCreateDtoRequest dtoRequest = new FileCreateDtoRequest(invalidFile, "title", "descr", LocalDate.now());

        given()
                .contentType(ContentType.JSON)
                .body(dtoRequest)
                .when()
                .post(BASE_URL)
                .then()
                .status(HttpStatus.BAD_REQUEST);

        verify(fileService, never()).saveFile(dtoRequest);
    }

    @DisplayName("Save file: invalid title and then status bad request")
    @MethodSource("getArgsForInvalidTitle")
    @ParameterizedTest
    void saveFile_invalidTitle_statusBadRequest(String invalidTitle) {
        FileCreateDtoRequest dtoRequest = new FileCreateDtoRequest("dsazcxklASejff", invalidTitle, "descr", LocalDate.now());

        given()
                .contentType(ContentType.JSON)
                .body(dtoRequest)
                .when()
                .post(BASE_URL)
                .then()
                .status(HttpStatus.BAD_REQUEST);

        verify(fileService, never()).saveFile(dtoRequest);
    }

    @DisplayName("Save file: invalid creationTime and then status bad request")
    @MethodSource("getArgsForInvalidCreationTime")
    @ParameterizedTest
    void saveFile_invalidCreationTime_statusBadRequest(LocalDate invalidCreationTime) {
        FileCreateDtoRequest dtoRequest = new FileCreateDtoRequest("tesdsavZXVcwtee", "title", "descr", invalidCreationTime);

        given()
                .contentType(ContentType.JSON)
                .body(dtoRequest)
                .when()
                .post(BASE_URL)
                .then()
                .status(HttpStatus.BAD_REQUEST);

        verify(fileService, never()).saveFile(dtoRequest);
    }

    @DisplayName("Get file by id: should return file with status ok")
    @Test
    void getFileById_existsFile_shouldReturnFileWithStatusOk() {
        Long id = 1L;
        FileDto expectedDto = new FileDto("asdkzxETdxz", "title", "descr", LocalDate.now());

        when(fileService.getFileById(id))
                .thenReturn(expectedDto);

        given()
                .pathParam("id", id)
                .when()
                .get(BASE_URL + "/{id}")
                .then()
                .status(HttpStatus.OK)
                .body("title", equalTo(expectedDto.title()))
                .body("file", equalTo(expectedDto.file()))
                .body("description", equalTo(expectedDto.description()))
                .body("creationTime", equalTo(expectedDto.creationTime().toString()));

        verify(fileService).getFileById(id);
    }

    @DisplayName("Get file by id: file not found then status bad request")
    @Test
    void getFileById_nonExistsFile_statusBadRequest() {
        Long id = 1L;
        FileDto expectedDto = new FileDto("asdkzxETdxz", "title", "descr", LocalDate.now());

        when(fileService.getFileById(id))
                .thenThrow(new FileNotFoundException("File not found!"));

        given()
                .pathParam("id", id)
                .when()
                .get(BASE_URL + "/{id}")
                .then()
                .status(HttpStatus.BAD_REQUEST);

        verify(fileService).getFileById(id);
    }

    @DisplayName("Get file page: should return page by provided params with status ok")
    @Test
    void getFilePage_validParams_shouldReturnPageWithStatusOk() {
        Page<FileDto> fileDtoPage = getFileDtoPage();
        int page = 1;
        int size = 30;
        String direction = "asc";

        when(fileService.getFilePageSortedByCreationTime(page, size, direction))
                .thenReturn(fileDtoPage);

        given()
                .param("page", page)
                .param("size", size)
                .param("direction", direction)
                .when()
                .get(BASE_URL)
                .then()
                .status(HttpStatus.OK);

        verify(fileService).getFilePageSortedByCreationTime(page, size, direction);
    }

    @DisplayName("Get file page: should return page by default params with status ok")
    @Test
    void getFilePage_withoutParams_shouldReturnPageWithStatusOk() {
        Page<FileDto> fileDtoPage = getFileDtoPage();
        int defaultPage = 0;
        int defaultSize = 20;
        String defaultDirection = "asc";

        when(fileService.getFilePageSortedByCreationTime(defaultPage, defaultSize, defaultDirection))
                .thenReturn(fileDtoPage);

        given()
                .when()
                .get(BASE_URL)
                .then()
                .status(HttpStatus.OK);

        verify(fileService).getFilePageSortedByCreationTime(defaultPage, defaultSize, defaultDirection);
    }

    @DisplayName("Get file page: invalid page param then status bad request")
    @Test
    void getFilePage_invalidPageParam_statusBadRequest() {
        int page = -1;
        int size = 30;
        String direction = "asc";

        given()
                .param("page", page)
                .param("size", size)
                .param("direction", direction)
                .when()
                .get(BASE_URL)
                .then()
                .status(HttpStatus.BAD_REQUEST);

        verify(fileService, never()).getFilePageSortedByCreationTime(page, size, direction);
    }

    @DisplayName("Get file page: invalid size param then status bad request")
    @Test
    void getFilePage_invalidSizeParam_statusBadRequest() {
        int page = 1;
        int size = -1;
        String direction = "asc";

        given()
                .param("page", page)
                .param("size", size)
                .param("direction", direction)
                .when()
                .get(BASE_URL)
                .then()
                .status(HttpStatus.BAD_REQUEST);

        verify(fileService, never()).getFilePageSortedByCreationTime(page, size, direction);
    }

    @DisplayName("Get file page: invalid direction param then status bad request")
    @Test
    void getFilePage_invalidDirectionParam_statusBadRequest() {
        int page = 1;
        int size = 10;
        String direction = "dummy";

        given()
                .param("page", page)
                .param("size", size)
                .param("direction", direction)
                .when()
                .get(BASE_URL)
                .then()
                .status(HttpStatus.BAD_REQUEST);

        verify(fileService, never()).getFilePageSortedByCreationTime(page, size, direction);
    }

    private static Stream<Arguments> getArgsForInvalidFile() {
        return Stream.of(
                arguments(named("File is blank", "  ")),
                arguments(named("File is null", null))
        );
    }

    private static Stream<Arguments> getArgsForInvalidTitle() {
        return Stream.of(
                arguments(named("Title is blank", "  ")),
                arguments(named("Title is null", null))
        );
    }

    private static Stream<Arguments> getArgsForInvalidCreationTime() {
        return Stream.of(
                arguments(named("Date is null", null)),
                arguments(named("Date in the future", LocalDate.now().plusMonths(1)))
        );
    }

    private Page<FileDto> getFileDtoPage() {
        FileDto firstDto = new FileDto("asdvxETesszxcd", "first-title", "first-descr", LocalDate.now());
        FileDto secondDto = new FileDto("asdat32weds", "second-title", "second-descr", LocalDate.now());


        List<FileDto> fileDtoList = List.of(firstDto, secondDto);

        return new PageImpl<>(fileDtoList, PageRequest.of(0, 2, Sort.by(Sort.Order.desc("creationTime"))), fileDtoList.size());
    }
}
