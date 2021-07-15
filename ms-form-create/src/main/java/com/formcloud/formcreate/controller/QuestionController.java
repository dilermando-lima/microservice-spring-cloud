package com.formcloud.formcreate.controller;

import java.util.List;

import com.formcloud.formcreate.constant.Endpoint;
import com.formcloud.formcreate.domain.dto.QuestionDTO;
import com.formcloud.formcreate.service.QuestionService;
import com.formcloud.archcommons.errorhandler.ApiException;

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

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/forms/{keyForm}/version/{versionForm}/questions")
public class QuestionController {

  @Autowired
  private QuestionService questionService;

  @Operation(summary = Endpoint.FORM_INSERT_LIST_QUESTION, tags = { Endpoint.TAG_COMMMAND_OPER })
  @PutMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<?> update(@PathVariable("keyForm") String keyForm, @PathVariable("versionForm") Integer versionForm, @RequestBody(required = false) List<QuestionDTO> questionDTOList, UriComponentsBuilder uriComponentsBuilder) throws ApiException {
    questionService.update(questionDTOList, keyForm, versionForm);
    return ResponseEntity.noContent().location(uriComponentsBuilder.path("/forms/{keyForm}/version/{versionForm}/questions").buildAndExpand(keyForm,versionForm).toUri()).build();
  }

  @Operation(summary = Endpoint.FORM_LIST_QUESTION, tags = { Endpoint.TAG_QUERY_OPER })
  @GetMapping
  public ResponseEntity<List<QuestionDTO>> listByKeyAndVersionForm(@PathVariable("keyForm") String keyForm, @PathVariable("versionForm") Integer versionForm ) throws ApiException {
    return ResponseEntity.ok(questionService.listByKeyAndVersionForm(keyForm, versionForm));
  }

}
