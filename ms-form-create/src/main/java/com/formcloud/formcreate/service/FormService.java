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
import com.formcloud.formcreate.repository.QuestionRepository;
import com.formcloud.springutil.database.SelectCustom;
import com.formcloud.springutil.errorhandler.ApiException;
import com.formcloud.springutil.util.UtilDate;
import com.formcloud.springutil.util.UtilNum;
import com.formcloud.springutil.util.UtilString;
import com.formcloud.springutil.valid.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class FormService {

    @Autowired
    private FormRepository formRepository;

    @Autowired
    private QuestionRepository questionRepository;


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SendQueueService sendQueueService;

    public FormDTO create(FormDTO formDTO) throws ApiException {
        
        Valid.objNull(Err.F_8_TITLE_REQUIRED.getMsg(), formDTO);
        Valid.stringNullOrTrimEmpty( Err.F_8_TITLE_REQUIRED.getMsg() , formDTO.getTitle() );
        Valid.check(Err.F_2_KEY_MUST_BE_LESS_255_CARACT.getMsg() , formDTO.getTitle().length() >= 255 );
        Valid.check(Err.F_3_COMMENT_MUST_BE_LESS_255_CARACT.getMsg() , formDTO.getComment() != null && formDTO.getComment().length() >= 255 );

        formDTO.setKeyForm(UtilString.shortUUID());
        formDTO.setDateInsert(UtilDate.getNow());
        formDTO.setDateLastUpdate(UtilDate.getNow());
        formDTO.setVersionPrevious(0);
        formDTO.setVersionForm(1);
        formDTO.setStatus(StatusForm.ON_DRAFT.getId());
        formDTO.setId(null); // generate new one

        return  FormDTO.buildIntoFormDTO(formRepository.save( formDTO.buildIntoForm()));
    }

    public FormDTO draft(String keyForm, Integer versionForm) throws ApiException {
        Valid.stringNullOrTrimEmpty(Err.F_1_KEY_REQUIRED.getMsg(), keyForm);
        Valid.objNull(Err.F_4_VERSION_REQUIRED.getMsg(), versionForm);

        Form form = formRepository.getFormByKeyAndVersion(keyForm, versionForm);
        Valid.objNull(HttpStatus.NOT_FOUND, Err.F_6_KEY_AND_VERSION_NOT_FOUND.getMsg(), form);
        Valid.check(HttpStatus.NOT_FOUND, Err.F_6_KEY_AND_VERSION_NOT_FOUND.getMsg(), form.getStatus() == StatusForm.REMOVED.getId() );

        form.setKeyForm(form.getKeyForm());
        form.setDateInsert(UtilDate.getNow());
        form.setDateLastUpdate(UtilDate.getNow());
        form.setVersionPrevious(form.getVersionForm());
        form.setVersionForm(formRepository.getNumLastVersionByKey(keyForm) + 1 );
        form.setStatus(StatusForm.ON_DRAFT.getId());
        form.setId(null); // generate new one

        Form formInserted = formRepository.save(form);

        copyQuestionToOtherForm(keyForm, versionForm, formInserted.getKeyForm(), formInserted.getVersionForm());

        return  FormDTO.buildIntoFormDTO(formInserted);

    }


    private void copyQuestionToOtherForm(String keyFormFROM, Integer versionFormFROM, String keyFormTO, Integer versionFormTO ) throws ApiException {

        questionRepository.listQuestionByKeyAndVersionForm(keyFormFROM, versionFormFROM)
                          .forEach( q ->{
                                q.setKeyForm(keyFormTO);
                                q.setVersionForm(versionFormTO);
                                q.setId(null);
                                questionRepository.save(q);
                            }
                          );

    }


    public FormDTO clone(String keyForm, Integer versionForm) throws ApiException {
        Valid.stringNullOrTrimEmpty(Err.F_1_KEY_REQUIRED.getMsg(), keyForm);
        Valid.objNull(Err.F_4_VERSION_REQUIRED.getMsg(), versionForm);

        Form form = formRepository.getFormByKeyAndVersion(keyForm, versionForm);
        Valid.objNull(HttpStatus.NOT_FOUND, Err.F_6_KEY_AND_VERSION_NOT_FOUND.getMsg(), form);
        Valid.check(HttpStatus.NOT_FOUND, Err.F_6_KEY_AND_VERSION_NOT_FOUND.getMsg(), form.getStatus() == StatusForm.REMOVED.getId() );

        form.setKeyForm(UtilString.shortUUID());
        form.setDateInsert(UtilDate.getNow());
        form.setDateLastUpdate(UtilDate.getNow());
        form.setVersionPrevious(form.getVersionForm());
        form.setVersionForm(0);
        form.setStatus(StatusForm.ON_DRAFT.getId());
        form.setId(null); // generate new one

        Form formInserted = formRepository.save(form);

        copyQuestionToOtherForm(keyForm, versionForm, formInserted.getKeyForm(), formInserted.getVersionForm());
 
        return  FormDTO.buildIntoFormDTO(formInserted);

    }


    public void publish(String keyForm, Integer versionForm) throws ApiException {
        Valid.stringNullOrTrimEmpty(Err.F_1_KEY_REQUIRED.getMsg(), keyForm);
        Valid.objNull(Err.F_4_VERSION_REQUIRED.getMsg(), versionForm);

        Form form = formRepository.getFormByKeyAndVersion(keyForm, versionForm);
        Valid.objNull(HttpStatus.NOT_FOUND, Err.F_6_KEY_AND_VERSION_NOT_FOUND.getMsg(), form);
        Valid.check(HttpStatus.NOT_FOUND, Err.F_6_KEY_AND_VERSION_NOT_FOUND.getMsg(), form.getStatus() == StatusForm.REMOVED.getId() );

        Valid.check(Err.F_13_FORM_MUST_BE_ON_DRAFT.getMsg(), form.getStatus() != StatusForm.ON_DRAFT.getId() );

        Valid.check(Err.F_14_FORM_MUST_HAVE_QUESTION_TO_PUBULISH.getMsg(), questionRepository.countByKeyAndVersionForm(keyForm, versionForm) <= 0 );

        formRepository.listFormByKeyAndStatus(keyForm, StatusForm.PUBLISHED.getId())
                      .forEach(f -> 
                            formRepository.changeStatusByKeyAndVersion(
                                                StatusForm.UNPUBLISHED.getId(),  
                                                UtilDate.getNow(), 
                                                f.getKeyForm(), 
                                                f.getVersionForm())
                        );

        formRepository.changeStatusByKeyAndVersion(StatusForm.PUBLISHED.getId(), UtilDate.getNow(), keyForm, versionForm);

        sendQueueService.sendPublishedFormToQueue(FormDTO.buildIntoFormDTO(form));
    }

    public void unpublish(String keyForm, Integer versionForm) throws ApiException {
        Valid.stringNullOrTrimEmpty(Err.F_1_KEY_REQUIRED.getMsg(), keyForm);
        Valid.objNull(Err.F_4_VERSION_REQUIRED.getMsg(), versionForm);

        Form form = formRepository.getFormByKeyAndVersion(keyForm, versionForm);
        Valid.objNull(HttpStatus.NOT_FOUND, Err.F_6_KEY_AND_VERSION_NOT_FOUND.getMsg(), form);
        Valid.check(HttpStatus.NOT_FOUND, Err.F_6_KEY_AND_VERSION_NOT_FOUND.getMsg(), form.getStatus() == StatusForm.REMOVED.getId() );
        
        Valid.check(Err.F_11_FORM_MUST_BE_PUBLISHED.getMsg(), form.getStatus() != StatusForm.PUBLISHED.getId() );

        formRepository.changeStatusByKeyAndVersion(StatusForm.UNPUBLISHED.getId(), UtilDate.getNow(), keyForm, versionForm);

        sendQueueService.sendUnPublishedFormToQueue(FormDTO.buildIntoFormDTO(form));

    }

    public void close(String keyForm, Integer versionForm) throws ApiException {
        Valid.stringNullOrTrimEmpty(Err.F_1_KEY_REQUIRED.getMsg(), keyForm);
        Valid.objNull(Err.F_4_VERSION_REQUIRED.getMsg(), versionForm);

        Form form = formRepository.getFormByKeyAndVersion(keyForm, versionForm);
        Valid.objNull(HttpStatus.NOT_FOUND, Err.F_6_KEY_AND_VERSION_NOT_FOUND.getMsg(), form);
        Valid.check(HttpStatus.NOT_FOUND, Err.F_6_KEY_AND_VERSION_NOT_FOUND.getMsg(), form.getStatus() == StatusForm.REMOVED.getId() );

        Valid.check(Err.F_11_FORM_MUST_BE_PUBLISHED.getMsg(), form.getStatus() != StatusForm.PUBLISHED.getId() );

        formRepository.changeStatusByKeyAndVersion(StatusForm.CLOSED.getId(), UtilDate.getNow(), keyForm, versionForm);

    }



    public List<FormDTO> list(FormDTOFilter formDTOFilter) throws ApiException {

        SelectCustom selectCustom = new SelectCustom();
        selectCustom
        .col("form.key_form",
             "form.title",
             "form.comment",
             "form.date_insert",
             "form.date_last_update",
             "form.version_form",
             "form.version_previous",
             "form.status")
        .from("form")
        .andWhere("form.status <> ? ", StatusForm.REMOVED.getId() );

        if( !UtilString.isEmptyTrim(formDTOFilter.getBy_key() )  ){
            selectCustom.andWhere("form.key_form = ? ", formDTOFilter.getBy_key() );
        }

        if( !UtilString.isEmptyTrim(formDTOFilter.getContains_title() )  ){
            selectCustom.andWhere("form.title like ? ", "%" + formDTOFilter.getContains_title() + "%");
        }

        if( !UtilString.isEmptyTrim(formDTOFilter.getOnly_removed())  ){
            selectCustom.andWhere("form.status = ? ", StatusForm.REMOVED);
        }else if(  !UtilString.isEmptyTrim(formDTOFilter.getBy_status() )   ){

            Valid.check( Err.F_7_STATUS_INVALID.getMsg(), !StatusForm.containId(UtilNum.parseToInt(formDTOFilter.getBy_status())));
            selectCustom.andWhere("form.status = ? ", UtilNum.parseToInt(formDTOFilter.getBy_status()));
        }


        if( !UtilString.isEmptyTrim(formDTOFilter.getContains_comment() )  ){
            selectCustom.andWhere("form.comment like ? ", "%" + formDTOFilter.getContains_comment() + "%");
        }
        selectCustom.setPaginationMysql(formDTOFilter.getPage_size(), formDTOFilter.getNum_page() );
        selectCustom.orderBy("date_insert", SelectCustom.DESC);

        return jdbcTemplate
                    .queryForList(selectCustom.build(), selectCustom.getListValues().toArray())
                    .stream()
                    .map(
                        (x) -> new FormDTO( 
                                    (String) x.get("key_form") , 
                                    (Integer) x.get("version_form"), 
                                    (String)  x.get("title"),
                                    (String)  x.get("comment"), 
                                    ((Timestamp) x.get("date_insert")).toLocalDateTime(),
                                    (Integer)  x.get("version_previous"),
                                    (Integer)  x.get("status"),
                                    ((Timestamp) x.get("date_last_update")).toLocalDateTime() )
                    ).collect(Collectors.toList());
    }


    public FormDTO getByKeyAndVersion(String keyForm, Integer versionForm) throws ApiException {
        Valid.stringNullOrTrimEmpty(Err.F_1_KEY_REQUIRED.getMsg(), keyForm);
        Valid.objNull(Err.F_4_VERSION_REQUIRED.getMsg(), versionForm);
        
        Form form = formRepository.getFormByKeyAndVersion(keyForm, versionForm);
        Valid.objNull(HttpStatus.NOT_FOUND, Err.F_6_KEY_AND_VERSION_NOT_FOUND .getMsg(), form);
        Valid.check(HttpStatus.NOT_FOUND, Err.F_6_KEY_AND_VERSION_NOT_FOUND.getMsg(), form.getStatus() == StatusForm.REMOVED.getId() );

        return  FormDTO.buildIntoFormDTO(form);
    }

    public void update(String keyForm, Integer versionForm, FormDTO formDTO) {

        Valid.stringNullOrTrimEmpty(Err.F_1_KEY_REQUIRED.getMsg(), keyForm);
        Valid.objNull(Err.F_4_VERSION_REQUIRED.getMsg(), versionForm);
        Valid.objNull( Err.F_8_TITLE_REQUIRED.getMsg(), formDTO);
        Valid.stringNullOrTrimEmpty( Err.F_8_TITLE_REQUIRED.getMsg() , formDTO.getTitle() );
        Valid.check(   Err.F_2_KEY_MUST_BE_LESS_255_CARACT.getMsg() , formDTO.getTitle().length() >= 255 );
        Valid.check(   Err.F_3_COMMENT_MUST_BE_LESS_255_CARACT.getMsg() , formDTO.getComment() != null && formDTO.getComment().length() >= 255 );

        
        Form formToUpdate = formRepository.getFormByKeyAndVersion(keyForm, versionForm);
        Valid.objNull(HttpStatus.NOT_FOUND, Err.F_6_KEY_AND_VERSION_NOT_FOUND .getMsg(), formToUpdate);
        Valid.check(HttpStatus.NOT_FOUND, Err.F_6_KEY_AND_VERSION_NOT_FOUND.getMsg(), formToUpdate.getStatus() == StatusForm.REMOVED.getId() );


        formToUpdate.setComment(formDTO.getComment());
        formToUpdate.setTitle(formDTO.getTitle());
        formToUpdate.setDateLastUpdate(UtilDate.getNow());

        formRepository.save(formToUpdate);

    }

    public void disable(String keyForm, Integer versionForm) throws ApiException {
        Valid.stringNullOrTrimEmpty(Err.F_1_KEY_REQUIRED.getMsg(), keyForm);
        Valid.objNull(Err.F_4_VERSION_REQUIRED.getMsg(), versionForm);

        Form form = formRepository.getFormByKeyAndVersion(keyForm, versionForm);
        Valid.objNull(HttpStatus.NOT_FOUND, Err.F_6_KEY_AND_VERSION_NOT_FOUND.getMsg(), form);
        Valid.check(HttpStatus.NOT_FOUND, Err.F_6_KEY_AND_VERSION_NOT_FOUND.getMsg(), form.getStatus() == StatusForm.REMOVED.getId() );
        Valid.check(Err.F_16_FORM_PUBLISHED_CANNOT_BE_DISABLE.getMsg(), form.getStatus() == StatusForm.PUBLISHED.getId() );

        formRepository.changeStatusByKeyAndVersion(StatusForm.DISABLED.getId(), UtilDate.getNow(), keyForm, versionForm);
    }

    public void disableAllVersion(String keyForm) throws ApiException {
        Valid.stringNullOrTrimEmpty(Err.F_1_KEY_REQUIRED.getMsg(), keyForm);

        List<Form> formList = formRepository.listFormByKey(keyForm);
        Valid.listNullOrEmpty(HttpStatus.NOT_FOUND, Err.F_5_KEY_NOT_FOUND.getMsg(), formList);

        if( formList.stream().anyMatch(f -> f.getStatus() == StatusForm.PUBLISHED.getId()) )
            throw new ApiException(HttpStatus.BAD_REQUEST, Err.F_16_FORM_PUBLISHED_CANNOT_BE_DISABLE.getMsg()); 
                            
        formList
            .stream()
            .filter(f -> f.getStatus() != StatusForm.REMOVED.getId() )
            .forEach(
                f -> formRepository.changeStatusByKeyAndVersion(StatusForm.DISABLED.getId(), UtilDate.getNow(), f.getKeyForm(),  f.getVersionForm())
            );
    }


    public void remove(String keyForm, Integer versionForm) throws ApiException {
        Valid.stringNullOrTrimEmpty(Err.F_1_KEY_REQUIRED.getMsg(), keyForm);
        Valid.objNull(Err.F_4_VERSION_REQUIRED.getMsg(), versionForm);

        Form form = formRepository.getFormByKeyAndVersion(keyForm, versionForm);
        Valid.objNull(HttpStatus.NOT_FOUND, Err.F_6_KEY_AND_VERSION_NOT_FOUND.getMsg(), form);
        Valid.check(Err.F_12_FORM_MUST_BE_DISABLED.getMsg(), form.getStatus() != StatusForm.DISABLED.getId() );

        formRepository.changeStatusByKeyAndVersion(StatusForm.REMOVED.getId(), UtilDate.getNow(), keyForm, versionForm);
    }

    public void removeAllVersion(String keyForm ) throws ApiException {
        Valid.stringNullOrTrimEmpty(Err.F_1_KEY_REQUIRED.getMsg(), keyForm);
     
        List<Form> formList = formRepository.listFormByKey(keyForm);
        Valid.listNullOrEmpty(HttpStatus.NOT_FOUND, Err.F_5_KEY_NOT_FOUND.getMsg(), formList);

        formList.stream()
                .filter(f -> f.getStatus() != StatusForm.DISABLED.getId())
                .findFirst().ifPresent(
                            f -> { throw new ApiException(HttpStatus.BAD_REQUEST, String.format(Err.F_15_FORM_NOT_DISABLED_TO_REMOVE.getMsg(),f.getTitle())); }
                            );
  
        formRepository.changeStatusByKey(StatusForm.REMOVED.getId(), UtilDate.getNow(), keyForm);
    }

    
}
