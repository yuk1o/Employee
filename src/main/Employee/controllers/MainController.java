package com.yuk1of.Employee.controllers;

import com.yuk1of.Employee.models.Employee;
import com.yuk1of.Employee.repositorys.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Главная страница");
        Iterable<Employee> employees = employeeRepository.findAll();
        model.addAttribute("employees", employees);
        return "home";
    }

    @GetMapping("/add")
    public String employeeAdd(Model model) {
        return "employee-add";
    }

    @PostMapping("/add")
    public String employeeAdd(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String emailId, Model model) {
        Employee employee = new Employee(firstName,lastName,emailId);
        employeeRepository.save(employee);
        return "redirect:/";
    }
    @GetMapping("/home/{id}")
    public String employeeDetails(@PathVariable(value = "id") long id, Model model) {

        if(!employeeRepository.existsById(id)){
            return "redirect:/";
        }

        Optional<Employee> employee = employeeRepository.findById(id);
        ArrayList<Employee> res = new ArrayList<>();
        employee.ifPresent(res :: add);
        model.addAttribute("employee", res);
        return "employee-details";
    }

    @GetMapping("/home/{id}/edit")
    public String employeeEdit(@PathVariable(value = "id") long id, Model model) {

        if(!employeeRepository.existsById(id)){
            return "redirect:/";
        }

        Optional<Employee> employee = employeeRepository.findById(id);
        ArrayList<Employee> res = new ArrayList<>();
        employee.ifPresent(res :: add);
        model.addAttribute("employee", res);
        return "employee-edit";
    }

    @PostMapping("/home/{id}/edit")
    public String employeeUpdate(@PathVariable(value = "id") long id, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String emailId, Model model) {
        Employee employee = employeeRepository.findById(id).orElseThrow();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setEmailId(emailId);
        employeeRepository.save(employee);
        return "redirect:/";
    }
    @PostMapping("/home/{id}/remove")
    public String employeeDelete(@PathVariable(value = "id") long id, Model model) {
        Employee employee = employeeRepository.findById(id).orElseThrow();
        employeeRepository.delete(employee);
        return "redirect:/";
    }
}

