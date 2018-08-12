package pl.akademiakodu.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import pl.akademiakodu.model.Employer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.akademiakodu.other.UserData;
import pl.akademiakodu.repository.EmployerRepository;

import javax.validation.Valid;


@Controller
public class CrudController {
    private EmployerRepository employerRepository;

    @Autowired
    UserData userData;

    @Autowired
    public CrudController(EmployerRepository employerRepository) {
        this.employerRepository = employerRepository;
    }

    @RequestMapping(value = "/admin/add", method = RequestMethod.GET)
    public ModelAndView addSite() {
        ModelAndView modelAndView = userData.getUserData();
        Employer employer = new Employer();
        modelAndView.addObject("employer", employer);
        modelAndView.setViewName("admin/adduser");
        return modelAndView;
    }


    @RequestMapping(value = "/admin/add", method = RequestMethod.POST)
    public ModelAndView createNewEmployer(@Valid Employer employer, BindingResult bindingResult) {
        ModelAndView modelAndView = userData.getUserData();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("admin/adduser");
        } else {
            employerRepository.save(employer);
            modelAndView.setViewName("redirect:/admin/index");
        }
        return modelAndView;
    }

    @RequestMapping(value = "admin/delete", method = RequestMethod.DELETE)
    public ModelAndView delete(@RequestParam("id") Integer id) {
        employerRepository.deleteById(id);
        return new ModelAndView("redirect:/admin/index");

    }


    @RequestMapping(value = "/admin/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam("id") Integer id) {
        Employer employer = employerRepository.getOne(id);
        ModelAndView modelAndView = userData.getUserData();
        modelAndView.addObject("employer", employer);
        modelAndView.setViewName("admin/edituser");
        return modelAndView;
    }

    @RequestMapping(value = "admin/edit", method = RequestMethod.POST)
    public ModelAndView editEmployer(@RequestParam("id") Integer id, @ModelAttribute @Valid Employer employer, BindingResult bindingResult) {
        ModelAndView modelAndView = userData.getUserData();

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("admin/edituser");
        } else {
            employer.setId(id);
            employerRepository.save(employer);
            modelAndView.setViewName("redirect:/admin/index");
        }

        return modelAndView;
    }

}
