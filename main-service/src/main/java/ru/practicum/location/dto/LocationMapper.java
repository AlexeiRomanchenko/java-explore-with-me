package ru.practicum.location.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.location.Location;

@UtilityClass
public class LocationMapper {

    public LocationDto toLocationDto(Location location) {
        return LocationDto.builder()
                .lat(location.getLat())
                .lon(location.getLon())
                .build();
    }

    public Location toLocation(LocationDto locationDto) {
        return Location.builder()
                .lat(locationDto.getLat())
                .lon(locationDto.getLon())
                .build();
    }
}