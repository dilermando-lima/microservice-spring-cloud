package com.formcloud.formcreate.domain.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.formcloud.formcreate.domain.entity.Question;
import com.formcloud.springutil.util.UtilClass;


public class QuestionDTO {
    
    private String id;
    private String keyForm;
    private String title;
    private String comment;
    private Integer type;
    private Integer required;
    private Integer width;
    private Integer versionForm;
    private Integer position;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "date_insert")
    private LocalDateTime dateInsert;


    public Question buildIntoQuestion() throws RuntimeException{
        return UtilClass.convertObject(this, Question.class);
    }

    public static QuestionDTO buildIntoQuestionDTO(Question question) throws RuntimeException{
        return UtilClass.convertObject(question, QuestionDTO.class);
    }

    public QuestionDTO() {
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
