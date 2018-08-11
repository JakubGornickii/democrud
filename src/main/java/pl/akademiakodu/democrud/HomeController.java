package pl.akademiakodu.democrud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HomeController {
    private EmployerRepository employerRepository;

    @Autowired
    public HomeController(EmployerRepository employerRepository) {
        this.employerRepository = employerRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Employer> allEmployers = employerRepository.findAll();
        model.addAttribute("allEmployers", allEmployers);
        return "index";
    }

    @GetMapping("/add")
    public String addSite() {
        return "adduser";
    }

    @PostMapping("/add")
    public String addEmployer(@ModelAttribute Employer employer) {
        employerRepository.save(employer);
        return "redirect:/";
    }

    @RequestMapping("/delete")
    public String delete(@RequestParam("id") Integer id) {
        employerRepository.deleteById(id);
        return "redirect:/";

    }

    @RequestMapping("/edit")
    public String edit(@RequestParam("id") Integer id, Model model) {
        Employer employer = employerRepository.getOne(id);
        model.addAttribute("employer", employer);
        return "edituser";
    }

    @PostMapping("/edit")
    public String editEmployer(@RequestParam("id") Integer id, @ModelAttribute Employer employer) {
        employer.setId(id);
        employerRepository.save(employer);
        return "redirect:/";
    }
}
