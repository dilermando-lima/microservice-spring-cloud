package com.formcloud.formcreate.service;

import java.util.List;

import com.formcloud.formcreate.domain.Form;
import com.formcloud.formcreate.repository.FormRepository;
import com.formcloud.springutil.errorhandler.ApiException;
import com.formcloud.springutil.util.UtilString;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormService {

    @Autowired
    private FormRepository formRepository;

    public String create(Form formDTO) throws ApiException {
        formDTO.setKey(UtilString.shortUUID());
        return  formRepository.save( formDTO).getKey();
    }

    public List<Form> list() throws ApiException {
        return  formRepository.findAll();
    }


    public Form getByKey(String key) throws ApiException {
        return  formRepository.getFormByKey(key);
    }
    

    
}
