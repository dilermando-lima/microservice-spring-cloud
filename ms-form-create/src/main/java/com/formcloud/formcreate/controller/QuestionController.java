package com.formcloud.formcreate.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/question")
public class QuestionController {

  // @Autowired
  // FormService formService;

  // @PostMapping
  // public ResponseEntity<?> create(@RequestBody(required = false) FormDTOAux formDTOAux, UriComponentsBuilder uriComponentsBuilder) throws ApiException {
  //   return ResponseEntity.created(uriComponentsBuilder.path("/form/{id}").buildAndExpand(formService.create(formDTOAux)).toUri()).build();
  // }

	// @GetMapping
	// public ResponseEntity<List<FormDTO>> list(FormDTOFilter formDTOFilter) throws ApiException {
	// 	return ResponseEntity.ok( formService.list(formDTOFilter) );
	// }
    
  // @GetMapping("/{key}")
  // public ResponseEntity<FormDTO> getById(@PathVariable("key") String key) throws ApiException {
  //   return ResponseEntity.ok(formService.getByKey(key));
  // }

}
