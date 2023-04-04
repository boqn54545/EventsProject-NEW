package com.example.EventsProject.Enums;

import java.util.Arrays;
import java.util.List;

public enum InterestsEnum {
    SPORTS("Sports"),
    MUSIC("Music"),
    FOOD("Food"),
    TRAVEL("Travel"),
    ART("Art"),
    TECHNOLOGY("Technology"),
    FASHION("Fashion"),
    OUTDOORS("Outdoors");


    private final String interest;

    InterestsEnum(String interest) {
        this.interest = interest;
    }

    public String getInterest() {
        return interest;
    }

}

