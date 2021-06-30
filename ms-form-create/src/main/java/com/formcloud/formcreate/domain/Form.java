package com.formcloud.formcreate.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="form")
public class Form {

    @Id 
    /*@GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")*/
    private String key;
    private Integer version;
    private String title;
    private String comment;

    public Form() {
    }
    public Form(String key, Integer version, String title, String comment) {
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
