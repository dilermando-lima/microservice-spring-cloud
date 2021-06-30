package com.formcloud.formcreate.controller;

import java.util.List;

import com.formcloud.formcreate.domain.Form;
import com.formcloud.formcreate.service.FormService;
import com.formcloud.springutil.errorhandler.ApiException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/form")
public class FormController {

  @Autowired
  FormService formService;

  @PostMapping
  public ResponseEntity<?> create(@RequestBody(required = false) Form formDTO, UriComponentsBuilder uriComponentsBuilder) throws ApiException {
    return ResponseEntity.created(uriComponentsBuilder.path("/form/{id}").buildAndExpand(formService.create(formDTO)).toUri()).build();
  }

	@GetMapping
	public ResponseEntity<List<Form>> list() throws ApiException {
		return ResponseEntity.ok( formService.list() );
	}
    
  @GetMapping("/{key}")
  public ResponseEntity<Form> getById(@PathVariable("key") String key) throws ApiException {
    return ResponseEntity.ok(formService.getByKey(key));
  }

}
