package com.tietoevry.soilops.repository;

import com.tietoevry.soilops.model.Device;
import com.tietoevry.soilops.model.Place;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface DeviceRepository extends CrudRepository<Device, Long> {
    Optional<Device> findByUuid(String uuid);

    List<Device> findAllByUserId(Long id);
}