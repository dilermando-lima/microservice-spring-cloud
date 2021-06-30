package com.formcloud.springutil.valid;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import com.formcloud.springutil.errorhandler.ApiException;

import org.springframework.http.HttpStatus;




public class Valid {

	final private static Pattern EMAIL_MULT_REGEX = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", Pattern.CASE_INSENSITIVE);
	final private static Pattern EMAIL_SINGLE_REGEX = Pattern.compile("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	final private static Pattern LINK_HTTP_REGEX = Pattern.compile("^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$", Pattern.CASE_INSENSITIVE);
    
	
	public static void objNull(HttpStatus httpStatus, String msgError, Object value) throws ApiException{
		if(  value == null  )
		throw new ApiException(httpStatus , msgError );
	}


	public static void stringNullOrTrimEmpty(HttpStatus httpStatus, String msgError, Object value) throws ApiException{
		if(  value == null || value.toString().trim().equals("")  ) {
			throw new ApiException(httpStatus , msgError );
		}
	}

	public static void emailMultNotValid(HttpStatus httpStatus, String msgError, Object value) throws ApiException{
		if( value == null ) return;
				
		if(  !EMAIL_MULT_REGEX.matcher(value.toString()).matches() ) 
			throw new ApiException(httpStatus , msgError );
	}

	public static void linkHttpValid(HttpStatus httpStatus, String msgError, Object value) throws ApiException{
		if( value == null ) return;
			
		if(  !LINK_HTTP_REGEX.matcher(value.toString()).matches() ) 
			throw new ApiException(httpStatus , msgError );
	}

	public static void mapNullOrEmpty(HttpStatus httpStatus, String msgError, Object value) throws ApiException{
		if(  value == null ) 
		throw new ApiException(httpStatus , msgError );
		
		if(  ((HashMap<?,?> )value).isEmpty() ) {
			throw new ApiException(httpStatus , msgError );
		}
		
	}
	public static void longNullOrInvalid(HttpStatus httpStatus, String msgError, Object value) throws ApiException{
		if(  value == null ) 
		throw new ApiException(httpStatus , msgError );
	
		try {
			Long.parseLong(String.valueOf(value));
		} catch (NumberFormatException e) {
			throw new ApiException(httpStatus , msgError );
		}
	}

	public static void intNullOrInvalid(HttpStatus httpStatus, String msgError, Object value) throws ApiException{
		if(  value == null ) 
		throw new ApiException(httpStatus , msgError );
	
		try {
			Integer.parseInt(String.valueOf(value));
		} catch (NumberFormatException e) {
			throw new ApiException(httpStatus , msgError );
		}
	}

	public static void intNullOrZero(HttpStatus httpStatus, String msgError, Object value) throws ApiException{
		if( value == null ) throw new ApiException(httpStatus , msgError );
				
		try {
			if( Integer.parseInt(String.valueOf(value)) == 0 ) {
				throw new ApiException(httpStatus , msgError );
			}
		} catch (NumberFormatException e) {
			throw new ApiException(httpStatus , msgError );
		}
		
	}

	public static void listNullOrEmpty(HttpStatus httpStatus, String msgError, Object value) throws ApiException{
		if(  value == null ) 
		throw new ApiException(httpStatus , msgError );
	
		if(  (( List<?>  )value).isEmpty() ) {
			throw new ApiException(httpStatus , msgError );
		}
	}

	public static void emailSingleNotValid(HttpStatus httpStatus, String msgError, Object value) throws ApiException{
		if( value == null ) return;
				
		if(  !EMAIL_SINGLE_REGEX.matcher(value.toString()).matches() ) 
			throw new ApiException(httpStatus , msgError );
		
	}

	public static void check(HttpStatus httpStatus , String msgError, boolean condition) throws ApiException{
		if( condition  ) {
			throw new ApiException(httpStatus , msgError );
		}
	}
	


}
