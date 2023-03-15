package com.example.EventsProject.Enums;

public enum InterestsEnum {
    SPORTS("Sports"),
    MUSIC("Music"),
    FOOD("Food"),
    TRAVEL("Travel"),
    ART("Art"),
    TECHNOLOGY("Technology"),
    FASHION("Fashion"),
    OUTDOORS("Outdoors");

    private final String Interests;

    InterestsEnum(String Interest) {
        this.Interests = Interest;
    }

    public String getInterests() {
        return Interests;
    }
}

