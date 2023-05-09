package bonqa.domain.model.data.request;

import bonqa.domain.security.StrongPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
  @NotBlank(message = "Username is required")
  @Size(min = 3, max = 15, message = "Username must be between 3 and 15 characters")
  private String username;

  @StrongPassword
  @NotBlank(message = "Password is required")
  private String password;

  @NotBlank(message = "Email is required")
  @Email(message = "Email is not valid")
  @Size(min = 8, max = 40, message = "Email should be between 8 and 40 characters")
  private String email;
}