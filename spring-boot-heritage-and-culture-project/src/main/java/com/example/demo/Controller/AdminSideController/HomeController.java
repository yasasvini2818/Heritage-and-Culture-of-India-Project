package com.example.demo.Controller.AdminSideController;

import com.example.demo.Entity.Category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.Services.categoryService;

@Controller
@RequestMapping("admin")
public class HomeController {

    @Autowired
    private categoryService service;
    private List<Category> categories;

    public HomeController(categoryService service){
        this.service=service;
        categories=service.getAllCategory();
    }

    @GetMapping("/login")
    public String showLoginPage() {
        System.out.println("loginnnnnnnnnnnnnnnnnnnnnnn");
        return "admin/authentication/login";
    }

    @GetMapping("/index")
    public String home(Model model){
        System.out.println("homeee");
        model.addAttribute("categories", categories);
        return "admin/index";
    }

    

    @GetMapping("/add/category")
    public String addCategory(Model model){
        Category thCategory = new Category();
        model.addAttribute("category", thCategory);
        return "admin/category";
    }

    @PostMapping("/cat/processaddform")
    public String processAddForm(@ModelAttribute("category") Category thCategory){
        service.saveCategory(thCategory);
        this.categories=service.getAllCategory();
        return "redirect:/admin/index";
    }   
}
