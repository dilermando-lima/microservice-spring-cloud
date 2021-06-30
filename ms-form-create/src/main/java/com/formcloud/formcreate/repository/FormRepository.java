package com.formcloud.formcreate.repository;

import com.formcloud.formcreate.domain.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FormRepository extends JpaRepository<Form, String> {
    @Query("select obj from #{#entityName} obj where key = ?1 ")
    Form getFormByKey(String key);
}
