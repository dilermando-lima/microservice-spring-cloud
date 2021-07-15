package com.formcloud.formcreate.controller;

import java.util.List;

import com.formcloud.formcreate.constant.Endpoint;
import com.formcloud.formcreate.domain.dto.FormDTO;
import com.formcloud.formcreate.domain.dto.FormDTOFilter;
import com.formcloud.formcreate.service.FormService;
import com.formcloud.archcommons.errorhandler.ApiException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/forms")
public class FormController {

  @Autowired
  FormService formService;

  @Operation(summary = Endpoint.FORM_CREATE_TITLE, tags = { Endpoint.TAG_COMMMAND_OPER })
  @PostMapping
  public ResponseEntity<?> create(@RequestBody(required = false) FormDTO formDTO, UriComponentsBuilder uriComponentsBuilder) throws ApiException {
    formDTO = formService.create(formDTO);
    return ResponseEntity.created(
                  uriComponentsBuilder.path("/forms/{keyForm}/version/{versionForm}").buildAndExpand(formDTO.getKeyForm(), formDTO.getVersionForm()).toUri()
                ).build();
  }

  @Operation(summary = Endpoint.FORM_CLONE_TITLE, tags = { Endpoint.TAG_COMMMAND_OPER })
  @PostMapping("/{keyForm}/version/{versionForm}/clone")
  public ResponseEntity<?> clone(@PathVariable("keyForm") String keyForm, @PathVariable("versionForm") Integer versionForm, UriComponentsBuilder uriComponentsBuilder) throws ApiException {
    FormDTO  formDTO = formService.clone(keyForm, versionForm);
    return ResponseEntity.created(
                  uriComponentsBuilder.path("/forms/{keyForm}/version/{versionForm}").buildAndExpand(formDTO.getKeyForm(), formDTO.getVersionForm()).toUri()
                ).build();
  }

  @Operation(summary = Endpoint.FORM_DRAFT_TITLE, tags = { Endpoint.TAG_COMMMAND_OPER })
  @PostMapping("/{keyForm}/version/{versionForm}/draft")
  public ResponseEntity<?> draft(@PathVariable("keyForm") String keyForm, @PathVariable("versionForm") Integer versionForm, UriComponentsBuilder uriComponentsBuilder) throws ApiException {
    FormDTO  formDTO = formService.draft(keyForm, versionForm);
    return ResponseEntity.created(
                  uriComponentsBuilder.path("/forms/{keyForm}/version/{versionForm}").buildAndExpand(formDTO.getKeyForm(), formDTO.getVersionForm()).toUri()
                ).build();
  }

  @Operation(summary = Endpoint.FORM_PUBLISH_TITLE, tags = { Endpoint.TAG_ALTER_STATUS_FORM })
  @PostMapping("/{keyForm}/version/{versionForm}/publish")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<?> publish(@PathVariable("keyForm") String keyForm, @PathVariable("versionForm") Integer versionForm) throws ApiException {
    formService.publish(keyForm, versionForm);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = Endpoint.FORM_UNPUBLISH_TITLE, tags = { Endpoint.TAG_ALTER_STATUS_FORM })
  @PostMapping("/{keyForm}/version/{versionForm}/unpublish")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<?> unpublish(@PathVariable("keyForm") String keyForm, @PathVariable("versionForm") Integer versionForm) throws ApiException {
    formService.unpublish(keyForm, versionForm);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = Endpoint.FORM_DISABLE_VERSION_TITLE, tags = { Endpoint.TAG_ALTER_STATUS_FORM })
  @PostMapping("/{keyForm}/version/{versionForm}/disable")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<?> disable(@PathVariable("keyForm") String keyForm, @PathVariable("versionForm") Integer versionForm) throws ApiException {
    formService.disable(keyForm, versionForm);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = Endpoint.FORM_DISABLE_ALL_VERSION_TITLE, tags = { Endpoint.TAG_ALTER_STATUS_FORM })
  @PostMapping("/{keyForm}/disable")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<?> disableAllVersion(@PathVariable("keyForm") String keyForm) throws ApiException {
    formService.disableAllVersion(keyForm);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = Endpoint.FORM_REMOVE_VERSION_TITLE, tags = { Endpoint.TAG_ALTER_STATUS_FORM })
  @DeleteMapping("/{keyForm}/version/{versionForm}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<?> remove(@PathVariable("keyForm") String keyForm, @PathVariable("versionForm") Integer versionForm) throws ApiException {
    formService.remove(keyForm, versionForm);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = Endpoint.FORM_REMOVE_ALL_VERSION_TITLE, tags = { Endpoint.TAG_ALTER_STATUS_FORM })
  @DeleteMapping("/{keyForm}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<?> removeAllVersion(@PathVariable("keyForm") String keyForm) throws ApiException {
    formService.removeAllVersion(keyForm);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = Endpoint.FORM_UPDATE_TITLE, tags = { Endpoint.TAG_COMMMAND_OPER })
  @PutMapping("/{keyForm}/version/{versionForm}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<?> update(@PathVariable("keyForm") String keyForm, @PathVariable("versionForm") Integer versionForm, @RequestBody(required = false) FormDTO formDTO) throws ApiException {
    formService.update(keyForm, versionForm, formDTO );
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = Endpoint.FORM_LIST_TITLE, tags = { Endpoint.TAG_QUERY_OPER })
	@GetMapping
	public ResponseEntity<List<FormDTO>> list(FormDTOFilter formDTOFilter) throws ApiException {
		return ResponseEntity.ok( formService.list(formDTOFilter) );
	}

  @Operation(summary = Endpoint.FORM_GET_BY_KEY_AND_VERSION_TITLE, tags = { Endpoint.TAG_QUERY_OPER })
  @GetMapping("/{keyForm}/version/{versionForm}")
  public ResponseEntity<FormDTO> getByKeyAndVersion(@PathVariable("keyForm") String keyForm, @PathVariable("versionForm") Integer versionForm) throws ApiException {
    return ResponseEntity.ok(formService.getByKeyAndVersion(keyForm, versionForm));
  }

}
