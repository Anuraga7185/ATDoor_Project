package com.droppart.shortenly.urlshortener.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShortenUrlRequest {
    private String longUrl;
    private String userId;
    private Long expirationTime; // Optional: set by client (Unix timestamp in ms)

}