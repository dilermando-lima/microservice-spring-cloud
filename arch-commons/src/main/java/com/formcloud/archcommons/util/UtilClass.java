package com.formcloud.archcommons.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class UtilClass {
    
    public static <T> T convertObject(Object object, Class<T> classToConvert){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule() );
            return objectMapper.readValue(objectMapper.writeValueAsString(object), classToConvert);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    } 

    public static boolean checkEqualObject(Object obj1, Object obj2){
        if( obj1 == null &&  obj2 == null ) return true;
        if( obj1 == null &&  obj2 != null ) return false;
        if( obj1 != null &&  obj2 == null ) return false;
        return objToJson(obj1).equals(objToJson(obj2));
    }
    
    public static String objToJson(Object object){
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule() );
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    public static <T> T jsonToObject(String json, Class<T> classToConvert){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule() );
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




    public static String toString(Object object){
        if( object == null  ) return "null";
        try {
            return new ObjectMapper()
                .setSerializationInclusion(Include.NON_NULL)
                .setSerializationInclusion(Include.NON_EMPTY)
                .writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return object.toString();
        }
    } 



}
