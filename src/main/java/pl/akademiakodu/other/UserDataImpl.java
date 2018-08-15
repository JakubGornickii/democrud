package pl.akademiakodu.other;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import pl.akademiakodu.model.Role;
import pl.akademiakodu.model.User;
import pl.akademiakodu.service.UserService;

import java.util.Set;

@Component
public class UserDataImpl implements UserData {


    @Autowired
    private UserService userService;


    @Override
    public ModelAndView getUserData() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        String rString = getRoleString(user);
        modelAndView.addObject("myId", user.getId());
        modelAndView.addObject("userName", user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("adminMessage", "Poziom uprawnień - " + rString);
        modelAndView.addObject("myRole", rString);
        return modelAndView;
    }

    @Override
    public Integer getUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        Integer id = user.getId();
        return id;
    }

    @Override
    public String getUserRole() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        Set<Role> roles = user.getRoles();
        String srole = "";
        for (Role role : roles) {
            if (role.getRole().equals("ADMIN")) {
                srole = "ADMIN";
                break;
            } else if (role.getRole().equals("USER"))
                srole = "USER";
        }
        return srole;
    }

    private String getRoleString(User user) {
        String rString = "";
        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            if (role.getRole().equals("ADMIN")) {
                rString = "Administrator";
                break;
            } else if (role.getRole().equals("USER"))
                rString = "Urzytkownik";
        }
        return rString;
    }
}
