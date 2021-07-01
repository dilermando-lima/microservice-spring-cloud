package com.formcloud.formcreate.domain.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="question")
public class Question {
    
    @Id
    private String key;
    @Column(name = "key_form")
    private String keyForm;
    private String title;
    private String comment;
    private Integer type;
    private Integer required;
    private Integer width;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "date_insert")
    private LocalDateTime dateInsert;

    
    public Question() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKeyForm() {
        return keyForm;
    }

    public void setKeyForm(String keyForm) {
        this.keyForm = keyForm;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getRequired() {
        return required;
    }

    public void setRequired(Integer required) {
        this.required = required;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public LocalDateTime getDateInsert() {
        return dateInsert;
    }

    public void setDateInsert(LocalDateTime dateInsert) {
        this.dateInsert = dateInsert;
    }


    

}
