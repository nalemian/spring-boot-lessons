package ru.inno.nalemian.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/object-mapper")
public class ObjectMapperController {
    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/string-to-json")
    public JsonNode stringToJson(@RequestBody String jsonString) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(jsonString);
        String color = jsonNode.get("color").asText();
        System.out.println(color);
        return jsonNode;
    }

    @GetMapping("/date-to-json")
    public String dateToJson() throws JsonProcessingException {
        Date currentDate = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
        objectMapper.setDateFormat(format);
        ObjectNode response = objectMapper.createObjectNode();
        response.put("currentDate", format.format(currentDate));
        return objectMapper.writeValueAsString(response);
    }

    @GetMapping("/java-to-json")
    public String javaObjectToJson() throws JsonProcessingException {
        Car car = new Car("yellow", "renault", new Date());
        return objectMapper.writeValueAsString(car);
    }

    @PostMapping("/json-to-java")
    public Car jsonToJavaObject(@RequestBody String jsonString) throws JsonProcessingException {
        return objectMapper.readValue(jsonString, Car.class);
    }

    @GetMapping("/get-dto-with-date")
    public String getDtoWithDate() throws JsonProcessingException {
        Car car = new Car("pink", "lamborgini", new Date());
        return objectMapper.writeValueAsString(car);
    }

    @PostMapping("/post-dto-with-date")
    public Car postDtoWithDate(@RequestBody Car car) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
        System.out.println("Date: " + format.format(car.getDate()));
        return car;
    }

    static class Car {
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
}
