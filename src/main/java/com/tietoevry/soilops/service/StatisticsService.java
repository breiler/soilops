package com.tietoevry.soilops.service;

import com.tietoevry.soilops.model.Device;
import com.tietoevry.soilops.model.DeviceStatus;
import com.tietoevry.soilops.repository.DeviceRepository;
import com.tietoevry.soilops.repository.DeviceStatusRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsService {

    private final DeviceStatusRepository deviceStatusRepository;
    private final DeviceRepository deviceRepository;
    private static final long ONE_HOUR = 1000 * 60  * 60;

    public StatisticsService(DeviceStatusRepository deviceStatusRepository, DeviceRepository deviceRepository) {
        this.deviceStatusRepository = deviceStatusRepository;
        this.deviceRepository = deviceRepository;
    }

    public List<DeviceStatus> getStatistics(String uuid) {
        Device device = deviceRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Couldn't find device"));

        return deviceStatusRepository.getStatistics(device.getId(), ONE_HOUR);
    }
}
