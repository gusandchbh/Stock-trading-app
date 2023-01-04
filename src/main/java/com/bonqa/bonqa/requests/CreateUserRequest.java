package com.bonqa.bonqa.requests;

import com.bonqa.bonqa.model.Role;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {
    @NotNull
    @Size(min = 5)
    private String username;
    @NotNull
    @Size(min = 5)
    private String password;
    @NotNull
    private Role role;

}
