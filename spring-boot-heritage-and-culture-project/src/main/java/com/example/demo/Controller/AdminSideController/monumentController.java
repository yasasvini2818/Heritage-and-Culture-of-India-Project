package com.example.demo.Controller.AdminSideController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.Entity.Monuments;
import com.example.demo.Services.monumentService;
import com.example.demo.Services.stateService;

@Controller
@RequestMapping("admin/monuments")
public class monumentController {
    public monumentController() {

    }

    @Autowired
    private monumentService service;

    public monumentController(monumentService service) {
        this.service = service;
    }

    @Autowired
    private stateService theStateService;

    public monumentController(stateService theStateService) {
        this.theStateService = theStateService;
    }

    // get all foods
    @GetMapping
    public String index(Model model) {
        model.addAttribute("monuments", service.getAllMonuments());
        return "admin/monuments/allMonuments";
    }

    // add new food
    @GetMapping("/add")
    public String getAddForm(Model model) {
        Monuments theMonument = new Monuments();
        model.addAttribute("monument", theMonument);
        model.addAttribute("states", theStateService.getAllStates());
        model.addAttribute("actionPath", "/admin/monuments/process/add");
        return "admin/monuments/showForm";
    }

    // process add form
    @PostMapping("/process/add")
    public String addMonuments(@ModelAttribute("monument") Monuments theMonument) {
        processImageLists(theMonument);
        service.saveChanges(theMonument);
        return "redirect:/admin/monuments";
    }

    // get update form of food
    @GetMapping("/update/{id}")
    public String getUpdateForm(Model model, @PathVariable int id) {
        Monuments theMonument = service.getById(id);
        model.addAttribute("monument", theMonument);
        model.addAttribute("actionPath", "/admin/monuments/process/update/" + id);
        model.addAttribute("states", theStateService.getAllStates());
        return "admin/monuments/showForm";
    }

    // process update form of food
    @PostMapping("/process/update/{id}")
    public String updateFoodChanges(@PathVariable int id, @ModelAttribute("monument") Monuments theMonument) {
        processImageLists(theMonument);
        theMonument.setId(id);
        service.saveChanges(theMonument);
        return "redirect:/admin/monuments";
    }

    // delete food
    @GetMapping("/delete/{id}")
    public String deleteMonuments(@PathVariable int id) {
        service.remove(id);
        return "redirect:/admin/monuments";
    }

    // process and get imagelist
    public void processImageLists(Monuments theMonument) {
        List<String> imageList = theMonument.getImageList() != null ? theMonument.getImageList() : null;
        if (imageList != null) {
            imageList.remove(null);
            imageList.remove("");            
            String images = String.join(",", imageList);
            if (!images.isBlank()) {
                theMonument.setImages(images);
            }
        }
    }

}
