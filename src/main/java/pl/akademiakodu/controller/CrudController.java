package pl.akademiakodu.controller;

import pl.akademiakodu.model.Employer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.akademiakodu.repository.EmployerRepository;

@Controller
public class CrudController {
    private EmployerRepository employerRepository;

    @Autowired
    public CrudController(EmployerRepository employerRepository) {
        this.employerRepository = employerRepository;
    }


    @GetMapping("/admin/add")
    public String addSite() {
        return "admin/adduser";
    }

    @PostMapping("/admin/add")
    public String addEmployer(@ModelAttribute Employer employer) {
        employerRepository.save(employer);
        return "redirect:/admin/index";
    }

    @RequestMapping("/delete")
    public String delete(@RequestParam("id") Integer id) {
        employerRepository.deleteById(id);
        return "redirect:/admin/index";

    }

    @RequestMapping("/edit")
    public String edit(@RequestParam("id") Integer id, Model model) {
        Employer employer = employerRepository.getOne(id);
        model.addAttribute("employer", employer);
        return "/admin/edituser";
    }

    @PostMapping("/edit")
    public String editEmployer(@RequestParam("id") Integer id, @ModelAttribute Employer employer) {
        employer.setId(id);
        employerRepository.save(employer);
        return "redirect:/admin/index";
    }
}
