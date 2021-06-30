package com.formcloud.springutil.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class UtilClass {
    
    public static <T> T convertObject(Object object, Class<T> classToConvert){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(objectMapper.writeValueAsString(object), classToConvert);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    } 
    
    public static <T> T jsonToObject(String json, Class<T> classToConvert){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, classToConvert);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    } 


    public static <T> T cloneObject(Object object, Class<T> classToConvert){
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule() );
            return mapper.readValue(mapper.writeValueAsString(object), classToConvert);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }




}
