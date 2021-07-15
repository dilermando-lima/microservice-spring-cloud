package com.formcloud.archcommons.util;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;


public class UtilString {

	public static boolean isEmptyTrim(String string) {
		return string == null || string.trim().isEmpty();
	}


	public static String shortUUID(){

        Instant instant = Instant.now();
        Date now = new Date(instant.toEpochMilli());
        Long date = Long.parseLong(new SimpleDateFormat("yyyyMMdd").format(now)) - 20210101;
        Long time = Long.parseLong(new SimpleDateFormat("HHmmss").format(now));
        Integer nano =  Integer.parseInt(String.valueOf(instant.getNano() + "000000").substring(0, 6));
        Integer added = new Random().nextInt(1000);

        return Long.toString( date ,36) +
                Long.toString( time ,36)  +
                Long.toString( nano ,36) + "-" +
                Long.toString( added ,36);
    } 



	public static String removeAcento(String text){
		if( text == null  ) return null;
		return Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}


	public static String replaceSpecialCaract(String text, String textSubstitute, String exceptCaract){
		
		if( text == null  ) return null;

		if(  textSubstitute == null ) textSubstitute = "";

		String [] textSplit = text.split(exceptCaract);

		for (int i = 0; i < textSplit.length; i++) {
			textSplit[i] =  textSplit[i].replaceAll("[^a-zA-Z0-9]", textSubstitute);
		}

		return String.join(exceptCaract, textSplit);
	}

	public static String getOnlyNumbersInString(String string) {
		if( string == null ) return null;
		return string.replaceAll("\\D+", "");
	}

	public static String left(String text,  int caract){
		if( text == null ) return  null;
		if( text.equals("") ) return  "";
		if( caract <= 0 ) return null;
		if( caract > text.length()  ) return text;
		return text.substring(0, caract  );
	}

	public static String right(String text,  int caract){
		if( text == null ) return  null;
		if( text.equals("") ) return  "";
		if( caract <= 0 ) return null;
		if( caract > text.length()  ) return text;
		return text.substring(text.length()  - caract);
	}

	public static String toJsonPrettyNonNull(Object object) throws RuntimeException {
		try {
			return  new ObjectMapper()
					.setSerializationInclusion(Include.NON_NULL)
					.setSerializationInclusion(Include.NON_EMPTY)
					.writerWithDefaultPrettyPrinter()
					.writeValueAsString(object);
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}


	
}
