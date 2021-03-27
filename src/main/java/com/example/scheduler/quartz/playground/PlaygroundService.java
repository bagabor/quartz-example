package com.example.scheduler.quartz.playground;

import java.util.List;

import com.example.scheduler.quartz.config.TimerConfigDto;
import com.example.scheduler.quartz.jobs.CurrentTimeJob;
import com.example.scheduler.quartz.services.QuartzService;
import org.springframework.stereotype.Service;

@Service
public class PlaygroundService {

    private final QuartzService quartzService;

    public PlaygroundService(final QuartzService quartzService) {this.quartzService = quartzService;}

    public void runCurrentTimeJob() {
        final TimerConfigDto timerConfigDto = new TimerConfigDto();
        timerConfigDto.setTotalFireCount(100);
        timerConfigDto.setRemainingFireCount(timerConfigDto.getTotalFireCount());
        timerConfigDto.setRunForever(false);
        timerConfigDto.setRepeateTimeInMs(1000);
        timerConfigDto.setCallbackData("My callback data.");

        quartzService.schedule(CurrentTimeJob.class, timerConfigDto);
    }

    public List<TimerConfigDto> getAllRunningTimers() {
        return quartzService.getRunningTimers();
    }

    public TimerConfigDto getAllRunningTimer(final String timerId) {
        return quartzService.getRunningTimer(timerId);
    }

    public Boolean deleteTimer(final String timerId) {
        return quartzService.deleteTimer(timerId);
    }
}
