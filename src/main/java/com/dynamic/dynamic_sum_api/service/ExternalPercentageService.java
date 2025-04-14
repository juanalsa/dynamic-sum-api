package com.dynamic.dynamic_sum_api.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ExternalPercentageService {

    private Random random = new Random();

    public ExternalPercentageService(Random random) {
        this.random = random;
    }

    public ExternalPercentageService() {
        this(new Random());
    }

    @Cacheable("percentage")
    public double getPercentage() {
        if (random.nextBoolean()) {
            return 10.0;
        } else {
            throw new RuntimeException("External service failed");
        }
    }
}
