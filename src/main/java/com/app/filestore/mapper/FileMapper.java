package com.app.filestore.mapper;

import com.app.filestore.dto.FileDtoRequest;
import com.app.filestore.entity.FileEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FileMapper {

    @Mapping(target = "id", ignore = true)
    FileEntity map(FileDtoRequest fileDtoRequest);
}
