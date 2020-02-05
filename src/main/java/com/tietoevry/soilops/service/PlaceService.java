package com.tietoevry.soilops.service;

import com.tietoevry.soilops.model.Place;
import com.tietoevry.soilops.model.User;
import com.tietoevry.soilops.repository.PlaceRepository;
import com.tietoevry.soilops.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PlaceService {
    private UserRepository userRepository;
    private PlaceRepository placeRepository;
    private NameGeneratorService nameGeneratorService;

    public PlaceService(PlaceRepository placeRepository, UserRepository userRepository, NameGeneratorService nameGeneratorService) {
        this.placeRepository = placeRepository;
        this.userRepository = userRepository;
        this.nameGeneratorService = nameGeneratorService;
    }

    public List<Place> findAllByUsername(String username) {
        User user = userRepository.findByUsername(username);
        Iterable<Place> iterable = placeRepository.findAllByUserId(user.getId());
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    public Place create(String username, Place place) {
        User user = userRepository.findByUsername(username);

        place.setId(null);
        place.setUuid(UUID.randomUUID().toString());
        place.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        place.setUser(user);

        if (StringUtils.isEmpty(place.getName())) {
            place.setName(nameGeneratorService.generate());
        }

        return placeRepository.save(place);
    }
}
