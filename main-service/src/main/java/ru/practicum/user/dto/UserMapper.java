package ru.practicum.user.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.user.User;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class UserMapper {

    public UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public UserShortDto toUserShortDto(User user) {
        return UserShortDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }

    public List<UserDto> toUserDto(Iterable<User> users) {
        List<UserDto> dtos = new ArrayList<>();
        for (User user : users) {
            dtos.add(toUserDto(user));
        }
        return dtos;
    }

    public User toUser(NewUserRequestDto newUserRequestDto) {
        return User.builder()
                .name(newUserRequestDto.getName())
                .email(newUserRequestDto.getEmail())
                .build();
    }
}