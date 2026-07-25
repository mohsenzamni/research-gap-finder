package com.mohsenzamni.researchgapfinder.project;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ResearchProjectController {

    private final ResearchProjectService service;

    public ResearchProjectController(ResearchProjectService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/projects";
    }

    @GetMapping("/projects")
    public String list(Model model) {
        model.addAttribute("projects", service.findAll());
        return "projects/list";
    }

    @GetMapping("/projects/new")
    public String createForm(Model model) {
        model.addAttribute("project", new ResearchProject());
        return "projects/form";
    }

    @PostMapping("/projects")
    public String create(@Valid ResearchProject project, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "projects/form";
        }
        return "redirect:/projects/" + service.create(project).getId();
    }

    @GetMapping("/projects/{id}")
    public String details(@PathVariable Long id, Model model) {
        model.addAttribute("project", service.findById(id));
        return "projects/details";
    }
}
