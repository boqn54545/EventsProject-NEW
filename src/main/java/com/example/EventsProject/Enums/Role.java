package com.example.EventsProject.Enums;

public enum Role {
    USER("User"),
    ADMIN("Admin"),
    ORGANIZER("Organizer");


    private final String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
