package com.droppart.shortenly.urlshortener.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ShortenUrlResponse {
    private String shortUrl;
    private String longUrl;
    private String message;
    private Long expirationTime;

}