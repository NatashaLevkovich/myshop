package web.command.utils;

import entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import services.UserService;

public abstract class CommandUtils {

    public static User getUserByAuth(Authentication auth, UserService userService){
        if (auth != null) {
            Object principal = auth.getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                return userService.getUserByEmail(userDetails.getUsername());
            }
        }
        return new User();
    }
}
