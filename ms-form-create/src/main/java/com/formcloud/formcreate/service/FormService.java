package com.formcloud.formcreate.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import com.formcloud.formcreate.constant.Err;
import com.formcloud.formcreate.domain.dto.FormDTO;
import com.formcloud.formcreate.domain.dto.FormDTOFilter;
import com.formcloud.formcreate.domain.entity.Form;
import com.formcloud.formcreate.domain.enums.StatusForm;
import com.formcloud.formcreate.repository.FormRepository;
import com.formcloud.springutil.database.SelectCustom;
import com.formcloud.springutil.errorhandler.ApiException;
import com.formcloud.springutil.util.UtilDate;
import com.formcloud.springutil.util.UtilNum;
import com.formcloud.springutil.util.UtilString;
import com.formcloud.springutil.valid.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class FormService {

    @Autowired
    private FormRepository formRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public FormDTO createNewOne(FormDTO formDTO) throws ApiException {
        
        Valid.objNull( Err.F_8_TITLE_REQUIRED.getMsg(), formDTO);
        Valid.stringNullOrTrimEmpty( Err.F_8_TITLE_REQUIRED.getMsg() , formDTO.getTitle() );
        Valid.check(   Err.F_2_KEY_MUST_BE_LESS_255_CARACT.getMsg() , formDTO.getTitle().length() >= 255 );
        Valid.check(   Err.F_3_COMMENT_MUST_BE_LESS_255_CARACT.getMsg() , formDTO.getComment() != null && formDTO.getComment().length() >= 255 );

        formDTO.setKey(UtilString.shortUUID());
        formDTO.setDateInsert(UtilDate.getNow());
        formDTO.setDateLastUpdate(UtilDate.getNow());
        formDTO.setVersionPrevious(0);
        formDTO.setVersion(1);
        formDTO.setStatus(StatusForm.JUST_CREATED.getId());
 
        return  FormDTO.buildIntoFormDTO(formRepository.save( formDTO.buildIntoForm()));
    }

    public FormDTO cloneToNewVersion(String key, Integer version) throws ApiException {
        Valid.stringNullOrTrimEmpty(Err.F_1_KEY_REQUIRED.getMsg(), key);
        Valid.objNull(Err.F_4_VERSION_REQUIRED.getMsg(), version);

        Form form = formRepository.getFormByKeyAndVersion(key, version);
        Valid.objNull(HttpStatus.NOT_FOUND, Err.F_6_KEY_AND_VERSION_NOT_FOUND.getMsg(), form);

        form.setKey(UtilString.shortUUID());
        form.setDateInsert(UtilDate.getNow());
        form.setDateLastUpdate(UtilDate.getNow());
        form.setVersionPrevious(form.getVersion());
        form.setVersion( formRepository.getNumLastVersionByKey(key) );
        form.setStatus(StatusForm.JUST_CLONED.getId());

        return  FormDTO.buildIntoFormDTO(formRepository.save(form));

    }

    public void submit(String key, Integer version) throws ApiException {
        Valid.stringNullOrTrimEmpty(Err.F_1_KEY_REQUIRED.getMsg(), key);
        Valid.objNull(Err.F_4_VERSION_REQUIRED.getMsg(), version);

        Form form = formRepository.getFormByKeyAndVersion(key, version);
        Valid.objNull(HttpStatus.NOT_FOUND, Err.F_6_KEY_AND_VERSION_NOT_FOUND.getMsg(), form);

        formRepository.changeStatusByKeyAndVersion(StatusForm.SUBMITED.getId(), UtilDate.getNow(), key, version);
    }


    public List<FormDTO> list(FormDTOFilter formDTOFilter) throws ApiException {

        SelectCustom selectCustom = new SelectCustom();
        selectCustom
        .col("form.key",
             "form.title",
             "form.comment",
             "form.date_insert",
             "form.date_last_update",
             "form.version",
             "form.version_previous",
             "form.status")
        .from("form");


        if( !UtilString.isEmptyTrim(formDTOFilter.getBy_key() )  ){
            selectCustom.andWhere("form.key = ? ", formDTOFilter.getBy_key() );
        }

        if( !UtilString.isEmptyTrim(formDTOFilter.getContain_litle() )  ){
            selectCustom.andWhere("form.title like ? ", "%" + formDTOFilter.getContain_litle() + "%");
        }

        if( !UtilString.isEmptyTrim(formDTOFilter.getOnly_removed())  ){
            selectCustom.andWhere("form.status = ? ", StatusForm.REMOVED);
        }else if(  !UtilString.isEmptyTrim(formDTOFilter.getBy_status() )   ){

            Valid.check( Err.F_7_STATUS_INVALID.getMsg(), !StatusForm.containId(UtilNum.parseToInt(formDTOFilter.getBy_status())));
            selectCustom.andWhere("form.status = ? ", UtilNum.parseToInt(formDTOFilter.getBy_status()));
        }


        if( !UtilString.isEmptyTrim(formDTOFilter.getContain_comment() )  ){
            selectCustom.andWhere("form.comment like ? ", "%" + formDTOFilter.getContain_comment() + "%");
        }
        selectCustom.setPaginationMysql(formDTOFilter.getPage_size(), formDTOFilter.getNum_page() );
        selectCustom.orderBy("key", SelectCustom.DESC);

        return jdbcTemplate
                    .queryForList(selectCustom.build(), selectCustom.getListValues().toArray())
                    .stream()
                    .map(
                        (x) -> new FormDTO( 
                                    (String) x.get("key") , 
                                    (Integer) x.get("version"), 
                                    (String)  x.get("title"),
                                    (String)  x.get("comment"), 
                                    ((Timestamp) x.get("date_insert")).toLocalDateTime(),
                                    (Integer)  x.get("version_previous"),
                                    (Integer)  x.get("status"),
                                    ((Timestamp) x.get("date_last_update")).toLocalDateTime() )
                    ).collect(Collectors.toList());
    }

    public FormDTO getLastVersionByKey(String key) throws ApiException {
        Valid.stringNullOrTrimEmpty(Err.F_1_KEY_REQUIRED.getMsg(), key);
        
        List<Form> form = formRepository.listFormByKeyOrderByVersionDesc(key, PageRequest.of(0,1));
        Valid.listNullOrEmpty(HttpStatus.NOT_FOUND, Err.F_5_KEY_NOT_FOUND.getMsg(), form);

        return  FormDTO.buildIntoFormDTO(form.get(0));
    }

    public FormDTO getByKeyAndVersion(String key, Integer version) throws ApiException {
        Valid.stringNullOrTrimEmpty(Err.F_1_KEY_REQUIRED.getMsg(), key);
        Valid.objNull(Err.F_4_VERSION_REQUIRED.getMsg(), version);
        
        Form form = formRepository.getFormByKeyAndVersion(key, version);
        Valid.objNull(HttpStatus.NOT_FOUND, Err.F_6_KEY_AND_VERSION_NOT_FOUND .getMsg(), form);
        return  FormDTO.buildIntoFormDTO(form);
    }

    public void update(String key, Integer version, FormDTO formDTO) {

        Valid.stringNullOrTrimEmpty(Err.F_1_KEY_REQUIRED.getMsg(), key);
        Valid.objNull(Err.F_4_VERSION_REQUIRED.getMsg(), version);
        Valid.objNull( Err.F_8_TITLE_REQUIRED.getMsg(), formDTO);
        Valid.stringNullOrTrimEmpty( Err.F_8_TITLE_REQUIRED.getMsg() , formDTO.getTitle() );
        Valid.check(   Err.F_2_KEY_MUST_BE_LESS_255_CARACT.getMsg() , formDTO.getTitle().length() >= 255 );
        Valid.check(   Err.F_3_COMMENT_MUST_BE_LESS_255_CARACT.getMsg() , formDTO.getComment() != null && formDTO.getComment().length() >= 255 );

        
        Form formToUpdate = formRepository.getFormByKeyAndVersion(key, version);
        Valid.objNull(HttpStatus.NOT_FOUND, Err.F_6_KEY_AND_VERSION_NOT_FOUND .getMsg(), formToUpdate);

        formToUpdate.setComment(formDTO.getComment());
        formToUpdate.setTitle(formDTO.getTitle());
        formToUpdate.setDateLastUpdate(UtilDate.getNow());

        formRepository.save(formToUpdate);

    }

    public void disable(List<FormDTO> formDTOList) {
        // TODO: will redirect to rabbitmq
    }

    public void remove(List<FormDTO> formDTOList) {
        // TODO: will redirect to rabbitmq
    }
    
}
