package com.csci5308.g17.slot;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.csci5308.g17.utils.JsonResponseDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/slot")
public class SlotController {

    private ISlotService slotService;

    public SlotController() {
        slotService = SlotService.getInstance();
    }

    @GetMapping("")
    public ResponseEntity<JsonResponseDTO> getSlotByFacilityId(@RequestParam("facility_id") Integer facilityId, @RequestParam("date") String dateStr) {
        ResponseEntity response = null;
        JsonResponseDTO responseBody = null;
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
        responseBody = new JsonResponseDTO(true, "Slot list", null, slots);
        response = new ResponseEntity(responseBody, HttpStatus.OK);
        return response;
    }
}
