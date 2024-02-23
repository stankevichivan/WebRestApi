package com.ivan.api.service.impl;

import com.ivan.api.dto.event.EventInfo;
import com.ivan.api.dto.event.EventUser;
import com.ivan.api.dto.file.FileDto;
import com.ivan.api.model.File;
import com.ivan.api.model.User;
import com.ivan.api.repository.EventRepository;
import com.ivan.api.repository.impl.EventRepositoryImpl;
import com.ivan.api.service.EventService;

import java.util.ArrayList;
import java.util.List;

public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository = new EventRepositoryImpl();


    @Override
    public EventInfo getById(Long id) {
        var event = eventRepository.getById(id);
        return EventInfo.builder()
                .fileDto(fileToFileDto(event.getFile()))
                .eventUser(userToEventUser(event.getUser()))
                .build();
    }

    private EventUser userToEventUser(User user) {
        return EventUser.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }

    private FileDto fileToFileDto(File file) {
        return FileDto.builder()
                .id(file.getId())
                .fileName(file.getFileName())
                .filePath(file.getFilePath())
                .build();
    }

    @Override
    public List<EventInfo> getAll() {
        var events = eventRepository.getAll();
        List<EventInfo> eventInfoList = new ArrayList<>();
        events.forEach(event -> {
            eventInfoList.add(EventInfo.builder()
                    .fileDto(fileToFileDto(event.getFile()))
                    .eventUser(userToEventUser(event.getUser()))
                    .build());
        });
        return eventInfoList;
    }

    @Override
    public EventInfo create(EventInfo eventDto) {
        throw new UnsupportedOperationException("operation create unsupported fo Event");

    }

    @Override
    public EventInfo update(EventInfo eventDto, Long id) {
        throw new UnsupportedOperationException("operation update unsupported fo Event");

    }

    @Override
    public void deleteById(Long id) {
        throw new UnsupportedOperationException("operation deleteById unsupported fo Event");
    }
}
