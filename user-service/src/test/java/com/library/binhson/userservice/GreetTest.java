package com.library.binhson.userservice;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithMockAuthentication;
import com.c4_soft.springaddons.security.oauth2.test.webmvc.AutoConfigureAddonsWebmvcResourceServerSecurity;
import com.library.binhson.userservice.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@WebMvcTest(GreetingController.class)
@AutoConfigureAddonsWebmvcResourceServerSecurity
@Import(SecurityConfig.class)
public class GreetTest {
    @Autowired
    MockMvc mockMvc;
    @Test
    @WithMockAuthentication(name = "ch4mp", authorities = {"user"})
    void givenUserHasNiceAuthority_whenGetGreeting_thenOk() throws Exception {
        mockMvc.perform(get("/greeting")).andExpect(status().isOk());
    }
}
