package com.example.pushkar.habitcreatingapp.Models;

public class JourneysData {

    String journeyName;
    int journeyId;

    public JourneysData(String journeyName, int journeyId) {
        this.journeyName = journeyName;
        this.journeyId = journeyId;
    }

    public String getJourneyName() {
        return journeyName;
    }

    public int getJourneyId() {
        return journeyId;
    }
}
