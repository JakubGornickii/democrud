package pl.akademiakodu.controller;

import pl.akademiakodu.model.Employer;
import pl.akademiakodu.model.User;
import pl.akademiakodu.repository.EmployerRepository;
import pl.akademiakodu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController {
@Autowired
	private EmployerRepository employerRepository;

	@Autowired
	private UserService userService;


	@RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
	public ModelAndView login(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}
	
	
	@RequestMapping(value="/registration", method = RequestMethod.GET)
	public ModelAndView registration(){
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
							"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		} else {
			userService.saveUser(user);
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("registration");
			
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/index", method = RequestMethod.GET)
	public ModelAndView adminhome(){
		List<Employer> allEmployers = employerRepository.findAll();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("allEmployers", allEmployers);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("userName", user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		modelAndView.addObject("adminMessage","Poziom uprawnień - Administrator");
		modelAndView.setViewName("admin/home");

		return modelAndView;
	}

	@RequestMapping(value="/user/index", method = RequestMethod.GET)
	public ModelAndView userhome(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("userName", user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		modelAndView.addObject("userMessage","Poziom uprawnień - Urzytkownik");
		modelAndView.setViewName("user/index");
		return modelAndView;
	}
	@RequestMapping(value = "/accessdenied", method = RequestMethod.GET)
	public ModelAndView accessdenied(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("accessdenied");
		return modelAndView;
	}


}
