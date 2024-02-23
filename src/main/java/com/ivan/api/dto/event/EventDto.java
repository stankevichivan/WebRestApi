package com.ivan.api.dto.event;

import com.ivan.api.dto.file.FileDto;
import lombok.Builder;

@Builder
public record EventDto(Long id, FileDto fileDto) {

}
