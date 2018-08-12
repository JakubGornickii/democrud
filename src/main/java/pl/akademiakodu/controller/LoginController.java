package pl.akademiakodu.controller;

import org.springframework.web.bind.annotation.RestController;
import pl.akademiakodu.model.Employer;
import pl.akademiakodu.model.User;
import pl.akademiakodu.other.UserData;
import pl.akademiakodu.repository.EmployerRepository;
import pl.akademiakodu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@RestController
public class LoginController {
    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserData userData;


    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }


    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "Urzytkownik o podanym mailu już istnieje");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "Urzytkownik zarejestrowany pomyślnie");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registration");

        }
        return modelAndView;
    }

    @RequestMapping(value = "/admin/index", method = RequestMethod.GET)
    public ModelAndView adminhome() {
        List<Employer> allEmployers = employerRepository.findAll();
        ModelAndView modelAndView = userData.getUserData();
        modelAndView.addObject("allEmployers", allEmployers);

        modelAndView.setViewName("admin/home");

        return modelAndView;
    }

    @RequestMapping(value = "/user/index", method = RequestMethod.GET)
    public ModelAndView userhome() {
        List<Employer> allEmployers = employerRepository.findAll();
        ModelAndView modelAndView = userData.getUserData();
        modelAndView.addObject("allEmployers", allEmployers);
        modelAndView.setViewName("user/home");
        return modelAndView;
    }

    @RequestMapping(value = "/accessdenied", method = RequestMethod.GET)
    public ModelAndView accessdenied() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("accessdenied");
        return modelAndView;
    }


}
