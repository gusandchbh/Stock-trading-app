/*
package com.bonqa.bonqa.integration_tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bonqa.bonqa.domain.authentication.AuthenticationService;
import com.bonqa.bonqa.domain.model.data.request.AuthenticationRequest;
import com.bonqa.bonqa.domain.model.data.request.RegisterRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerIntegrationTest {

  private static final String AUTH_ENDPOINT = "/api/v1/auth";
  private static final String REGISTER_ENDPOINT = AUTH_ENDPOINT + "/register";
  private static final String AUTHENTICATE_ENDPOINT = AUTH_ENDPOINT + "/authenticate";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private AuthenticationService authenticationService;

  @Test
  void testRegisterAndAuthenticate() throws Exception {
    // Create a valid register request
    RegisterRequest
        registerRequest = new RegisterRequest("testuser", "Testtest123!", "testemail@test.com");

    // Send the register request
    MvcResult registerResult =
        mockMvc
            .perform(
                post(REGISTER_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(registerRequest)))
            .andExpect(status().isCreated())
            .andReturn();

    // Extract the token from the register response
    String registerResponse = registerResult.getResponse().getContentAsString();
    assertNotNull(registerResponse);
    assertTrue(registerResponse.length() > 0);

    // Create a valid authentication request
    AuthenticationRequest authenticationRequest =
        new AuthenticationRequest("Testtest123!", "testuser");

    // Send the authentication request
    MvcResult authenticateResult =
        mockMvc
            .perform(
                post(AUTHENTICATE_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(authenticationRequest)))
            .andExpect(status().isOk())
            .andReturn();

    // Extract the token from the authentication response
    String authenticateResponse = authenticateResult.getResponse().getContentAsString();
    assertNotNull(authenticateResponse);
    assertTrue(authenticateResponse.length() > 0);
  }

  @Test
  void testRegisterBadRequest() throws Exception {
    // Create an invalid register request with a blank username
    RegisterRequest registerRequest = new RegisterRequest("", "testpassword", "testemail@test.com");

    // Send the register request
    MvcResult registerResult =
        mockMvc
            .perform(
                post(REGISTER_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(registerRequest)))
            .andExpect(status().isBadRequest())
            .andReturn();

    // Verify that the response contains the expected error message
    String registerResponse = registerResult.getResponse().getContentAsString();
    assertEquals("Username cannot be blank", registerResponse);
  }

  @Test
  void testAuthenticateUnauthorized() throws Exception {
    // Create an authentication request with an invalid password
    AuthenticationRequest authenticationRequest =
        new AuthenticationRequest("testuser", "invalidpassword");

    // Send the authentication request
    MvcResult authenticateResult =
        mockMvc
            .perform(
                post(AUTHENTICATE_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(authenticationRequest)))
            .andExpect(status().isUnauthorized())
            .andReturn();

    // Verify that the response contains the expected error message
    String authenticateResponse = authenticateResult.getResponse().getContentAsString();
    assertEquals("Invalid username or password", authenticateResponse);
  }

  @Test
  void testPasswordUppercase() throws Exception {
    // Create an invalid register request with a blank email
    RegisterRequest registerRequest =
        new RegisterRequest("testuser", "testpassword1", "test@test.com");

    // Send the register request
    MvcResult registerResult =
        mockMvc
            .perform(
                post(REGISTER_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(registerRequest)))
            .andExpect(status().isBadRequest())
            .andReturn();

    // Verify that the response contains the expected error messages
    String registerResponse = registerResult.getResponse().getContentAsString();
    assertTrue(registerResponse.contains(
        "Password must contain 1 or more uppercase characters."));
  }

}
*/
