package com.droppart.shortenly.urlshortener.controller;

import com.droppart.shortenly.urlshortener.dto.AnalyticsResponse;
import com.droppart.shortenly.urlshortener.dto.ShortenUrlRequest;
import com.droppart.shortenly.urlshortener.dto.ShortenUrlResponse;
import com.droppart.shortenly.urlshortener.model.UrlMapping;
import com.droppart.shortenly.urlshortener.service.UrlService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api")
public class UrlController {
    @Autowired
    private UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<ShortenUrlResponse> createShortUrl(@RequestBody ShortenUrlRequest request) {
        // add checks for empty userid or wrong userid, and also for correct original url
        UrlMapping shortUrl = urlService.shortenUrl(request.getLongUrl(), request.getUserId(), request.getExpirationTime());
        if (shortUrl == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ShortenUrlResponse response = new ShortenUrlResponse(shortUrl.getShortUrl(), shortUrl.getLongUrl(), "Short URL created", shortUrl.getExpirationTime());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<String> getOriginalUrl(@PathVariable String shortUrl) {
        String original = urlService.getOriginalUrl(shortUrl);
        if (original == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("404 - Not Found");
        }
        return ResponseEntity.ok(original);
    }

    @GetMapping("/redirect/{shortUrl}")
    public void redirect(@PathVariable String shortUrl, HttpServletResponse response) throws IOException {
        String url = urlService.getOriginalUrl(shortUrl);
        if (url != null) {
            response.sendRedirect(url);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "URL not found");
        }
    }

    @GetMapping("/user/{userId}/urls")
    public ResponseEntity<List<UrlMapping>> getUrlsByUser(@PathVariable String userId) {
        List<UrlMapping> urls = urlService.getUrlsByUser(userId);
        if (urls.isEmpty()) {
            urls = new ArrayList<>();
//            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(urls);
    }

    @GetMapping("/analytics/{shortUrl}")
    public ResponseEntity<?> getAnalytics(@PathVariable String shortUrl) {
        UrlMapping mapping = urlService.getUrlFromShortUrl(shortUrl);
        if (mapping == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Short URL not found");
        }

        AnalyticsResponse analytics = new AnalyticsResponse(mapping.getShortUrl(), mapping.getLongUrl(), mapping.getClickCount(), mapping.getLastAccessedAt(), mapping.getExpirationTime());

        return ResponseEntity.ok(analytics);
    }


}
