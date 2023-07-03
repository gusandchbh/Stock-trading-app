package bonqa.user;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    public boolean isAuthenticatedUser(Long id) {
        Object principal =
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User user) {
            Long userId = user.getId();
            return userId.equals(id);
        }
        return false;
    }
}
