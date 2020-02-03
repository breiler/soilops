package com.tietoevry.soilops.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
public class NameGeneratorService {
    public String generate() {
        return RandomStringUtils.randomAlphabetic(10);
    }
}
