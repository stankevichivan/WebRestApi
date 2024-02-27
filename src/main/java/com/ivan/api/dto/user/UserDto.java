package com.ivan.api.dto.user;

import java.time.LocalDate;
import lombok.Builder;

@Builder
public record UserDto(long id,
                      String name,
                      UserStatus status,
                      LocalDate createdAt,
                      LocalDate updatedAt) {

}
