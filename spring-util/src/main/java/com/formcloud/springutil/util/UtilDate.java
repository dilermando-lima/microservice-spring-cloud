package com.formcloud.springutil.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class UtilDate {
    

    public static LocalDateTime getNow() {
		try {
			return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toLocalDateTime();
		} catch (Exception e) {
			return null;
		}
    }

    public static LocalDate getToday() {
		try {
			return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toLocalDate();
		} catch (Exception e) {
			return null;
		}
    }

    public static String parseToString(TemporalAccessor date, String format){
		if(date ==null || format == null) return null;
        return DateTimeFormatter.ofPattern(format).format(date);
    }

    public static LocalDate parseStringToLocalDate(String formatParam, String dateInString) {
		try {
			return   LocalDate.parse(  dateInString , DateTimeFormatter.ofPattern(formatParam));
		} catch (Exception e) {
			return null;
		}
	}

	public static LocalDateTime parseStringToLocalDateTime(String formatParam, String dateInString) {
		try {
			return   LocalDateTime.parse(  dateInString , DateTimeFormatter.ofPattern(formatParam));
		} catch (Exception e) {
			return null;
		}
    }
    

    public static LocalDateTime getLocalDateTime(Long miliseconds){
		try {
			return   Instant.ofEpochMilli(miliseconds).atZone(ZoneId.of("America/Sao_Paulo")).toLocalDateTime();
		} catch (Exception e) {
			return null;
		}
	}
    
    public static LocalDate getLocalDate(Long miliseconds){
		try {
			return   Instant.ofEpochMilli(miliseconds).atZone(ZoneId.of("America/Sao_Paulo")).toLocalDate();
		} catch (Exception e) {
			return null;
		}
	}

    public static Long getMiliSec(TemporalAccessor date) {
		try {
            
            if(  date ==  null ) return null;

            if( date.getClass().isAssignableFrom(LocalDateTime.class) ){
                return  LocalDateTime.from(date).atZone(ZoneId.of("America/Sao_Paulo")).toInstant().toEpochMilli();
            }else if ( date.getClass().isAssignableFrom(LocalDate.class)  ){
                return  LocalDate.from(date).atStartOfDay().atZone(ZoneId.of("America/Sao_Paulo")).toInstant().toEpochMilli();
            }else if ( date.getClass().isAssignableFrom(ZonedDateTime.class)  ){
                return  ZonedDateTime.from(date).toInstant().toEpochMilli();
            }else if ( date.getClass().isAssignableFrom(OffsetDateTime.class)  ){
                return  OffsetDateTime.from(date).toInstant().toEpochMilli();
            }else{
               
               throw new Exception("error on parse TemporalAccessor into Long miliSec. check if your object are assignable from LocalDateTime, LocalDate, ZonedDateTime or OffsetDateTime");
               
            }
		} catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
		}
	}



    
}
