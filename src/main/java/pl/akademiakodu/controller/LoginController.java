package pl.akademiakodu.controller;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.akademiakodu.model.EmailToken;
import pl.akademiakodu.model.Employer;
import pl.akademiakodu.model.Post;
import pl.akademiakodu.model.User;
import pl.akademiakodu.other.UserData;
import pl.akademiakodu.repository.EmployerRepository;
import pl.akademiakodu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.util.List;

@RestController
public class LoginController {

    @Autowired
    PostService postService;

    @Autowired
    UserData userData;

    @Autowired
    UserService userService;

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }


    @RequestMapping(value = "/admin/index", method = RequestMethod.GET)
    public ModelAndView adminhome() {
        List<Post> posts = postService.findByActive();
        ModelAndView modelAndView = userData.getUserData();
        modelAndView.addObject("posts", posts);

        modelAndView.setViewName("admin/home");

        return modelAndView;
    }

    @RequestMapping(value = "/user/index", method = RequestMethod.GET)
    public ModelAndView userhome() {
        List<Post> posts = postService.findByActive();
        ModelAndView modelAndView = userData.getUserData();
        modelAndView.addObject("posts", posts);
        modelAndView.setViewName("user/home");
        return modelAndView;
    }

    @RequestMapping(value = "/accessdenied", method = RequestMethod.GET)
    public ModelAndView accessdenied() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("accessdenied");
        return modelAndView;
    }
    @RequestMapping(value = "/disabled", method = RequestMethod.GET)
    public ModelAndView disabledPage(){
        ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("/activationError");
            return modelAndView;
    }

}
