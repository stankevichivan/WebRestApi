package com.ivan.api.dto.user;

import com.ivan.api.dto.event.EventDto;
import lombok.Builder;

import java.util.List;

@Builder
public record UserDto(long id, String name, List<EventDto> eventDtoList) {

}
