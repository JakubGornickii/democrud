package pl.akademiakodu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.akademiakodu.model.User;
import pl.akademiakodu.other.UserData;
import pl.akademiakodu.service.UserService;

import javax.validation.Valid;

@RestController
public class UserDetailsController {

    @Autowired
    UserData userData;
    @Autowired
    UserService userService;

    @RequestMapping(value = "/user/details", method = RequestMethod.GET)
    public ModelAndView userDetails()
    {
        ModelAndView modelAndView = userData.getUserData();
        User user = userService.getUserById(userData.getUserId());
        modelAndView.addObject("user", user);
        modelAndView.setViewName("user/details");
        return modelAndView;
    }
    @RequestMapping(value = "/user/details/update", method = RequestMethod.PUT)
    public ModelAndView updateUser (@RequestParam("id") Integer id, @ModelAttribute @Valid User user, BindingResult bindingResult)
    {
        ModelAndView modelAndView = userData.getUserData();

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("user/details");
        } else {
            userService.updateUser(user,id);
            modelAndView.setViewName("/login");
        }
        return modelAndView;
    }

}
