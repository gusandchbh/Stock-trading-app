package com.bonqa.bonqa.integration_tests;

import bonqa.BonqaApplication;
import bonqa.authentication.request.AuthenticationRequest;
import bonqa.authentication.request.RegisterRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(classes = BonqaApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthenticationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MappingJackson2HttpMessageConverter springMvcJacksonConverter;

    ObjectMapper objectMapper;

    @BeforeEach
    void setup(){
         objectMapper = springMvcJacksonConverter.getObjectMapper();
    }

    @Test
    void testRegisterUserSuccess() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("test2", "Testar1337!", "test2@test.com");
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().string(not(emptyString())));
    }

    @Test
    void testRegisterUserFailure() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("test1", "anyPassword", "test1@test.com");
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testAuthenticateSuccess() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("test", "Testar1337!", "test@test.com");
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isCreated());

        AuthenticationRequest authenticationRequest = new AuthenticationRequest("Testar1337!", "test");
        mockMvc.perform(post("/api/v1/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authenticationRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string(not(emptyString())));
    }


    @Test
    void testAuthenticateFailure() throws Exception {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("test", "test");
        mockMvc.perform(post("/api/v1/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authenticationRequest)))
                .andExpect(status().isUnauthorized());
    }
}
