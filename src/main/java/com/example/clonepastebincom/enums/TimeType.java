package com.example.clonepastebincom.enums;

public enum TimeType {
    MINUTE_10(10), HOUR_1(1), HOUR_3(3), DAY_1(1), WEEK_1(1), MONTH_1(1), NO_LIMITS();

    private int value = 0;

    TimeType(int value) {
        this.value = value;
    }

    TimeType() {
    }

    public int getValue() {
        return value;
    }
}
