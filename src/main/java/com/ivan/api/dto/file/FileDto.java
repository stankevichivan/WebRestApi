package com.ivan.api.dto.file;

import lombok.Builder;

@Builder
public record FileDto(Long id, String fileName, String filePath) {
}
