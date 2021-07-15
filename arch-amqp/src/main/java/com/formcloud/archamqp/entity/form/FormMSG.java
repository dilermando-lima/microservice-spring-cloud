package com.formcloud.archamqp.entity.form;

import java.time.LocalDateTime;
import java.util.List;

import com.formcloud.archamqp.abstracts.EntityBase;

public class FormMSG extends EntityBase{

    private String id;
    private String keyForm;
    private Integer versionForm;
    private String title;
    private String comment;
    private LocalDateTime dateInsert;
    private Integer versionPrevious;
    private Integer status;
    private LocalDateTime dateLastUpdate;
    private List<QuestionMSG> questionList;

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

    public List<QuestionMSG> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<QuestionMSG> questionList) {
        this.questionList = questionList;
    }

    

    
}
