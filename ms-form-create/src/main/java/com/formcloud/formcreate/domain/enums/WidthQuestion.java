package com.formcloud.formcreate.domain.enums;

public enum WidthQuestion {
    W25(25),
    W50(50),
    W33(33),
    W66(66),
    W75(75),
    W100(100);
    
    private final Integer id;

    WidthQuestion(Integer id) {
		this.id=id;
	}

    public static boolean containId(Integer id ){
        if( id == null ) return false;
        for (WidthQuestion valueToFind : WidthQuestion.values() )
             if(  valueToFind.getId() ==  id) return true;
        return false;
    }

    public static WidthQuestion getById(Integer id ){
        if( id == null ) return null;
        for (WidthQuestion valueToFind : WidthQuestion.values() )
        if(  valueToFind.getId() ==  id) return valueToFind;
        return null;
    }

	public Integer getId() {
		return this.id;
	}
}
