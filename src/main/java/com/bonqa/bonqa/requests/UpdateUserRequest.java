package com.bonqa.bonqa.requests;

import com.bonqa.bonqa.model.Role;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UpdateUserRequest {
    @NotNull
    @Size(min = 5)
    private String password;
}
