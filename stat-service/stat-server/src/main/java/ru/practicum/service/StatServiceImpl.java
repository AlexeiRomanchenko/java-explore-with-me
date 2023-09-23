package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.discriptions.MessageManager;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.StatDto;
import ru.practicum.exceptions.ValidationRequestException;
import ru.practicum.model.HitMapper;
import ru.practicum.model.Stat;
import ru.practicum.model.StatMapper;
import ru.practicum.repository.StatRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {
    private final StatRepository statRepository;

    @Override
    public void addHit(HitDto hitDto) {
        statRepository.save(HitMapper.toHit(hitDto));
    }

    @Override
    @Transactional(readOnly = true)
    public List<StatDto> getStats(LocalDateTime start, LocalDateTime end,  List<String> uris, boolean unique) {
        List<Stat> stats;
        if (start.isAfter(end)) {
            throw new ValidationRequestException(MessageManager.END_BEFORE_START);
        }
        if (unique) {
            if (uris == null) {
                stats = statRepository.findAllUrisWithUniqueIp(start, end);
            } else {
                stats = statRepository.findUrisWithUniqueIp(uris, start, end);
            }
        } else {
            if (uris == null) {
                stats = statRepository.findAllUris(start, end);
            } else {
                stats = statRepository.findUris(uris, start, end);
            }
        }
        return !stats.isEmpty() ? stats.stream().map(StatMapper::toStatDto)
                .collect(Collectors.toList()) : Collections.emptyList();
    }
}