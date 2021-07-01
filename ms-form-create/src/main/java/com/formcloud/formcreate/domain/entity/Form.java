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
    public void buildId() {
        if( this.key == null && this.version == null ) throw new RuntimeException("key and version cannot be null");
        this.id = this.key + "-" + this.version;
    }

    private String key;
    private Integer version;
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
