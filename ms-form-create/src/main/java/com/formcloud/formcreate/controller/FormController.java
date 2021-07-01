package com.formcloud.formcreate.controller;

import java.util.List;

import com.formcloud.formcreate.domain.dto.FormDTO;
import com.formcloud.formcreate.domain.dto.FormDTOFilter;
import com.formcloud.formcreate.service.FormService;
import com.formcloud.springutil.errorhandler.ApiException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/forms")
public class FormController {

  @Autowired
  FormService formService;

  @PostMapping
  public ResponseEntity<?> createNewOne(@RequestBody(required = false) FormDTO formDTO, UriComponentsBuilder uriComponentsBuilder) throws ApiException {
    formDTO = formService.createNewOne(formDTO);
    return ResponseEntity.created(
                  uriComponentsBuilder.path("/form/{key}/version/{version}").buildAndExpand(formDTO.getKey(), formDTO.getVersion()).toUri()
                ).build();
  }

  @PostMapping("/{key}/version/{version}")
  public ResponseEntity<?> cloneToNewVersion(@PathVariable("key") String key, @PathVariable("version") Integer version, UriComponentsBuilder uriComponentsBuilder) throws ApiException {
    
    FormDTO  formDTO = formService.cloneToNewVersion(key, version);
    return ResponseEntity.created(
                  uriComponentsBuilder.path("/form/{key}/version/{version}").buildAndExpand(formDTO.getKey(), formDTO.getVersion()).toUri()
                ).build();

  }

  @PostMapping("/{key}/version/{version}/submit")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<?> submit(@PathVariable("key") String key, @PathVariable("version") Integer version) throws ApiException {
    formService.submit(key, version);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/disable")
  @ResponseStatus(HttpStatus.PARTIAL_CONTENT)
  public ResponseEntity<?> disable(@RequestBody(required = false) List<FormDTO> formDTOList) throws ApiException {
    formService.disable(formDTOList);
    return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).build();
  }

  @PostMapping("/remove")
  @ResponseStatus(HttpStatus.PARTIAL_CONTENT)
  public ResponseEntity<?> remove(@RequestBody(required = false) List<FormDTO> formDTOList) throws ApiException {
    formService.remove(formDTOList);
    return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).build();
  }

  @PutMapping("/{key}/version/{version}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<?> update(@PathVariable("key") String key, @PathVariable("version") Integer version, @RequestBody(required = false) FormDTO formDTO, UriComponentsBuilder uriComponentsBuilder) throws ApiException {
    formService.update(key, version, formDTO );
    return ResponseEntity.noContent().location(uriComponentsBuilder.path("/form/{key}/version/{version}").buildAndExpand(key,version).toUri()).build();
  }


	@GetMapping
	public ResponseEntity<List<FormDTO>> list(FormDTOFilter formDTOFilter) throws ApiException {
		return ResponseEntity.ok( formService.list(formDTOFilter) );
	}
    
  @GetMapping("/{key}/last-version")
  public ResponseEntity<FormDTO> getLastVersionByKey(@PathVariable("key") String key) throws ApiException {
    return ResponseEntity.ok(formService.getLastVersionByKey(key));
  }

  @GetMapping("/{key}/version/{version}")
  public ResponseEntity<FormDTO> getById(@PathVariable("key") String key, @PathVariable("version") Integer version) throws ApiException {
    return ResponseEntity.ok(formService.getByKeyAndVersion(key, version));
  }

}
