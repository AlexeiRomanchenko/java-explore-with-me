package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.model.Hit;
import ru.practicum.model.Stat;

import java.time.LocalDateTime;
import java.util.List;

public interface StatRepository extends JpaRepository<Hit, Long> {

    @Query(value = "SELECT new ru.practicum.model.Stat(s.app, s.uri, COUNT(s.uri)) FROM Hit s "
            + "WHERE s.uri IN (:uris) AND s.timestamp BETWEEN :start AND :end "
            + "GROUP BY s.app, s.uri "
            + "ORDER BY COUNT(s.uri) "
            + "DESC")
    List<Stat> findUris(
            @Param("uris") List<String> uris,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    @Query(value = "SELECT new ru.practicum.model.Stat(s.app, s.uri, COUNT(DISTINCT s.ip)) FROM Hit s "
            + "WHERE s.uri IN (:uris) AND s.timestamp BETWEEN :start AND :end "
            + "GROUP BY s.app, s.uri, s.ip "
            + "ORDER BY COUNT(DISTINCT s.ip) "
            + "DESC")
    List<Stat> findUrisWithUniqueIp(
            @Param("uris") List<String> uris,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    @Query(value = "SELECT new ru.practicum.model.Stat(s.app, s.uri, COUNT(s.uri)) FROM Hit s "
            + "WHERE s.timestamp BETWEEN :start AND :end "
            + "GROUP BY s.app, s.uri "
            + "ORDER BY COUNT(s.uri) "
            + "DESC")
    List<Stat> findAllUris(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    @Query(value = "SELECT new ru.practicum.model.Stat(s.app, s.uri, COUNT(DISTINCT s.ip)) FROM Hit s "
            + "WHERE s.timestamp BETWEEN :start AND :end "
            + "GROUP BY s.app, s.uri, s.ip "
            + "ORDER BY COUNT(DISTINCT s.ip) "
            + "DESC")
    List<Stat> findAllUrisWithUniqueIp(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}