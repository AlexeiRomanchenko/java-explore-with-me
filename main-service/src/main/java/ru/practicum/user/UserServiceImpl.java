package ru.practicum.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.discriptions.FromSizeRequest;
import ru.practicum.user.dto.NewUserRequestDto;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.dto.UserMapper;

import java.util.List;

@Transactional
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<UserDto> getUsers(List<Long> ids, int from, int size) {
        PageRequest page = FromSizeRequest.of(from, size);
        log.info("Getting a list of users by IDs: ids = {}, from = {}, size = {}", ids, from, size);
        return UserMapper.toUserDto(userRepository.findByIdIn(ids, page));
    }

    @Transactional(readOnly = true)
    public List<UserDto> getUsers(int from, int size) {
        PageRequest page = FromSizeRequest.of(from, size);
        log.info("Getting a list of all users: from = {}, size = {}", from, size);
        return UserMapper.toUserDto(userRepository.findAll(page));
    }

    public UserDto createUser(NewUserRequestDto newUserRequestDto) {
        log.info("Creating a new user: {}", newUserRequestDto);
        return UserMapper.toUserDto(userRepository.save(UserMapper.toUser(newUserRequestDto)));
    }

    public void deleteUser(Long id) {
        log.info("Deleting a user with an ID = {}", id);
        userRepository.deleteById(id);
    }
}