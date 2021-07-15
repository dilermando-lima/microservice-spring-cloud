package com.formcloud.archamqp.abstracts;

import org.springframework.amqp.core.ExchangeTypes;

public enum ExchangeType {

    DIRECT(ExchangeTypes.DIRECT),
    FANOUT(ExchangeTypes.FANOUT);

    ExchangeType(String type) {
		this.type=type;
	}

    private final String type;

    public static boolean containType(String type ){
        if( type == null ) return false;
        for (ExchangeType valueToFind : ExchangeType.values() )
             if(  valueToFind.getType().equals(type) ) return true;
        return false;
    }

    public static ExchangeType getByType(String type ){
        if( type == null ) return null;
        for (ExchangeType valueToFind : ExchangeType.values() )
            if( valueToFind.getType().equals(type)  ) return valueToFind;
        return null;
    }

	public String getType() {
		return this.type;
	}
    
}
