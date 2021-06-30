package com.formcloud.formcreate.domain;

public class FormDTO {

    private String key;
    private Integer version;
    private String title;
    private String comment;

    public FormDTO() {
    }
    public FormDTO(String key, Integer version, String title, String comment) {
        this.key = key;
        this.version = version;
        this.title = title;
        this.comment = comment;
    }

    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public Integer getVersion() {
        return version;
    }
    public void setVersion(Integer version) {
        this.version = version;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }


    
}
