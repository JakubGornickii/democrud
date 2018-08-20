package pl.akademiakodu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

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
            EmailToken emailToken = emailTokenService.generateToken(user1.getId(), "REGISTER");
            String email = emailGeneratorService.getEmailRegistation(emailToken.getToken());
            emailService.sendSimpleMessage(user.getEmail(), "Potwierdzenie rejestracji", email);
            modelAndView.addObject("successMessage", "Urzytkownik zarejestrowany pomyślnie. Sprawdź swój email");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registration");

        }
        return modelAndView;
    }

    @RequestMapping(value = "/reSendActivationLink", method = RequestMethod.POST)
    public ModelAndView reSend(@RequestParam("email") String email) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.findUserByEmail(email);
        if (user.getActive() == 1) {
            modelAndView.addObject("message","Podane konto jest już aktywne");

            return modelAndView;
        } else if (user.getActive() == 0) {
            EmailToken emailToken = emailTokenService.findByUserIdActiveToken(user.getId());
            if (emailToken != null) {
                emailTokenService.tokenExpire(emailToken);
            }
            EmailToken emailToken1 = emailTokenService.generateToken(user.getId(), "REGISTER");
            String generatedEmail = emailGeneratorService.getNewEmailRegistration(emailToken1.getToken());
            emailService.sendSimpleMessage(user.getEmail(), "Potwierdzenie rejestracji", generatedEmail);
            modelAndView.addObject("message","Link aktywacyjny został ponownie wysłany na maila");
        }
        modelAndView.setViewName("/infoPage");
        return modelAndView;
    }

    @RequestMapping(value = "/registration/confirm/{token}", method = RequestMethod.GET)
    public ModelAndView activeUser(@PathVariable("token") String token) {
        ModelAndView modelAndView = new ModelAndView();
        EmailToken emailToken = emailTokenService.findByToken(token);
        if (emailToken == null) {
            modelAndView.addObject("message","Błędny link aktywacyjny");
        }
        else if (emailToken.isActive()) {
            emailTokenService.tokenExpire(emailToken);
            userService.setActive(emailToken.getUserId());
            modelAndView.addObject("message","Konto potwierdzone pomyślnie");
        } else if (!emailToken.isActive()){
            modelAndView.addObject("message","Link aktywacyjny stracił ważność prosze wygenerować nowy");
        }
        modelAndView.setViewName("/infoPage");
        return modelAndView;
    }


}
