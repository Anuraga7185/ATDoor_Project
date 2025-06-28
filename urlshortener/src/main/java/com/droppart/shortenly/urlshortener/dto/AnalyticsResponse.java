package com.droppart.shortenly.urlshortener.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AnalyticsResponse {
    private String shortUrl;
    private String longUrl;
    private long clickCount;
    private long lastAccessedAt;
    private long expirationTime;
}
