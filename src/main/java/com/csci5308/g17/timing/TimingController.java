package com.csci5308.g17.timing;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import com.csci5308.g17.utils.JsonResponseDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/timing")
public class TimingController {

    ITimingService timingService;
    Logger logger = LoggerFactory.getLogger(TimingController.class);

    public TimingController() {
        timingService = TimingService.getInstance();
    }

    @GetMapping("")
    public ResponseEntity<JsonResponseDTO> getTimingsForFacility(@RequestParam(name="facility_id") Integer facilityId) {
        List<Timing> timings = timingService.getFacilityTimings(facilityId);
        JsonResponseDTO<List<TimingDTO>> responseData = null;
        ResponseEntity<JsonResponseDTO> response = null;

        try {
            List<TimingDTO> timingDTOs = timings.stream().map((timing) -> new TimingDTO(timing)).collect(Collectors.toList());
            responseData = new JsonResponseDTO(true, "Timing list", null, timingDTOs);
            response = new ResponseEntity(responseData, HttpStatus.OK);
        }
        catch(Exception e) {
            String errMessage = "Error retriving timings from database";
            logger.error(errMessage, e);
            responseData = new JsonResponseDTO(false, null, errMessage, null);
            response = new ResponseEntity(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @PostMapping(path = "", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<JsonResponseDTO> addTiming(@ModelAttribute("timing") TimingDTO requestTiming) {

        JsonResponseDTO<String> responseData = null;
        ResponseEntity<JsonResponseDTO> response = null;
        Timing newTiming = new Timing();

        newTiming.setDay(DayOfWeek.valueOf(requestTiming.getDay()));
        newTiming.setStartTime(LocalTime.parse(requestTiming.getStartTime()));
        newTiming.setEndTime(LocalTime.parse(requestTiming.getEndTime()));
        newTiming.setFacilityId(requestTiming.getFacilityId());

        try {
            newTiming = timingService.addTiming(newTiming);
            responseData = new JsonResponseDTO(true, "Timing added", null, newTiming);
            response = new ResponseEntity(responseData, HttpStatus.OK);
        }
        catch(TimingConflictException e) {
            logger.error(e.getT1() + " conflict with " + e.getT2(), e);
            responseData = new JsonResponseDTO(false, "Cannot add conflicting timing", "Timing conflict", null);
            response = new ResponseEntity(responseData, HttpStatus.CONFLICT);
        }
        catch(IllegalArgumentException e) {
            logger.error("Invalid time range", e);
            responseData = new JsonResponseDTO(false, e.getMessage(), "Invalid time range", null);
            response = new ResponseEntity(responseData, HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<JsonResponseDTO> deleteTiming(@PathVariable("id") Integer timingId) {
        JsonResponseDTO<String> responseData = null;
        ResponseEntity<JsonResponseDTO> response = null;

        try{
            timingService.deleteTiming(timingId);
            logger.info("Deleted timing: "+ timingId);
        }
        catch(Exception e) {
            logger.error("Error deleting exception", e);
        }

        responseData = new JsonResponseDTO(true, "Timing deleted", null, null);
        response = new ResponseEntity(responseData, HttpStatus.OK);

        return response;
    }

}
