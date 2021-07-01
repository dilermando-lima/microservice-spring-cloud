package com.formcloud.formcreate.constant;

public enum Err{
    
   F_1_KEY_REQUIRED("key is required"), 
   F_2_KEY_MUST_BE_LESS_255_CARACT("key must be less than 255 caract"),
   F_3_COMMENT_MUST_BE_LESS_255_CARACT("key must be less than 255 caract"),
   F_4_VERSION_REQUIRED("version is required"),
   F_5_KEY_NOT_FOUND("key is not found"),
   F_6_KEY_AND_VERSION_NOT_FOUND("key and version are not found"),
   F_7_STATUS_INVALID("status is not valid"),
   F_8_TITLE_REQUIRED("title is required"), 


   Q_1_KEY_FORM_REQUIRED("keyForm is required"),
   Q_2_TITLE_REQUIRED("title is required"), 
   Q_3_TYPE_REQUIRED("type is required"), 
   Q_4_POSITION_REQUIRED("position is required"), 
   Q_5_COMMENT_MUST_BE_LESS_THAN_255("comment must be less than 255 caract"), 
   Q_6_WIDTH_NOT_VALID("width is not valid"),
   Q_7_REQUIRED_NOT_VALID("required is not valid"),
   Q_8_TYPE_NOT_VALID("required is not valid"),
   Q_9_TYPE_NOT_VALID("you need create at leat one question to saving form"),
   Q_10_VERSION_FORM_REQUIRED("versionForm is required"),
   Q_11_KEYF_AND_VERSIONF_NOT_FOUND("keyForm and versionForm are not found"),

   ;

   private String msg;

   Err(String msg) {
	   this.msg=msg;
	}

	public String getMsg() {    
         return  String.format("%s ( %s.%s )", this.msg, this.name().split("_")[0] , this.name().split("_")[1]);
	}

}
