package com.example.pushkar.habitcreatingapp.Models;

public class RitualData {
    int hour;
    int min;
    String name;
    String Time;
    public RitualData(int hour, int min, String name, String time) {
        this.hour = hour;
        this.min = min;
        this.name = name;
        Time = time;
    }

    public int getHour() {
        return hour;
    }

    public int getMin() {
        return min;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return Time;
    }
}
