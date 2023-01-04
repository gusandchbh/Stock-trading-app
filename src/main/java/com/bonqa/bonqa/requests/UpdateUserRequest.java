package com.bonqa.bonqa.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class UpdateUserRequest {
    @NotNull
    @Size(min = 5)
    private String password;
}
