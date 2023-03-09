package com.pi.kursy.security.jwt;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
class InvalidTokenCacheImpl implements InvalidTokenCache {

    private final Map<String, Date> cache = new ConcurrentHashMap<>();

    @Override
    public boolean containsToken(String token) {
        return cache.containsKey(token);
    }

    @Override
    public void addToken(String token, Date invalidUntil) {
        cache.put(token, invalidUntil);
    }

    @Scheduled(cron = "0 * * * * *")
    private void cacheCleaner() {
        List<String> tokensToClean = new ArrayList<>();
        Date now = new Date(System.currentTimeMillis());
        for (Map.Entry<String, Date> entry : cache.entrySet()) {
            if (entry.getValue().before(now)) {
                tokensToClean.add(entry.getKey());
            }
        }
        for (String token : tokensToClean) {
            cache.remove(token);
        }
    }
}
