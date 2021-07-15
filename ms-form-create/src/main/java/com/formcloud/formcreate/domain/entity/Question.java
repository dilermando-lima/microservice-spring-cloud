package com.formcloud.formcreate.domain.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.formcloud.archcommons.util.UtilString;

@Entity
@Table(name="question")
public class Question {
    
    @Id
    private String id;

    @PrePersist
    public void buildId() {
        this.id = UtilString.shortUUID();
    }

    @Column(name = "key_form")
    private String keyForm;

    @Column(name = "version_form")
    private Integer versionForm;

    private String title;
    private String comment;
    private Integer type;
    private Integer required;
    private Integer width;
    private Integer position;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "date_insert")
    private LocalDateTime dateInsert;

    
    public Question() {
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

    public Integer getVersionForm() {
        return versionForm;
    }

    public void setVersionForm(Integer versionForm) {
        this.versionForm = versionForm;
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public Integer getPosition() {
        return position;
    }


    public void setPosition(Integer position) {
        this.position = position;
    }
    
    
}
