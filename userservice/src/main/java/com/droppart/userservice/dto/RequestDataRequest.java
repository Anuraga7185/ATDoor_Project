package com.droppart.userservice.dto;

import com.droppart.userservice.model.helper.UserRole;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class RequestDataRequest {
    private UserRole userRole;

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public UserRole getUserRole() {
        return userRole;
    }
}
