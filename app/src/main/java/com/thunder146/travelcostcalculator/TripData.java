package com.thunder146.travelcostcalculator;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamConstants;
import java.io.Serializable;

public class TripData extends ObjectOutputStream implements ObjectStreamConstants, Serializable {
    private String distance;
    private String consumption;
    private String price;
    private String people;

    protected TripData() throws IOException {
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getConsumption() {
        return consumption;
    }

    public void setConsumption(String consumption) {
        this.consumption = consumption;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }
}
