package com.tietoevry.soilops.repository;

import com.tietoevry.soilops.model.Observation;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ObservationRepository extends CrudRepository<Observation, Long> {
    List<Observation> findAllByDeviceId(Long deviceId, Sort sort);
}
