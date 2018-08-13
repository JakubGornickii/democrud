package pl.akademiakodu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import pl.akademiakodu.model.User;
import pl.akademiakodu.other.UserData;
import pl.akademiakodu.repository.UserRepository;
import pl.akademiakodu.service.UserService;

import java.util.List;

@RestController
public class AdminUsersMenuController {
@Autowired
    UserService userService;

    @Autowired
    UserData userData;

    @Qualifier("userRepository")
    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "admin/users", method = RequestMethod.GET)
    public ModelAndView usersMenu() {
        ModelAndView modelAndView = userData.getUserData();
        modelAndView.setViewName("admin/users");
        List<User> userList = userRepository.findAll();
        modelAndView.addObject("users", userList);
        return modelAndView;
    }

    @RequestMapping(value = "admin/users/delete", method = RequestMethod.DELETE)
    public ModelAndView deleteUser(@RequestParam("id") Integer id) {
        if (id.equals(userData.getUserId()))
            return new ModelAndView("/accessDenied");
        else userService.deleteUser(id);
        return new ModelAndView("redirect:/admin/users");
    }
}
