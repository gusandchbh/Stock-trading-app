package com.bonqa.bonqa.integration_tests;

import com.bonqa.bonqa.model.Role;
import com.bonqa.bonqa.requests.CreateUserRequest;
import com.bonqa.bonqa.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class BankUserControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;

    @Test
    void testFetchByUsername() throws Exception {
        // Given
        String username = "test_user";
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setUsername(username);
        userRequest.setPassword("password");
        userRequest.setRole(Role.USER);
        userService.createUser(userRequest);

        // When
        MockHttpServletRequestBuilder request = get("/users/search?username={username}", username);
        ResultActions result = mockMvc.perform(request);

        // Then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(username));
    }


}
