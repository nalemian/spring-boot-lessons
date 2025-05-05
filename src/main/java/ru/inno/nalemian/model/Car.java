package ru.inno.nalemian.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Date;

public class Car {
    @JsonIgnore
    private String color;
    @JsonProperty(value = "brand")
    private String type;
    @Getter
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm a z")
    private Date date;

    public Car(String color, String type, Date date) {
        this.color = color;
        this.type = type;
        this.date = date;
    }

    public Car() {
    }
}