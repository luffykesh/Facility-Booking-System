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
    public String getFacilityById(@PathVariable(value = "id") int id,Model model) {
        FormFacility facility = this.facilityService.getFacilityById(id);
        System.out.println("desc  " + facility.getDescription());
        System.out.println(facility.getActive());
        System.out.println(facility.getApprovalRequired());
        model.addAttribute("name",facility.getName());
        model.addAttribute("description",facility.getDescription());
        model.addAttribute("location",facility.getLocation());
        model.addAttribute("manager",facility.getManagerEmail());
        model.addAttribute("occupancy",facility.getOccupancy());
        model.addAttribute("timeslot",facility.getTimeSlot());
        model.addAttribute("active",facility.getActive());
        model.addAttribute("approvalRequired",facility.getApprovalRequired());
        model.addAttribute("id",facility.getId());
        return "updateFacility";
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
    public String findAll(Model model) {
        List<FormFacility> facilityList = this.facilityService.findAll();
        model.addAttribute("facilities",facilityList);
        return "displayFacility";

    }

    @PostMapping(value = "/update/{id}")
    public String updateFacility(@PathVariable(value = "id") int id, @ModelAttribute("facility") FormFacility formFacility) {
        System.out.println("in update");
        System.out.println(formFacility.getDescription());
        System.out.println(formFacility.getActive());
        System.out.println(formFacility.getApprovalRequired());
        this.facilityService.updateFacility(id, formFacility);
        return "redirect:/facility";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteFacility(@PathVariable(value="id") int id) {
        this.facilityService.deleteFacility(id);
        return "redirect:/facility";
    }
}
