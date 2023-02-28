package com.example.demo.Schedule;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/v1/schedule")
public class ScheduleEntryController {
    private final ScheduleEntryService scheduleEntryService;
    @Autowired
    public ScheduleEntryController(ScheduleEntryService scheduleEntryService){
        this.scheduleEntryService = scheduleEntryService;
    }

    @GetMapping
    public ResponseEntity<List<ScheduleEntry>> getScheduleEntries(){
        return new ResponseEntity<>(scheduleEntryService.getScheduleEntries(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createScheduleEntry(@RequestBody ScheduleEntry scheduleEntry){
        String result = scheduleEntryService.addScheduleEntry(scheduleEntry);
        return new ResponseEntity<>("Returned: " + result,HttpStatus.OK);
    }
}
