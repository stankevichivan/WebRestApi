package com.ivan.api.dto.event;

import lombok.Builder;

@Builder
public record EventUser (long id, String name){
}
