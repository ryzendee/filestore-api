package com.app.filestore.unit.controller;

import com.app.filestore.controller.FileController;
import com.app.filestore.dto.FileDtoRequest;
import com.app.filestore.dto.FileDtoResponse;
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
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
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

    @DisplayName("Save file: should save file and return id with status CREATED(200)")
    @Test
    void saveFile_validRequest_statusCreated() {
        FileDtoRequest dtoRequest = new FileDtoRequest("asdkxzcWiksadm", "test-title", LocalDate.now());
        FileDtoResponse expectedResponse = new FileDtoResponse(1L);
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
        FileDtoRequest dtoRequest = new FileDtoRequest(invalidFile, "title", LocalDate.now());

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
        FileDtoRequest dtoRequest = new FileDtoRequest("dsazcxklASejff", invalidTitle, LocalDate.now());

        given()
                .contentType(ContentType.JSON)
                .body(dtoRequest)
                .when()
                .post(BASE_URL)
                .then()
                .status(HttpStatus.BAD_REQUEST);

        verify(fileService, never()).saveFile(dtoRequest);
    }

    @DisplayName("Save file: invalid creationTime and then status bad request ")
    @MethodSource("getArgsForInvalidCreationTime")
    @ParameterizedTest
    void saveFile_invalidCreationTime_statusBadRequest(LocalDate invalidCreationTime) {
        FileDtoRequest dtoRequest = new FileDtoRequest("tesdsavZXVcwtee", "title", invalidCreationTime);

        given()
                .contentType(ContentType.JSON)
                .body(dtoRequest)
                .when()
                .post(BASE_URL)
                .then()
                .status(HttpStatus.BAD_REQUEST);

        verify(fileService, never()).saveFile(dtoRequest);
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
}
