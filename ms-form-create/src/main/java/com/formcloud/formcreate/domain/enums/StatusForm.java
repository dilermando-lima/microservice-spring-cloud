package com.formcloud.formcreate.domain.enums;

public enum StatusForm {
    
    JUST_CREATED(1),
    JUST_CLONED(2),
    ON_DRAFT(3),
    SUBMITED(4),
    DISABLED(5),
    REMOVED(6);
    
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
