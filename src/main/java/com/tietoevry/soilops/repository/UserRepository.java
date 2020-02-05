package com.tietoevry.soilops.repository;

import com.tietoevry.soilops.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
