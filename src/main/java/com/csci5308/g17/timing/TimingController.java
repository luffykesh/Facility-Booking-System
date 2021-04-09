package com.csci5308.g17.timing;

import com.csci5308.g17.auth.CurrentUserService;
import com.csci5308.g17.utils.JsonResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/timing")
public class TimingController {

    ITimingService timingService;
    CurrentUserService currentUserService;
    Logger logger = LoggerFactory.getLogger(TimingController.class);

    public TimingController() {
        timingService = TimingService.getInstance();
        currentUserService = CurrentUserService.getInstance();
    }

    @GetMapping("")
    public String getTimingsForFacility(@RequestParam(name = "facility_id") Integer facilityId, Model model) {
        List<Timing> timings = new ArrayList<>();
        try {
            timings = timingService.getFacilityTimings(facilityId);
        } catch (Exception e) {
            String errMessage = "Error retriving timings from database";
            logger.error(errMessage, e);
        }

        model.addAttribute("facilityId", facilityId);
        model.addAttribute("user", currentUserService.getCurrentUser());
        model.addAttribute("timingList", timings);
        return "timing";
    }

    @PostMapping()
    public RedirectView addTiming(@ModelAttribute("timing") TimingDTO requestTiming, RedirectAttributes attributes) {

        JsonResponseDTO<String> responseData = null;
        Timing newTiming = new Timing();

        newTiming.setDay(DayOfWeek.valueOf(requestTiming.getDay()));
        newTiming.setStartTime(LocalTime.parse(requestTiming.getStartTime()));
        newTiming.setEndTime(LocalTime.parse(requestTiming.getEndTime()));
        newTiming.setFacilityId(requestTiming.getFacilityId());

        try {
            newTiming = timingService.addTiming(newTiming);
            responseData = new JsonResponseDTO(true, "Timing added", null, newTiming);
        } catch (TimingConflictException e) {
            logger.error(e.getT1() + " conflict with " + e.getT2(), e);
            responseData = new JsonResponseDTO(false, "Cannot add conflicting timing", "Timing conflict", null);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid time range", e);
            responseData = new JsonResponseDTO(false, e.getMessage(), "Invalid time range", null);
        }
        attributes.addAttribute("facility_id", requestTiming.getFacilityId());
        attributes.addFlashAttribute("message", responseData.getMessage());
        return new RedirectView("/timing");
    }

    @GetMapping("/{id}")
    public RedirectView deleteTiming(@PathVariable("id") Integer timingId, RedirectAttributes attributes) {
        JsonResponseDTO<String> responseData = null;
        Timing timing = timingService.getTiming(timingId);
        try {
            timingService.deleteTiming(timingId);
            logger.info("Deleted timing: " + timingId);
            responseData = new JsonResponseDTO(true, "Timing deleted", null, null);
        } catch (Exception e) {
            logger.error("Error deleting exception", e);
            responseData = new JsonResponseDTO(true, "Error deleting", null, null);
        }

        attributes.addAttribute("facility_id", timing.getFacilityId());
        attributes.addFlashAttribute("message", responseData.getMessage());
        return new RedirectView("/timing");
    }
}
