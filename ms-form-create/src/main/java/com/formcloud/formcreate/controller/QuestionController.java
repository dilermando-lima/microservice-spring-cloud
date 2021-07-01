package com.formcloud.formcreate.controller;

import java.util.List;

import com.formcloud.formcreate.domain.dto.QuestionDTO;
import com.formcloud.formcreate.service.QuestionService;
import com.formcloud.springutil.errorhandler.ApiException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/forms/{key}/version/{version}/questions")
public class QuestionController {

  @Autowired
  private QuestionService questionService;

  @PutMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<?> update(@PathVariable("key") String keyForm, @PathVariable("version") Integer versionForm, @RequestBody(required = false) List<QuestionDTO> questionDTOList, UriComponentsBuilder uriComponentsBuilder) throws ApiException {
    questionService.update(questionDTOList, keyForm, versionForm);
    return ResponseEntity.noContent().location(uriComponentsBuilder.path("/forms/{key}/version/{version}/questions").buildAndExpand(keyForm,versionForm).toUri()).build();
  }

  @GetMapping
  public ResponseEntity<List<QuestionDTO>> listByKeyAndVersionForm(@PathVariable("key") String keyForm, @PathVariable("version") Integer versionForm ) throws ApiException {
    return ResponseEntity.ok(questionService.listByKeyAndVersionForm(keyForm, versionForm));
  }


  


}
