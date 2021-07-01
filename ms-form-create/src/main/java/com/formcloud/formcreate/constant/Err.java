package com.formcloud.formcreate.constant;

public enum Err{
    
   E_1_1_KEY_REQUIRED("key is required"), 
   E_1_2_KEY_MUST_BE_LESS_255_CARACT("key must be less than 255 caract"),
   E_1_3_COMMENT_MUST_BE_LESS_255_CARACT("key must be less than 255 caract"),
   E_1_4_VERSION_REQUIRED("version is required"),
   E_1_5_KEY_NOT_FOUND("key is not found"),
   E_1_6_KEY_AND_VERSION_NOT_FOUND("key and version are not found"),
   E_1_7_STATUS_INVALID("status is not valid"),
   E_1_8_TITLE_REQUIRED("title is required"), 

   ;

   private String msg;

   Err(String msg) {
	   this.msg=msg;
	}

	public String getMsg() {
      if( this.name().startsWith("E_") )
         return  String.format("%s ( %s.%s )", this.msg, this.name().split("_")[1] , this.name().split("_")[2]);
      else
		   return this.msg;
	}

}
