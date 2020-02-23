package com.tietoevry.soilops.repository;

import com.tietoevry.soilops.model.DeviceStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface DeviceStatusRepository extends Repository<DeviceStatus, String> {

    @Query(
            value = "select (cast(created as varchar) || '_' || device_id) as id, created, avg(temperature) as temperature, avg(light) as light, avg(moisture) as moisture, device_id from observation where device_id=? group by (created / ?)",
            nativeQuery = true)
    List<DeviceStatus> getStatistics(Long id, Long time);
}
