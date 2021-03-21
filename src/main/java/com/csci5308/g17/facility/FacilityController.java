package com.csci5308.g17.facility;

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
    public Facility getFacilityById(@PathVariable(value = "id") int id) {
        System.out.println(id);
        return this.facilityService.getFacilityById(id);
    }

    @PostMapping()
    public void save(@ModelAttribute("facility") FormFacility formFacility) {
        this.facilityService.save(formFacility);
    }

    @GetMapping()
    public List<Facility> findAll() {
        return this.facilityService.findAll();
    }

    @PutMapping(value = "/{id}")
    public void updateFacility(@PathVariable(value = "id") int id,@ModelAttribute("facility") FormFacility formFacility){
        System.out.println(id);
        System.out.println(formFacility.managerEmail);
        this.facilityService.updateFacility(id,formFacility);
    }
}
