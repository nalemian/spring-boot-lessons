package ru.inno.nalemian.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
public class Car {
    @JsonIgnore
    private String color;
    @JsonProperty(value = "brand")
    private String type;
    @Getter
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm a z")
    private Date date;
}