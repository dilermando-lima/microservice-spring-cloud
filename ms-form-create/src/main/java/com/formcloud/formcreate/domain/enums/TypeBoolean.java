package com.formcloud.formcreate.domain.enums;

public enum TypeBoolean {
    
    TRUE(1),
    FALSE(0);
        
    private final Integer id;

    TypeBoolean(Integer id) {
		this.id=id;
	}

    public static boolean containId(Integer id ){
        if( id == null ) return false;
        for (TypeBoolean valueToFind : TypeBoolean.values() )
             if(  valueToFind.getId() ==  id) return true;
        return false;
    }

    public static TypeBoolean getById(Integer id ){
        if( id == null ) return null;
        for (TypeBoolean valueToFind : TypeBoolean.values() )
        if(  valueToFind.getId() ==  id) return valueToFind;
        return null;
    }

	public Integer getId() {
		return this.id;
	}
}
