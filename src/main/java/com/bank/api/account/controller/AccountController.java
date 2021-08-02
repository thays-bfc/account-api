package com.bank.api.account.controller;

import com.bank.api.account.dto.CustomerForm;
import com.bank.api.account.dto.AccountResponse;
import com.bank.api.account.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Api(value = "Bank - Account operations")
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @ApiOperation(value = "Creation of a new account", response = AccountResponse.class)
    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@RequestBody @Valid CustomerForm customerForm, UriComponentsBuilder uriBuilder) {
            AccountResponse account = accountService.createAccount(customerForm);
            URI uri = uriBuilder.path("/account/{id}").buildAndExpand(account.getId()).toUri();
            return ResponseEntity.created(uri).body(account);
    }

    @ApiOperation(value = "Retrieve account by id", response = AccountResponse.class)
    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> listAllAccounts(@PathVariable Long id){
        return ResponseEntity.ok(accountService.findById(id));
    }

    @ApiOperation(value = "Retrieve all accounts", response = AccountResponse.class)
    @GetMapping
    public List<AccountResponse> listAllAccounts(){
        return accountService.findAllWithCustomer();
    }

}
