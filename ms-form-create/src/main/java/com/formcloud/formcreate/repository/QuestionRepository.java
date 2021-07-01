package com.formcloud.formcreate.repository;

import com.formcloud.formcreate.domain.entity.Question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, String> {
    @Query("select obj from #{#entityName} obj where key = ?1 ")
    Question getQuestionByKey(String key);

    @Query("select obj from #{#entityName} obj where key_form = ?1 ")
    Question getQuestionByKeyForm(String keyForm);
}
