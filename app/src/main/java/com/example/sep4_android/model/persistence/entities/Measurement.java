package com.example.sep4_android.model.persistence.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.sep4_android.model.DateHandler;

import java.text.DateFormat;

@Entity(tableName = "measurements_table")
public class Measurement {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private double temperature, co2, humidity;
    private long timestamp;

    public Measurement(double temperature, double co2, double humidity, long timestamp) {
        this.temperature = temperature;
        this.co2 = co2;
        this.humidity = humidity;
        this.timestamp = timestamp;
    }

    //Getting timestamp in string format
    public String getTimestampString(){
        return DateHandler.fromLongToString(timestamp);
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "id=" + id +
                ", temperature=" + temperature +
                ", co2=" + co2 +
                ", humidity=" + humidity +
                ", timestamp=" + timestamp +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getCo2() {
        return co2;
    }

    public void setCo2(double co2) {
        this.co2 = co2;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}