package com.bank.api.account.controller;

import com.bank.api.account.dto.AccountTransactionResponse;
import com.bank.api.account.dto.DepositForm;
import com.bank.api.account.dto.TransferForm;
import com.bank.api.account.service.AccountTransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "Bank - Account's transaction operations")
@RestController
@RequestMapping("/transaction")
public class AccountTransactionController {

    @Autowired
    private AccountTransactionService accountTransactionService;

    @ApiOperation(value = "Deposit money into account", response = AccountTransactionResponse.class)
    @PostMapping("/deposit")
    public ResponseEntity<AccountTransactionResponse> deposit(@RequestBody @Valid DepositForm depositForm){
        AccountTransactionResponse response = accountTransactionService.deposit(depositForm);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Transfer money from an account to another", response = AccountTransactionResponse.class)
    @PostMapping("/transfer")
    public ResponseEntity<AccountTransactionResponse> transfer(@RequestBody @Valid TransferForm transferForm){
        AccountTransactionResponse response = accountTransactionService.transfer(transferForm);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Retrieve all account's transactions", response = AccountTransactionResponse.class)
    @GetMapping("/account/{code}")
    public ResponseEntity<AccountTransactionResponse> listAccountTransactions(@PathVariable Long code){
        AccountTransactionResponse response = accountTransactionService.findByAccountCode(code);
        return ResponseEntity.ok(response);
    }
}
