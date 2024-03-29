package com.formcloud.springutil.database;

import java.util.LinkedList;
import java.util.List;

import com.formcloud.springutil.errorhandler.ApiException;
import com.formcloud.springutil.util.UtilNum;

import org.springframework.http.HttpStatus;


public class SelectCustom {
    
    final public static String ASC = "asc";
    final public static String DESC = "desc";

    private boolean containsWhere;

    private String errorMsg;
    private String cols;
    private String table;
    private String inners;
    private String where;
    private String orderBy;
    private String pagination;

    private List<Object> listValues;
    int numPage;
    int pageSize;


    public SelectCustom() {
        this.containsWhere = false;
        listValues = new LinkedList<Object>();
        errorMsg = null;

        cols = "";
        table = "";
        inners = "";
        where = "";
        orderBy = "";
        pagination = "";
    }

    
    public String build() throws ApiException {

            String colsCopy = "";
            String whereCopy = "";
            
            if( errorMsg != null )  throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, errorMsg);

            if( cols.trim().endsWith(",") ) colsCopy = cols.trim().substring(0, cols.trim().length() - 1);

            if( containsWhere  ) whereCopy = " where " + where;

            String built = String.format(" select %s from %s %s %s %s %s",colsCopy,table, inners, whereCopy, orderBy, pagination );

            validateSelectSql(built);

            return built.replace("  ", " ");
           
    }


    public SelectCustom col(String col){
        
        if( col == null){  errorMsg = "Cols cannot be null on Select.col()"; return this;}

        if( col.trim().endsWith(",")  ) col = col.trim().substring(0, col.trim().length() - 1);

        cols+= String.format(" %s, ",col);
        return this;
    }

    public SelectCustom col(String... col){
        if( col == null ) return this;
        for (String string : col) {
            col(string);
        }
        return this;
   }


    public SelectCustom from(String table){
        if( table == null )  {  errorMsg = "Table cannot be null on Select.from()"; return this;}

        this.table = table;
        return this;
    }

    public SelectCustom innerJoin(String tableJoin, String colLeft, String colRight){
        
        if( tableJoin == null || colLeft == null || colRight == null ) 
        {  errorMsg = "TableJoin, ColLeft or ColRight cannot be null on Select.innerJoin()"; return this;}

        inners+=  String.format(" inner join %s on %s = %s ",tableJoin, colLeft,colRight);
        return this;
    }

    public SelectCustom leftJoin(String tableJoin, String colLeft, String colRight){
        
        if( tableJoin == null || colLeft == null || colRight == null ) 
        {  errorMsg = "TableJoin, ColLeft or ColRight cannot be null on Select.leftJoin()"; return this;}

        inners+=  String.format(" left join %s on %s = %s ",tableJoin, colLeft,colRight);
        return this;
    }


    public SelectCustom andWhere(String whereand){
        return andWhere(whereand, new Object[]{});
    }

    public SelectCustom andWhere(String whereand ,   Object... ObjParam){

        if( whereand == null  ) 
        {  errorMsg = "whereand cannot be null on Select.andWhere()"; return this;}


        if(   !containsWhere  ){
            containsWhere = true;
            where+= " " + whereand + " ";
        }else if( !where.trim().endsWith("and") ){
            where+= " and " + whereand + " ";
        }else{
            where+= " " + whereand + " ";
        }

        if(  ObjParam != null  ){
            for (Object object : ObjParam) {
                listValues.add(object);
            }
        }
       
        return this;
    }

    public SelectCustom orWhere(String whereand){
        return orWhere(whereand,new Object[]{});
    }
    
    public SelectCustom orWhere(String whereor ,   Object... ObjParam){

        if( whereor == null  ) 
        {  errorMsg = "whereor cannot be null on Select.orWhere()"; return this;}

        
        if(   !containsWhere  ){
            containsWhere = true;
            where+= " " + whereor + " ";
        }if( !where.trim().endsWith("or") ){
            where+= " or " + whereor + " ";
        }else{
            where+= " " + whereor + " ";
        }

        if(  ObjParam != null  ){
            for (Object object : ObjParam) {
                listValues.add(object);
            }
        }
        return this;
    }


    public SelectCustom orderBy(String col, String order ){

        if( col == null ||  order == null  ) 
        {  errorMsg = "col or order cannot be null on Select.orderBy()"; return this;}

        
        if( !orderBy.contains("order by")){
            orderBy+= " order by ";
        }else{
            orderBy+= " , ";
        }

        orderBy+= String.format(" %s %s ", col, order);
        return this;
    }

    
    public void setPaginationMysql( String pageSize, String numPage ){
        if(  UtilNum.parseToInt(pageSize) == 0  ) pageSize = "10"; // default value to pageSise
        this.pagination = String.format(" limit %d  offset %d ",
                                        UtilNum.parseToInt(pageSize), 
                                        UtilNum.parseToInt(numPage)  * UtilNum.parseToInt(pageSize) );

    }


    public void setPaginationPostgreSql( String pageSize, String numPage ){
        if(  UtilNum.parseToInt(pageSize) == 0  ) pageSize = "10"; // default value to pageSise
        this.pagination = String.format(" limit %d  offset %d ",
                                        UtilNum.parseToInt(pageSize), 
                                        UtilNum.parseToInt(numPage)  * UtilNum.parseToInt(pageSize) );

    }

    public void setPaginationSqlServer( String pageSize, String numPage ){
        if(  UtilNum.parseToInt(pageSize) == 0  ) pageSize = "10"; // default value to pageSise
        this.pagination = String.format(" offset %d  rows fetch next %d rows only ",
                                        UtilNum.parseToInt(numPage), 
                                        UtilNum.parseToInt(pageSize) );
    }

    public void setPaginationOracle12( String pageSize, String numPage ){
        if(  UtilNum.parseToInt(pageSize) == 0  ) pageSize = "10"; // default value to pageSise
        this.pagination = String.format(" offset %d  rows fetch next %d rows only ",
                                        UtilNum.parseToInt(numPage), 
                                        UtilNum.parseToInt(pageSize) );
    }

    public void setPaginationOracle11( String pageSize ){
        if(  UtilNum.parseToInt(pageSize) == 0  ) pageSize = "10"; // default value to pageSise
        andWhere(String.format(" rownum <= %d " , UtilNum.parseToInt(pageSize) ));
    }

    public List<Object> getListValues() {
        return listValues;
    }

    
    private static void validateSelectSql(String regexSelect) throws ApiException{
        if( regexSelect == null )   throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR,"select cannot be null");
        regexSelect =  regexSelect.toLowerCase();
		if( regexSelect.contains("inner")){
			if(  !regexSelect.matches(".*select .*? from .*? join .*") ){
                            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "select dosen't match to '.*select .*? from .*? join .*' ");
                    }	 
		}else if( regexSelect.contains(" where ") ){
			if(  !regexSelect.matches(".*select .*? from .*? where .*")){
                throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR,"select dosen't match to '.*select .*? from .*? where .*' ");
            }
		}else{ 
			if( !regexSelect.matches(".*select .*? from .*") ){
                throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR,"select dosen't match to '.*select .*? from .*' ");
            }
		}

    }



}
