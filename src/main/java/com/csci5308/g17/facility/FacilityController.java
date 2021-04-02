package com.csci5308.g17.facility;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/facility")
public class FacilityController {

    private final IFacilityService facilityService;

    public FacilityController() {
        facilityService = FacilityService.getInstance();
    }

    @GetMapping(value = "/{id}")
    public Facility getFacilityById(@PathVariable(value = "id") int id) {
        System.out.println(id);
        return this.facilityService.getFacilityById(id);
    }

    @GetMapping(value = "/addFacility")
    public String addFacility() {
        return "addFacility";
    }

    @PostMapping()
    public String save(@ModelAttribute("facility") FormFacility formFacility, Model model) {
        this.facilityService.save(formFacility);
        model.addAttribute("message","Facility added Successfully.");
        return "addFacility";
    }

    @GetMapping()
    public List<Facility> findAll() {
        return this.facilityService.findAll();
    }

    @PutMapping(value = "/{id}")
    public void updateFacility(@PathVariable(value = "id") int id, @ModelAttribute("facility") FormFacility formFacility) {

        this.facilityService.updateFacility(id, formFacility);
    }
}
