package com.balh.perucate.util;

import com.balh.perucate.agreggates.response.ResponseSunat;
import com.balh.perucate.entity.StudentsEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@Slf4j
public class Util {
    private Util() {
    }

    public static String convertToJson(ResponseSunat responseSunat) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(responseSunat);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
    public static String convertToJsonEntity(StudentsEntity studentsEntity) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(studentsEntity);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
    public static <T> T convertFromJson(String json, Class<T> valueType) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, valueType);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
