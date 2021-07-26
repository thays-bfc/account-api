package com.bank.api.account.controller;

import com.bank.api.account.service.AccountTransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = {AccountTransactionController.class})
public class AccountTransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountTransactionService accountTransactionService;

    @Test
    void shouldReturnBadRequestWhenDepositNegativeValue() throws Exception {
        URI uri = new URI("/transaction/deposit");
        String json = "{\n" +
                "    \"accountCode\": 1,\n" +
                "    \"depositValue\": -1\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

}
