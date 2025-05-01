package ru.inno.nalemian.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/object-mapper")
public class ObjectMapperController {
    private final ObjectMapper objectMapper = new ObjectMapper();

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
        Car car = new Car("yellow", "renault");
        return objectMapper.writeValueAsString(car);
    }

    static class Car {
        @JsonIgnore
        private String color;
        @JsonProperty(value = "brand")
        private String type;

        public Car(String color, String type) {
            this.color = color;
            this.type = type;
        }
    }

}
