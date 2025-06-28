package com.droppart.shortenly.urlshortener.service;

import com.droppart.shortenly.urlshortener.model.UrlMapping;
import com.droppart.shortenly.urlshortener.repository.UrlRepository;
import com.droppart.shortenly.urlshortener.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class UrlService {
    @Autowired
    private UrlRepository urlRepository;
    private final int SHORT_URL_LENGTH = 7;


    public UrlMapping shortenUrl(String originalUrl, String userId, Long expirationTime) {
        String hash = MD5Utils.md5Hash(originalUrl);
        for (int i = 0; i <= hash.length() - SHORT_URL_LENGTH; i++) {
            String candidate = hash.substring(i, i + SHORT_URL_LENGTH);
            if (!urlRepository.existsByShortUrl(candidate)) {

                UrlMapping mapping = new UrlMapping();
                mapping.setShortUrl(candidate);
                mapping.setLongUrl(originalUrl);
                mapping.setUserId(userId);
                mapping.setCreatedAt(System.currentTimeMillis());

                long now = System.currentTimeMillis();
                long expireAt = (expirationTime != null) ? expirationTime : now + TimeUnit.DAYS.toMillis(1);
                mapping.setExpirationTime(expireAt);
                try {
                    updateAccessStats(mapping);
//                                        long ttlMillis = expireAt - now;
//                    redisTemplate.opsForValue().set(candidate, originalUrl, ttlMillis, TimeUnit.MILLISECONDS);

                } catch (Exception e) {
                    return null;
                }
                return mapping;
            }
        }
        throw new RuntimeException("Unable to generate unique short URL");
    }

    @Cacheable(value = "urlCache", key = "#shortUrl")
    public String getOriginalUrl(String shortUrl) {
        /*
         // Check Redis first
        String cachedUrl = redisTemplate.opsForValue().get(shortUrl);
        if (cachedUrl != null) {
            updateAccessStats(shortUrl);
            return cachedUrl;
        }*/

        UrlMapping mapping = urlRepository.findByShortUrl(shortUrl);
        if (mapping != null) {
            mapping.setClickCount(mapping.getClickCount() + 1);
            mapping.setLastAccessedAt(System.currentTimeMillis());

            long now = System.currentTimeMillis();
            if (mapping.getExpirationTime() > 0 && now > mapping.getExpirationTime()) {
                return "Expired Time"; // expired
            }
/* // Store in Redis again for next time
            long ttl = mapping.getExpirationTime() - now;
            if (ttl > 0) {
                redisTemplate.opsForValue().set(shortUrl, mapping.getLongUrl(), ttl, TimeUnit.MILLISECONDS);
            }

            updateAccessStats(mapping);*/
            updateAccessStats(mapping);
        }
        return mapping != null ? mapping.getLongUrl() : null;
    }

    public UrlMapping getUrlFromShortUrl(String shortUrl) {
        UrlMapping mapping = urlRepository.findByShortUrl(shortUrl);
        if (mapping != null) {
            mapping.setClickCount(mapping.getClickCount() + 1);
            mapping.setLastAccessedAt(System.currentTimeMillis());
        }
        return mapping;
    }

    public List<UrlMapping> getUrlsByUser(String userId) {
        return urlRepository.findAllByUserId(userId);
    }

    private void updateAccessStats(String shortUrl) {
        UrlMapping mapping = urlRepository.findByShortUrl(shortUrl);
        if (mapping != null) {
            mapping.setClickCount(mapping.getClickCount() + 1);
            mapping.setLastAccessedAt(System.currentTimeMillis());
            urlRepository.save(mapping);
        }
    }

    private void updateAccessStats(UrlMapping mapping) {
        mapping.setClickCount(mapping.getClickCount() + 1);
        mapping.setLastAccessedAt(System.currentTimeMillis());
        urlRepository.save(mapping);
    }
}
