package Models;

import lombok.Data;

@Data
public abstract class User {
    private final String username;
    private final String password;

}


