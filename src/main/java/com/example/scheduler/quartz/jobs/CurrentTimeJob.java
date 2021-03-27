package com.example.scheduler.quartz.jobs;

import java.text.SimpleDateFormat;

import com.example.scheduler.quartz.config.TimerConfigDto;
import com.example.scheduler.schedulers.ScheduledTasks;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CurrentTimeJob implements Job {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Override
    public void execute(final JobExecutionContext jobExecutionContext) {
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        TimerConfigDto timerConfigDto = (TimerConfigDto) jobDataMap.get(CurrentTimeJob.class.getSimpleName());
        //        log.info("The time is now {} from quartz. and callbackData: {}", dateFormat.format(new Date()), timerConfig.getCallbackData());
        log.info("The remaining fire count is: {}", timerConfigDto.getRemainingFireCount());
    }
}
