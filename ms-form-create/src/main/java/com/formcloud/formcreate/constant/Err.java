package com.formcloud.formcreate.constant;

public enum Err{
    
   F_1_KEY_REQUIRED("keyForm is required"), 
   F_2_KEY_MUST_BE_LESS_255_CARACT("keyForm must be less than 255 caract"),
   F_3_COMMENT_MUST_BE_LESS_255_CARACT("keyForm must be less than 255 caract"),
   F_4_VERSION_REQUIRED("versionForm is required"),
   F_5_KEY_NOT_FOUND("keyForm is not found"),
   F_6_KEY_AND_VERSION_NOT_FOUND("keyForm and versionForm are not found"),
   F_7_STATUS_INVALID("status is not valid"),
   F_8_TITLE_REQUIRED("title is required"),
   F_9_FORM_ALREADY_PUBLISHED("form has already been published"),
   F_11_FORM_MUST_BE_PUBLISHED("form must be published to this operation"),
   F_12_FORM_MUST_BE_DISABLED("form must be disabled to this operation"),
   F_13_FORM_MUST_BE_ON_DRAFT("form must be on draft to this operation"),
   F_14_FORM_MUST_HAVE_QUESTION_TO_PUBULISH("you need to create at leat one question to this operation"),
   F_15_FORM_NOT_DISABLED_TO_REMOVE("form '%s' has not been disabled then cannot be removed"),
   F_16_FORM_PUBLISHED_CANNOT_BE_DISABLE("published form cannot be disabled"),

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
