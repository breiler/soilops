package com.tietoevry.soilops.repository;

import com.tietoevry.soilops.model.Device;
import com.tietoevry.soilops.model.Place;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DeviceRepository extends CrudRepository<Device, Long> {
    Device findByUuid(String deviceUuid);

    List<Device> findAllByUserId(Long id);
}