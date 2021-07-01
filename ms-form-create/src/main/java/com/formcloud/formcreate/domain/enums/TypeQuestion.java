package com.formcloud.formcreate.domain.enums;

public enum TypeQuestion {
    TEXT_255(1),
    TEXT_MULT_255(2),
    NUM_INT(3),
    NUM_FLOAT(4),
    DATE_YYYYMMDD(5),
    DATE_YYYYMM(6),
    RATE5(7),
    BOOLEAN_0_1(8),
    RATE10(9);

    private final Integer id;

    TypeQuestion(Integer id) {
		this.id=id;
	}


    public static boolean containId(Integer id ){
        if( id == null ) return false;
        for (TypeQuestion valueToFind : TypeQuestion.values() )
             if(  valueToFind.getId() ==  id) return true;
        return false;
    }

    public static TypeQuestion getById(Integer id ){
        if( id == null ) return null;
        for (TypeQuestion valueToFind : TypeQuestion.values() )
        if(  valueToFind.getId() ==  id) return valueToFind;
        return null;
    }

	public Integer getId() {
		return this.id;
	}


}
