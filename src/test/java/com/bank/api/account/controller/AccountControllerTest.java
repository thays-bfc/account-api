package com.bank.api.account.controller;

import com.bank.api.account.service.AccountService;
import com.bank.api.account.util.message.SystemConstants;
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
@WebMvcTest(controllers = {AccountController.class})
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Test
    void shouldReturnBadRequestWhenInvalidCPF() throws Exception {
        URI uri = new URI("/account");
        String json = "{\n" +
                "    \"name\": \"José Araujo\",\n" +
                "    \"cpf\": \"656.520.450-005\"\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

}
