package com.example.EventsProject.Enums;

public enum Role {
    USER("USER"),
    ADMIN("ADMIN"),
    ORGANIZER("ORGANIZER");


    private final String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
