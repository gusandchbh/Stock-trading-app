package com.bonqa.bonqa.domain.model.data.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UpdateUserRequest {
  @Size(min = 5)
  private String password;
  private String email;
  private String firstName;
  private String lastName;
}
