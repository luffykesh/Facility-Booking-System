package com.csci5308.g17.slot;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.csci5308.g17.facility.Facility;
import com.csci5308.g17.facility.FacilityService;
import com.csci5308.g17.facility.IFacilityService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/slot")
public class SlotController {

    private ISlotService slotService;
    private IFacilityService facilityService;

    public SlotController() {
        slotService = SlotService.getInstance();
        facilityService = FacilityService.getInstance();
    }

    @GetMapping("")
    public String getSlotByFacilityId(@RequestParam("facility_id") Integer facilityId, @RequestParam("date") String dateStr, Model model) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("y-M-d");
        LocalDate date = LocalDate.parse(dateStr, formatter);
        List<Slot> slots = null;
        if(date.isAfter(LocalDate.now())) {
            slots = slotService.getSlotsForFacility(facilityId, date);
        }
        else {
            slots = slotService.getSlotsForFacility(
                facilityId, date.atTime(LocalTime.now()), date.plusDays(1).atTime(LocalTime.MIN));
        }
        Facility facility = facilityService.getFacilityById(facilityId);
        model.addAttribute("facility", facility);
        model.addAttribute("slotList",slots);
        model.addAttribute("id",facilityId);
        return "user_slot_display";
    }
}
