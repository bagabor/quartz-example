package com.example.scheduler.quartz.controller;

import java.util.List;

import com.example.scheduler.quartz.config.TimerConfigDto;
import com.example.scheduler.quartz.playground.PlaygroundService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//https://www.youtube.com/watch?v=YV_wvqbzq-w&ab_channel=LiliumCode - for storing in db and additional inf.

@RestController
@RequestMapping("/api/timer")
public class TimerController {

    private final PlaygroundService service;

    public TimerController(final PlaygroundService service) {this.service = service;}

    @PostMapping("/run")
    public void runTimer() {
        service.runCurrentTimeJob();
    }

    @GetMapping
    public List<TimerConfigDto> getRunningTimers() {
        return service.getAllRunningTimers();
    }

    @GetMapping("/{timerId}")
    public TimerConfigDto getRunningTimer(@PathVariable String timerId) {
        return service.getAllRunningTimer(timerId);
    }

    //localhost:8080/api/timer/CurrentTimeJob
    @DeleteMapping("/{timerId}")
    public Boolean deleteTimer(@PathVariable String timerId) {
        return service.deleteTimer(timerId);
    }
}
