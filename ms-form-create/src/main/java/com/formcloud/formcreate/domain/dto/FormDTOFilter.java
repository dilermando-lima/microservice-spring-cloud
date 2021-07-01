package com.formcloud.formcreate.domain.dto;

public class FormDTOFilter {
    
    private String contain_litle;
    private String contain_comment;
    private String num_page;
    private String page_size;
    private String by_status;
    private String only_removed;
    private String by_key;

    public String getBy_status() {
        return by_status;
    }
    public void setBy_status(String by_status) {
        this.by_status = by_status;
    }
    public String getOnly_removed() {
        return only_removed;
    }
    public void setOnly_removed(String only_removed) {
        this.only_removed = only_removed;
    }
    public String getContain_litle() {
        return contain_litle;
    }
    public void setContain_litle(String contain_litle) {
        this.contain_litle = contain_litle;
    }
    public String getContain_comment() {
        return contain_comment;
    }
    public void setContain_comment(String contain_comment) {
        this.contain_comment = contain_comment;
    }
    public String getNum_page() {
        return num_page;
    }
    public void setNum_page(String num_page) {
        this.num_page = num_page;
    }
    public String getPage_size() {
        return page_size;
    }
    public void setPage_size(String page_size) {
        this.page_size = page_size;
    }
    public String getBy_key() {
        return by_key;
    }
    public void setBy_key(String by_key) {
        this.by_key = by_key;
    }

    

}
