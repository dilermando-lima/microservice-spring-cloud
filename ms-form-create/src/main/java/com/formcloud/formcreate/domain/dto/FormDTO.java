package com.formcloud.formcreate.domain.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.formcloud.formcreate.domain.entity.Form;
import com.formcloud.springutil.util.UtilClass;

public class FormDTO {

    private String id;
    private String key;
    private Integer version;
    private String title;
    private String comment;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateInsert;
    private Integer versionPrevious;
    private Integer status;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateLastUpdate;

    public FormDTO() {
    }

    public Form buildIntoForm() throws RuntimeException{
        return UtilClass.convertObject(this, Form.class);
    }

    public static FormDTO buildIntoFormDTO(Form form) throws RuntimeException{
        return UtilClass.convertObject(form, FormDTO.class);
    }
    

    public FormDTO(String key, Integer version, String title, String comment, LocalDateTime dateInsert,
            Integer versionPrevious, Integer status, LocalDateTime dateLastUpdate) {
        this.key = key;
        this.version = version;
        this.title = title;
        this.comment = comment;
        this.dateInsert = dateInsert;
        this.versionPrevious = versionPrevious;
        this.status = status;
        this.dateLastUpdate = dateLastUpdate;
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
