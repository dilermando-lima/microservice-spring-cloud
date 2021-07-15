package com.formcloud.archcommons.errorhandler;


import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;


@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ExceptionControllerAdvice{
	
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ErrorReturn> exceptionHandlerApiExcepetion(ApiException ex) {
		return new ResponseEntity<ErrorReturn>(
				new ErrorReturn(ex.getMessage() , ex.getStatus() ), HttpStatus.valueOf(ex.getStatus() ));
		
	}
	

    @ExceptionHandler({  NullPointerException.class})
    public ResponseEntity<ErrorReturn> handleNullPointException( final Exception ex, final WebRequest request) {
		ex.printStackTrace();
    	return new ResponseEntity<ErrorReturn>(
		 	    		  new ErrorReturn( ex.getMessage() ,
		 	    		  HttpStatus.INTERNAL_SERVER_ERROR .value() ) ,  HttpStatus.INTERNAL_SERVER_ERROR );
    }


	@ExceptionHandler({  Exception.class ,  RuntimeException.class})
    public ResponseEntity<ErrorReturn> anyException( final Exception ex, final WebRequest request) {
		ex.printStackTrace();
    	return new ResponseEntity<ErrorReturn>(
		 	    		  new ErrorReturn( ex.getMessage() ,
		 	    		  HttpStatus.INTERNAL_SERVER_ERROR .value() ) ,  HttpStatus.INTERNAL_SERVER_ERROR );
    }
    

    

    @ExceptionHandler({  HttpRequestMethodNotSupportedException.class })
    public ResponseEntity<ErrorReturn> handleNotSupportedException( final Exception ex, final WebRequest request) {
		 	      return new ResponseEntity<ErrorReturn>(
		 	    		  new ErrorReturn( ex.getMessage() ,
		 	    		  HttpStatus.METHOD_NOT_ALLOWED .value() ) ,  HttpStatus.METHOD_NOT_ALLOWED );
    }


    
    
    @ExceptionHandler({   NoHandlerFoundException.class })
    public ResponseEntity<ErrorReturn> handleNoHandlerFoundException( final Exception ex, final WebRequest request) {
       	return new ResponseEntity<ErrorReturn>(
		 	    		  new ErrorReturn( ex.getMessage() ,
		 	    		  HttpStatus.NOT_FOUND.value() ) ,  HttpStatus.NOT_FOUND );
	}


	
	@ExceptionHandler({   UnsupportedMediaTypeStatusException.class })
    public ResponseEntity<ErrorReturn> handleUnsupportedMediaTypeStatusException( final Exception ex, final WebRequest request) {
       	return new ResponseEntity<ErrorReturn>(
		 	    		  new ErrorReturn( ex.getMessage() ,
		 	    		  HttpStatus.NOT_FOUND.value() ) ,  HttpStatus.NOT_FOUND );
	}

	@ExceptionHandler({   MethodArgumentNotValidException.class })
    public ResponseEntity<ErrorReturn> handleMethodArgumentNotValidException( final MethodArgumentNotValidException  ex, final WebRequest request) {

        	return new ResponseEntity<ErrorReturn>(
	 	 	    		  new ErrorReturn(   ex.getBindingResult().getAllErrors().get(0).getDefaultMessage()  ,
	 	 	    		  HttpStatus.BAD_REQUEST.value() ) ,  HttpStatus.BAD_REQUEST );
	}
	
}
