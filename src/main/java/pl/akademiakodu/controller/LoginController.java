package pl.akademiakodu.controller;

import org.springframework.web.bind.annotation.*;
import pl.akademiakodu.model.EmailToken;
import pl.akademiakodu.model.Employer;
import pl.akademiakodu.model.Post;
import pl.akademiakodu.model.User;
import pl.akademiakodu.other.UserData;
import pl.akademiakodu.repository.EmployerRepository;
import pl.akademiakodu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
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
    @Autowired
    EmailTokenService emailTokenService;
    @Autowired
    EmailGeneratorService emailGeneratorService;
    @Autowired
    EmailService emailService;

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
    public ModelAndView disabledPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/activationError");
        return modelAndView;
    }

    @RequestMapping(value = "/passwordRecover", method = RequestMethod.GET)
    public ModelAndView passRecoverPage() {
        return new ModelAndView("/passwordRecover");
    }

    @RequestMapping(value = "/passwordRecover", method = RequestMethod.POST)
    public ModelAndView passwordRecover(@RequestParam("email") String email) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.findUserByEmail(email);
        if (user == null) {
            modelAndView.addObject("message", "Konto o podanym mailu nie istnieje");
        } else {
            EmailToken emailToken = emailTokenService.generateToken(user.getId(), "PAS_RECOVER");
            String generatedEmail = emailGeneratorService.getEmailPassworldRecover(emailToken.getToken());
            emailService.sendSimpleMessage(user.getEmail(), "Zmiana hasła", generatedEmail);
            modelAndView.addObject("message", "Na podany adres email wysłano linka do zmiany hasła");
        }
        modelAndView.setViewName("/infoPage");
        return modelAndView;
    }

    @RequestMapping(value = "/password/recover/{token}", method = RequestMethod.GET)
    public ModelAndView newPassword(@PathVariable("token") String token) {
        ModelAndView modelAndView = new ModelAndView();
        EmailToken emailToken = emailTokenService.findByToken(token);
        if (emailToken == null || !emailToken.getUsefor().equals("PAS_RECOVER")) {
            modelAndView.addObject("message", "Błędny link do zmiany hasła");
            modelAndView.setViewName("/infoPage");
            return modelAndView;
        } else if (!emailToken.isActive()) {
            modelAndView.addObject("message", "Link do zmiany hasła stracił ważność prosze wygenerować nowy");
            modelAndView.setViewName("/infoPage");
            return modelAndView;
        } else if (emailToken.isActive() && emailToken.getUsefor().equals("PAS_RECOVER")) {
            User user = new User();
            modelAndView.addObject("user", user);
            modelAndView.addObject("token", emailToken.getToken());

            modelAndView.setViewName("/newPassword");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/newPassword", method = RequestMethod.POST)
    public ModelAndView newPass(@Valid User user, BindingResult bindingResult, @RequestParam("token") String token) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("/newPassword");
        }
        if (token == null) {
            modelAndView.addObject("message", ":)");
            modelAndView.setViewName("/infoPage");
            return modelAndView;
        } else {
            EmailToken emailToken = emailTokenService.findByToken(token);
            if (emailToken.isActive() && emailToken.getUsefor().equals("PAS_RECOVER")) {
                emailTokenService.tokenExpire(emailToken);
                userService.updatePassword(user.getPassword(), emailToken.getUserId());
                modelAndView.addObject("message", "Hasło zmienione");
                modelAndView.setViewName("/infoPage");
            }
        }
        return modelAndView;

    }
}