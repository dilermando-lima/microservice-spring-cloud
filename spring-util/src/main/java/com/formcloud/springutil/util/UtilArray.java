package com.formcloud.springutil.util;

import java.util.List;

public class UtilArray {


    /**
     * 
     * @param arrayCurrent
     * @param lentghToNewArray
     * @param valuesToFillOnEmptyPositions
     * @implNote
     *  This method will return a new array with new lenght positions.
     *  The parameter lentghToNewArray will define the lenght to new return array
     *  The parameter valuesToFillOnEmptyPositions will add into new positions has no values from new array 
     * @return 
     */
    public static String [] getArrayStringWithPositionsFilled(String [] arrayCurrent , int lentghToNewArray, String valuesToFillOnEmptyPositions ){

        String [] newArrayWithNewLength  = new String[lentghToNewArray];
        for (int i = 0; i < newArrayWithNewLength.length; i++) {
            if( arrayCurrent != null && i+1 <= arrayCurrent.length ){
                if( arrayCurrent[i] == null  )
                    newArrayWithNewLength[i] = valuesToFillOnEmptyPositions;
                else
                    newArrayWithNewLength[i] = arrayCurrent[i];
            }else{
                newArrayWithNewLength[i] = valuesToFillOnEmptyPositions;
            }
        }
        return newArrayWithNewLength;
    }


    public static String toStringArray(Object[] array){

        if( array == null ) return "{null}";
        if( array.length == 0 ) return "{[]}";

        String toStringReturn = "{";
        for (int i = 0; i < array.length; i++) {
            toStringReturn+= String.format("[%d]='%s', ", i, array[i]);
        }
        toStringReturn+= "} ";

        return toStringReturn;
    }

    public static String toStringList(List<?> list){

        if( list == null ) return "{null}";
        if( list.size() == 0 ) return "{[]}";

        String toStringReturn = "{";
        int i = 0;
        for (Object obj : list) {
            toStringReturn+= String.format("[%d]='%s', ", i, obj);
            i++;
        }
        toStringReturn+= "} ";
        
        return toStringReturn;
    }

    public static String arrayToCommaSeparatedString(List<String> list) {
        return "'''" + String.join("'',''", list) + "'''";
    }


    
}
