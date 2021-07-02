package com.formcloud.formcreate.domain.enums;

public enum StatusForm {

    ON_DRAFT(1),     // CREATED OR CLONED CONTAINS AT LEAST ONE QUESTION
    PUBLISHED(2),    // PUBLISHED AND AVAILABLE TO REVEICE ANSWERS
    UNPUBLISHED(3),  // NOT UNPUBLISHED ANY MORE BUT ALL ANSWERS ARE AVAILABLE 
    CLOSED(4),       // NOT RECEIVING ANSWERS, ADD FEATURE ABOUT EXPIRING DATE THAT WILL CLOSE AUTOMATICLY AND ONE EMAIL TO RECEIVE LINK TO RESPONSES
    DISABLED(5),     // DISABLED CANNOT BE CLONED, RECEIVE ANSWERS AND MAY BE REMOVED  
    REMOVED(6);      // ON LIST TO BE DELETED ON BATCH PROCESS
    
    private final Integer id;

    StatusForm(Integer id) {
		this.id=id;
	}

    public static boolean containId(Integer id ){
        if( id == null ) return false;
        for (StatusForm valueToFind : StatusForm.values() )
             if(  valueToFind.getId() ==  id) return true;
        return false;
    }

    public static StatusForm getById(Integer id ){
        if( id == null ) return null;
        for (StatusForm valueToFind : StatusForm.values() )
        if(  valueToFind.getId() ==  id) return valueToFind;
        return null;
    }

	public Integer getId() {
		return this.id;
	}
}
