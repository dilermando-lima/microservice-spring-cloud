package com.formcloud.formcreate.domain.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="form")
public class Form {

    @Id
    private String id;

    @PrePersist
    public void buildNewId() {
        if( this.keyForm == null && this.versionForm == null ) throw new RuntimeException("keyForm and versionForm cannot be null");
        this.id = this.keyForm + "-" + this.versionForm;
    }

    @Column(name = "key_form")
    private String keyForm;

    @Column(name = "version_form")
    private Integer versionForm;

    @Column(name="version_previous")
    private Integer versionPrevious;
    private String title;
    private String comment;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "date_insert")
    private LocalDateTime dateInsert;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "date_last_update")
    private LocalDateTime dateLastUpdate;
    
    private Integer status;

    public Form() {
    }

    public String getKeyForm() {
        return keyForm;
    }

    public void setKeyForm(String keyForm) {
        this.keyForm = keyForm;
    }

    public Integer getVersionForm() {
        return versionForm;
    }

    public void setVersionForm(Integer versionForm) {
        this.versionForm = versionForm;
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
    public LocalDateTime getDateInsert() {
        return dateInsert;
    }
    public void setDateInsert(LocalDateTime dateInsert) {
        this.dateInsert = dateInsert;
    }

    public Integer getVersionPrevious() {
        return versionPrevious;
    }

    public void setVersionPrevious(Integer versionPrevious) {
        this.versionPrevious = versionPrevious;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getDateLastUpdate() {
        return dateLastUpdate;
    }

    public void setDateLastUpdate(LocalDateTime dateLastUpdate) {
        this.dateLastUpdate = dateLastUpdate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
}
