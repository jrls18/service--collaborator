package br.com.developcorporation.collaborator.rest.controller;

import br.com.developcorporation.collaborator.rest.constants.FieldConstant;
import br.com.developcorporation.collaborator.rest.message.response.CompanyResponse;
import br.com.developcorporation.collaborator.rest.message.response.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

public interface CompanyController {

    /*
    @PostMapping("/v1/save")
    ResponseEntity<MessageResponse> add(@RequestBody CompanyRequest request);

    @PutMapping("/v1/save")
    ResponseEntity<MessageResponse> update(@Validated @RequestBody CompanyRequest request);

    @GetMapping(value = "/v1/get/id={id}", produces = FieldConstant.APPLICATION_JSON)
    ResponseEntity<CompanyResponse> getById(@PathVariable("id") final String id);

    @GetMapping(value = "/v1/get/cnpj_cpf={cnpj_cpf}", produces = FieldConstant.APPLICATION_JSON)
    ResponseEntity<CompanyResponse> getByCnpj(@PathVariable("cnpj_cpf") final String cnpj);

     */
}
