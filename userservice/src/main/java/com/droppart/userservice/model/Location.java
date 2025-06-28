package com.droppart.userservice.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class Location {
    private double latitude;
    private double longitude;
    private String addressLine;
    private boolean isHome; // for frontend to help toggle home
}
