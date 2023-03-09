package com.bonqa.bonqa.domain.model.data.request;

import com.bonqa.bonqa.domain.security.StrongPassword;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePasswordRequest {

  @StrongPassword
  @NotBlank(message = "Password is required")
  private String password;

}
