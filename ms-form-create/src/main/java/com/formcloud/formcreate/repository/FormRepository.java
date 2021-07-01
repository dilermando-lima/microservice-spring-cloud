package com.formcloud.formcreate.repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import com.formcloud.formcreate.domain.entity.Form;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FormRepository extends JpaRepository<Form, String> {

    @Query("select obj from #{#entityName} obj where key = ?1 order by version desc")
    List<Form> listFormByKeyOrderByVersionDesc(String key, Pageable pageable);

    @Query("select obj from #{#entityName} obj where key = ?1 and version = ?2 ")
    Form getFormByKeyAndVersion(String key, Integer version);

    @Query("select max(version) from #{#entityName} obj where key = ?1")
    Integer getNumLastVersionByKey(String key);

    @Modifying(clearAutomatically=true)  @Transactional 
    @Query("update #{#entityName} obj set obj.status = ?1 , obj.dateLastUpdate = ?2  where obj.key = ?3 and obj.version = ?4 ") 
    void changeStatusByKeyAndVersion( Integer status ,  LocalDateTime dateLastUpdate, String key, Integer version);

}
