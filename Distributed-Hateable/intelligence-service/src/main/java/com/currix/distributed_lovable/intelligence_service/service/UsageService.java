package com.currix.distributed_lovable.intelligence_service.service;

import org.springframework.stereotype.Service;

@Service
public interface UsageService {

    void recordTokenUsage(Long userId, int actualTokens);
    void checkDailyTokenUsage();
}
