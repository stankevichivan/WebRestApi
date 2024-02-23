package com.ivan.api.service.impl;

import com.ivan.api.dto.event.EventDto;
import com.ivan.api.dto.file.FileDto;
import com.ivan.api.dto.user.UserDto;
import com.ivan.api.model.Event;
import com.ivan.api.model.File;
import com.ivan.api.model.User;
import com.ivan.api.repository.UserRepository;
import com.ivan.api.repository.impl.UserRepositoryImpl;
import com.ivan.api.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public UserDto getById(Long id) {
        var user = userRepository.getById(id);
        return createUserDtoFromUser(user);
    }

    @Override
    public List<UserDto> getAll() {
        var userList = userRepository.getAll();
        List<UserDto> userDtoList = new ArrayList<>();
        userList.forEach(user -> {
            var userDto = createUserDtoFromUser(user);
            userDtoList.add(userDto);
        });
        return userDtoList;
    }

    @Override
    public UserDto create(UserDto userDto) {
        User user = new User();
        user.setName(userDto.name());
        createUserFromUserDto(userDto, user);
        return createUserDtoFromUser(userRepository.create(user));
    }

    private static UserDto createUserDtoFromUser(User user) {
        var userDto = UserDto.builder().id(user.getId()).name(user.getName()).eventDtoList(new ArrayList<>()).build();
        user.getEvents().forEach(event -> {
            var file = event.getFile();
            var fileDto = FileDto.builder().id(file.getId()).fileName(file.getFileName()).filePath(file.getFilePath()).build();
            var eventDto = EventDto.builder().id(event.getId()).fileDto(fileDto).build();
            userDto.eventDtoList().add(eventDto);
        });
        return userDto;
    }

    @Override
    public UserDto update(UserDto userDto, Long id) {
        var userDto1 = getById(id);
        var user = new User();
        user.setId(id);
        user.setName(userDto.name());
        createUserFromUserDto(userDto1, user);
        var updatedUser = userRepository.update(user);
        return UserDto.builder().name(updatedUser.getName()).id(updatedUser.getId()).build();
    }

    private static void createUserFromUserDto(UserDto userDto, User user) {
        userDto.eventDtoList().forEach(eventDto -> {
            Event event = new Event();
            File file = new File();
            file.setId(eventDto.fileDto().id());
            file.setFilePath(eventDto.fileDto().filePath());
            file.setFileName(eventDto.fileDto().fileName());
            event.setFile(file);
            event.setId(eventDto.id());
            user.addEvent(event);
        });
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
