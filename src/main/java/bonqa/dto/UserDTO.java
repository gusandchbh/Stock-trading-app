package bonqa.dto;

import bonqa.domain.model.Role;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
  private Long id;
  private String email;
  private String username;
  private LocalDateTime createdTime;
  private Role role;

}
