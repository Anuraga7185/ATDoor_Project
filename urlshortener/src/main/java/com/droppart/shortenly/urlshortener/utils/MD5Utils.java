package com.droppart.shortenly.urlshortener.utils;

import java.security.MessageDigest;

public class MD5Utils {
    public static String md5Hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(input.getBytes());
            StringBuilder hex = new StringBuilder();
            for (byte b : bytes) {
                hex.append(Integer.toHexString((b & 0xFF) | 0x100), 1, 3);
            }
            return hex.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
