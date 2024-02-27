package com.ivan.api.dto.event;

import com.ivan.api.dto.file.FileDto;
import com.ivan.api.dto.user.UserDto;
import lombok.Builder;

@Builder
public record EventDto(Long id, String type, FileDto fileDto, UserDto userDto) {

}
