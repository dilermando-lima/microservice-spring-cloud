package com.formcloud.formcreate.repository;

import java.util.List;

import javax.transaction.Transactional;

import com.formcloud.formcreate.domain.entity.Question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, String> {

    @Query("select obj from #{#entityName} obj where key_form = ?1 And version_form = ?2 ")
    List<Question> listQuestionByKeyAndVersionForm(String keyForm, Integer versionForm);

    @Modifying(clearAutomatically=true)  @Transactional 
    @Query("delete #{#entityName} obj where key_form = ?1") 
    void deleteByKeyForm(String keyForm);
    
}
