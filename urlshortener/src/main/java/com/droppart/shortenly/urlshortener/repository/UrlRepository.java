package com.droppart.shortenly.urlshortener.repository;

import com.droppart.shortenly.urlshortener.model.UrlMapping;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;


public interface UrlRepository extends MongoRepository<UrlMapping, String> {
    boolean existsByShortUrl(String shortUrl);

    UrlMapping findByShortUrl(String shortUrl);

    List<UrlMapping> findAllByUserId(String userId);

}
