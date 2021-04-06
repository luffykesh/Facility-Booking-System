package com.csci5308.g17.facility;

import com.csci5308.g17.auth.CurrentUserService;
import com.csci5308.g17.user.IUserService;
import com.csci5308.g17.user.User;
import com.csci5308.g17.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/facility")
public class FacilityController {

    private final IFacilityService facilityService;
    private final IUserService userService;
    private final CurrentUserService currentUserService;
    public FacilityController() {

        facilityService = FacilityService.getInstance();
        userService = UserService.getInstance();
        currentUserService=CurrentUserService.getInstance();
    }

    @GetMapping(value = "/{id}")
    public String getFacilityById(@PathVariable(value = "id") int id,Model model) {
        Facility facility = this.facilityService.getFacilityById(id);

        FormFacility formFacility = new FormFacility();

        User u = userService.getUserById(facility.getManagerId());
        formFacility.setApprovalRequired(facility.getApprovalRequired());
        formFacility.setManagerEmail(u.getEmail());
        formFacility.setActive(facility.getActive());
        formFacility.setOccupancy(facility.getOccupancy());
        formFacility.setTimeSlot(facility.getTimeSlot());
        formFacility.setLocation(facility.getLocation());
        formFacility.setName(facility.getName());
        formFacility.setDescription(facility.getDescription());
        formFacility.setId(facility.getId());

        model.addAttribute("name",formFacility.getName());
        model.addAttribute("description",formFacility.getDescription());
        model.addAttribute("location",formFacility.getLocation());
        model.addAttribute("manager",formFacility.getManagerEmail());
        model.addAttribute("occupancy",formFacility.getOccupancy());
        model.addAttribute("timeslot",formFacility.getTimeSlot());
        model.addAttribute("active",formFacility.getActive());
        model.addAttribute("approvalRequired",formFacility.getApprovalRequired());
        model.addAttribute("id",formFacility.getId());
        if(currentUserService.isAdmin()) {
            return "updateFacility";
        }
        else {
            return "update_facility_manager";
        }
    }

    @GetMapping(value = "/addFacility")
    public String addFacility() {
        return "addFacility";
    }

    @PostMapping()
    public String save(@ModelAttribute("facility") FormFacility formFacility, Model model) {
        User u = userService.getUserByEmail(formFacility.getManagerEmail());

        if (formFacility.getActive() == null) {
            formFacility.setActive(false);
        }
        if (formFacility.getApprovalRequired() == null) {
            formFacility.setApprovalRequired(false);
        }
        Facility facility = new Facility();
        facility.setApprovalRequired(formFacility.getApprovalRequired());
        facility.setManagerId(u.getId());
        facility.setActive(formFacility.getActive());
        facility.setOccupancy(formFacility.getOccupancy());
        facility.setTimeSlot(formFacility.getTimeSlot());
        facility.setLocation(formFacility.getLocation());
        facility.setName(formFacility.getName());
        facility.setDescription(formFacility.getDescription());

        this.facilityService.save(facility);
        model.addAttribute("message","Facility added Successfully.");
        return "addFacility";
    }

    @GetMapping()
    public String findAll(Model model) {
        List<Facility> facilityList = this.facilityService.findAll();
        List<FormFacility> formFacilityList = new ArrayList<FormFacility>();

        for(Facility f1 : facilityList) {
            FormFacility formFacility = new FormFacility();
            User u = userService.getUserById(f1.getManagerId());
            formFacility.setApprovalRequired(f1.getApprovalRequired());
            formFacility.setManagerEmail(u.getEmail());
            formFacility.setActive(f1.getActive());
            formFacility.setOccupancy(f1.getOccupancy());
            formFacility.setTimeSlot(f1.getTimeSlot());
            formFacility.setLocation(f1.getLocation());
            formFacility.setName(f1.getName());
            formFacility.setDescription(f1.getDescription());
            formFacility.setId(f1.getId());
            formFacilityList.add(formFacility);
        }
        model.addAttribute("facilities",formFacilityList);
        return "displayFacility";

    }

    @PostMapping(value = "/update/{id}")
    public String updateFacility(@PathVariable(value = "id") int id, @ModelAttribute("facility") FormFacility formFacility) {
        User u = userService.getUserByEmail(formFacility.getManagerEmail());

        if (formFacility.getActive() == null) {
            formFacility.setActive(false);
        }
        if (formFacility.getApprovalRequired() == null) {
            formFacility.setApprovalRequired(false);
        }
        Facility facility = new Facility();
        facility.setApprovalRequired(formFacility.getApprovalRequired());
        facility.setManagerId(u.getId());
        facility.setActive(formFacility.getActive());
        facility.setOccupancy(formFacility.getOccupancy());
        facility.setTimeSlot(formFacility.getTimeSlot());
        facility.setLocation(formFacility.getLocation());
        facility.setName(formFacility.getName());
        facility.setDescription(formFacility.getDescription());
        this.facilityService.updateFacility(id, facility);
        return "redirect:/facility";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteFacility(@PathVariable(value="id") int id) {
        this.facilityService.deleteFacility(id);
        return "redirect:/facility";
    }
    @GetMapping("/display_manager_facility")
    public String getUserEmail(Model model){
        model.addAttribute("facility",facilityService.getManagerFacilities(currentUserService.getCurrentUser().getId()));
        return "display_manager_facility";
    }
}
