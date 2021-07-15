package com.formcloud.formcreate.service;

import java.util.List;
import java.util.stream.Collectors;

import com.formcloud.formcreate.constant.Err;
import com.formcloud.formcreate.domain.dto.QuestionDTO;
import com.formcloud.formcreate.domain.entity.Form;
import com.formcloud.formcreate.domain.entity.Question;
import com.formcloud.formcreate.domain.enums.StatusForm;
import com.formcloud.formcreate.domain.enums.TypeBoolean;
import com.formcloud.formcreate.domain.enums.TypeQuestion;
import com.formcloud.formcreate.domain.enums.WidthQuestion;
import com.formcloud.formcreate.repository.FormRepository;
import com.formcloud.formcreate.repository.QuestionRepository;
import com.formcloud.archcommons.errorhandler.ApiException;
import com.formcloud.archcommons.util.UtilDate;
import com.formcloud.archcommons.valid.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {

    @Autowired
    private FormRepository formRepository;

    @Autowired
    private QuestionRepository questionRepository;


    public void update(List<QuestionDTO> questionDTOList, String keyForm, Integer versionForm) throws ApiException {

        Valid.listNullOrEmpty( Err.Q_9_TYPE_NOT_VALID.getMsg(), questionDTOList);
        Valid.stringNullOrTrimEmpty( Err.Q_1_KEY_FORM_REQUIRED.getMsg(), keyForm);
        Valid.objNull( Err.Q_10_VERSION_FORM_REQUIRED.getMsg(), versionForm);

        Form form = formRepository.getFormByKeyAndVersion(keyForm, versionForm);
        Valid.objNull( Err.Q_11_KEYF_AND_VERSIONF_NOT_FOUND.getMsg(), form);

        Valid.check(Err.F_13_FORM_MUST_BE_ON_DRAFT.getMsg(), form.getStatus() != StatusForm.ON_DRAFT.getId() );
  

    
        questionDTOList.forEach(this::validateQuestion);

        List<Question> questionList = questionDTOList
                                            .stream()
                                            .map(q -> prepareQuestion(q, keyForm, versionForm))
                                            .collect(Collectors.toList());

        questionRepository.deleteByKeyForm(keyForm);

        questionList.forEach(questionRepository::save);

        formRepository.changeStatusByKeyAndVersion(StatusForm.ON_DRAFT.getId(), UtilDate.getNow(), keyForm, versionForm);
    }

    public List<QuestionDTO> listByKeyAndVersionForm(String keyForm, Integer versionForm) throws ApiException{

        Valid.stringNullOrTrimEmpty( Err.Q_1_KEY_FORM_REQUIRED.getMsg(), keyForm);
        Valid.objNull( Err.Q_10_VERSION_FORM_REQUIRED.getMsg(), versionForm);

        Form form = formRepository.getFormByKeyAndVersion(keyForm, versionForm);
        Valid.objNull( Err.Q_11_KEYF_AND_VERSIONF_NOT_FOUND.getMsg(), form);

        return questionRepository.listQuestionByKeyAndVersionForm(keyForm, versionForm)
                          .stream()
                          .map(QuestionDTO::buildIntoQuestionDTO)
                          .collect(Collectors.toList());
    }


    private void validateQuestion(QuestionDTO question) throws ApiException {

        Valid.objNull(Err.Q_1_KEY_FORM_REQUIRED.getMsg(), question);
        Valid.stringNullOrTrimEmpty(Err.Q_2_TITLE_REQUIRED.getMsg(), question.getTitle() );
        Valid.objNull(Err.Q_3_TYPE_REQUIRED.getMsg(), question.getType() );
        Valid.objNull(Err.Q_4_POSITION_REQUIRED.getMsg(), question.getPosition());
        Valid.objNull(Err.Q_8_TYPE_NOT_VALID.getMsg(), question.getType() != null && !TypeQuestion.containId(question.getType()) );
        Valid.check(Err.Q_5_COMMENT_MUST_BE_LESS_THAN_255.getMsg(), question.getComment() != null && question.getComment().length() >= 225 );
        Valid.check(Err.Q_6_WIDTH_NOT_VALID.getMsg(), question.getWidth() != null && ! WidthQuestion.containId(question.getWidth()) );
        Valid.check(Err.Q_7_REQUIRED_NOT_VALID.getMsg(), question.getRequired() != null && ! TypeBoolean.containId(question.getRequired()) );
    }

    private Question prepareQuestion(QuestionDTO q, String keyForm, Integer versionForm) throws ApiException {
        q.setKeyForm(keyForm);
        q.setVersionForm(versionForm);
        q.setDateInsert( UtilDate.getNow() );
        q.setWidth( q.getWidth() == null ? WidthQuestion.W100.getId() : q.getWidth()  );
        q.setType( q.getType() == null ? TypeQuestion.TEXT_255.getId() : q.getType()  );
        q.setRequired( q.getRequired() == null ? TypeBoolean.FALSE.getId() : q.getRequired()  );

        if( q.getType().equals(TypeQuestion.TITLE.getId()) ){
            q.setWidth(WidthQuestion.W100.getId());
            q.setRequired(TypeBoolean.FALSE.getId());
        }
        return q.buildIntoQuestion();
    }

    
    
}
