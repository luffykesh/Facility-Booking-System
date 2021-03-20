package com.csci5308.g17.facility;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/facility")
public class FacilityController {

    IFacilityService facilityService;
    public FacilityController(IFacilityService facilityService) {
        this.facilityService = facilityService;
    }

    @GetMapping(value = "/{id}")
    public List<Facility> getFacilityById(@PathVariable(value="id") int id){
        System.out.println(id);
        return this.facilityService.getFacilityById(id);
    }

    @PostMapping()
    public void Save(@ModelAttribute("facility") Facility facility){
        this.facilityService.Save(facility);
    }
}
