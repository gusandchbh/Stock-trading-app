package bonqa.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateEmailRequest {

  @NotBlank(message = "Email can not be empty")
  @Email(message = "Email needs to be valid")
  @Size(min = 8, max = 40, message = "Email should be between 8 and 40 characters")
  private String email;
}
