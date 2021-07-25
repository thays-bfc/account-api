package com.bank.api.account.controller;

import com.bank.api.account.dto.AccountTransactionResponse;
import com.bank.api.account.dto.DepositForm;
import com.bank.api.account.dto.TransferForm;
import com.bank.api.account.service.AccountTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/transaction")
public class AccountTransactionController {

    @Autowired
    private AccountTransactionService accountTransactionService;

    @PostMapping("/deposit")
    public ResponseEntity<AccountTransactionResponse> deposit(@RequestBody @Valid DepositForm depositForm){
        AccountTransactionResponse response = accountTransactionService.deposit(depositForm);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/transfer")
    public ResponseEntity<AccountTransactionResponse> transfer(@RequestBody @Valid TransferForm transferForm){
        AccountTransactionResponse response = accountTransactionService.transfer(transferForm);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/account/{code}")
    public ResponseEntity<AccountTransactionResponse> listAccountTransactions(@PathVariable Long code){
        AccountTransactionResponse response = accountTransactionService.findByAccountCode(code);
        return ResponseEntity.ok(response);
    }
}
