package com.formcloud.archamqp.entity.form;

import java.time.LocalDateTime;

import com.formcloud.archamqp.abstracts.EntityBase;

public class QuestionMSG extends EntityBase {

    private String id;
    private String keyForm;
    private String title;
    private String comment;
    private Integer type;
    private Integer required;
    private Integer width;
    private Integer versionForm;
    private Integer position;
    private LocalDateTime dateInsert;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
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
    public Integer getVersionForm() {
        return versionForm;
    }
    public void setVersionForm(Integer versionForm) {
        this.versionForm = versionForm;
    }
    public Integer getPosition() {
        return position;
    }
    public void setPosition(Integer position) {
        this.position = position;
    }
    public LocalDateTime getDateInsert() {
        return dateInsert;
    }
    public void setDateInsert(LocalDateTime dateInsert) {
        this.dateInsert = dateInsert;
    }

    
    
}
