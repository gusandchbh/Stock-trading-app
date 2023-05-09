package bonqa.domain.model.data.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateNamesRequest {
  @Pattern(regexp = "^[a-zA-Z]+$", message = "Name should contain only letters")
  @Size(min = 2, max = 15, message = "Name should be between 2 and 15 characters")
  private String firstName;

  @Pattern(regexp = "^[a-zA-Z]+$", message = "Name should contain only letters")
  @Size(min = 2, max = 15, message = "Name should be between 2 and 15 characters")
  private String lastName;

}
