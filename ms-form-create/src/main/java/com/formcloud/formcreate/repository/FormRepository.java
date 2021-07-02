package com.formcloud.formcreate.repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import com.formcloud.formcreate.domain.entity.Form;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FormRepository extends JpaRepository<Form, String> {

    @Query("select obj from #{#entityName} obj where keyForm = ?1 and versionForm = ?2 ")
    Form getFormByKeyAndVersion(String keyForm, Integer versionForm);

    @Query("select obj from #{#entityName} obj where keyForm = ?1 and status = ?2 ")
    List<Form> listFormByKeyAndStatus(String keyForm, Integer status);

    @Query("select obj from #{#entityName} obj where keyForm = ?1")
    List<Form> listFormByKey(String keyForm);

    @Query("select max(versionForm) from #{#entityName} obj where keyForm = ?1")
    Integer getNumLastVersionByKey(String keyForm);

    @Modifying(clearAutomatically=true)  @Transactional 
    @Query("update #{#entityName} obj set obj.status = ?1 , obj.dateLastUpdate = ?2  where obj.keyForm = ?3 and obj.versionForm = ?4 ") 
    void changeStatusByKeyAndVersion( Integer status ,  LocalDateTime dateLastUpdate, String keyForm, Integer versionForm);

    @Modifying(clearAutomatically=true)  @Transactional 
    @Query("update #{#entityName} obj set obj.status = ?1 , obj.dateLastUpdate = ?2  where obj.keyForm = ?3 ") 
    void changeStatusByKey( Integer status ,  LocalDateTime dateLastUpdate, String keyForm);

}
