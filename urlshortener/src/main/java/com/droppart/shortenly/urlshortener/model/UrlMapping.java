package com.droppart.shortenly.urlshortener.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "url_mapping")
@Getter
@Setter
public class UrlMapping {

    @Id
    private String shortUrl;
    private String longUrl;
    private String userId;
    private long createdAt;

    private long clickCount;
    private long lastAccessedAt;

    private long expirationTime; // Unix timestamp in ms — when this URL should expire
}
