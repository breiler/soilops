package com.tietoevry.soilops.repository;

import com.tietoevry.soilops.model.Device;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DeviceRepository extends CrudRepository<Device, Long> {
    List<Device> findAllByPlaceId(Long id);

    Device findByUuid(String deviceUuid);
}