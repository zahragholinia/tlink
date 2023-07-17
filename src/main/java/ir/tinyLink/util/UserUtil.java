package ir.tinyLink.util;

import ir.tinyLink.model.entity.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserUtil {

    public static UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       return  (UserEntity) authentication.getPrincipal();

    }
}
