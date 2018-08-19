package pl.akademiakodu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import pl.akademiakodu.model.EmailToken;
import pl.akademiakodu.model.User;
import pl.akademiakodu.service.EmailGeneratorService;
import pl.akademiakodu.service.EmailService;
import pl.akademiakodu.service.EmailTokenService;
import pl.akademiakodu.service.UserService;

import javax.validation.Valid;

@RestController
public class RegistrationController {

    @Autowired
    UserService userService;

    @Autowired
    EmailTokenService emailTokenService;

    @Autowired
    EmailService emailService;

    @Autowired
    EmailGeneratorService emailGeneratorService;

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
            User user1 = userService.findUserByEmail(user.getEmail());
            EmailToken emailToken = emailTokenService.generateToken(user1.getId(),"REGISTER");
            String email = emailGeneratorService.getEmailRegistation(emailToken.getToken());
            emailService.sendSimpleMessage(user.getEmail(),"Potwierdzenie rejestracji",email);
            modelAndView.addObject("successMessage", "Urzytkownik zarejestrowany pomyślnie. Sprawdź swój email");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registration");

        }
        return modelAndView;
    }
@RequestMapping(value = "/reSendActivationLink",method = RequestMethod.POST)
    public ModelAndView reSend(@RequestParam("email")String email)
    {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.findUserByEmail(email);
        if (user.getActive() == 1) {
            modelAndView.setViewName("/UserAlreadyActive");
            return modelAndView;
        }
        else if (user.getActive() == 0)
        {
            EmailToken emailToken = emailTokenService.findByUserId(user.getId());
            emailTokenService.tokenDelete(emailToken);
            EmailToken emailToken1 = emailTokenService.generateToken(user.getId(),"REGISTER");
             String generatedEmail = emailGeneratorService.getEmailRegistation(emailToken1.getToken());
             emailService.sendSimpleMessage(user.getEmail(),"Potwierdzenie rejestracji",generatedEmail);
        }
     return modelAndView;
    }







}
