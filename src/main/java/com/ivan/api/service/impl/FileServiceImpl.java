package com.ivan.api.service.impl;

import com.ivan.api.dto.file.FileDto;
import com.ivan.api.repository.FileRepository;
import com.ivan.api.repository.impl.FileRepositoryImpl;
import com.ivan.api.service.FileService;

import java.util.ArrayList;
import java.util.List;

public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository = new FileRepositoryImpl();

    @Override
    public FileDto getById(Long id) {
        var file = fileRepository.getById(id);
        return FileDto.builder().id(file.getId()).fileName(file.getFileName()).filePath(file.getFilePath()).build();
    }

    @Override
    public List<FileDto> getAll() {
        var files = fileRepository.getAll();
        List<FileDto> fileDtoList = new ArrayList<>();
        files.forEach(file -> fileDtoList.add(
                        FileDto.builder()
                                .id(file.getId())
                                .fileName(file.getFileName())
                                .filePath(file.getFilePath()).build()
                )
        );
        return fileDtoList;
    }

    @Override
    public FileDto create(FileDto fileDto) {
        throw new UnsupportedOperationException("operation create unsupported fo File");
    }

    @Override
    public FileDto update(FileDto fileDto, Long id) {
        throw new UnsupportedOperationException("operation update unsupported fo File");

    }

    @Override
    public void deleteById(Long id) {
        fileRepository.deleteById(id);
    }
}
