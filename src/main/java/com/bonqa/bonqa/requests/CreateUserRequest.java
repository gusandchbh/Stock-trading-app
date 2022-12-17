package com.bonqa.bonqa.requests;

import com.bonqa.bonqa.model.Role;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
