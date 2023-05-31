package bonqa.user;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String email;
    private String username;
    private LocalDateTime createdTime;
    private Role role;
}
