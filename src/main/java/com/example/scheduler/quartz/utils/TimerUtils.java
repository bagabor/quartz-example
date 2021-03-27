package com.example.scheduler.quartz.utils;

import java.util.Date;

import com.example.scheduler.quartz.config.TimerConfigDto;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

public class TimerUtils {

    public static JobDetail buildJobDetails(final Class jobClass, final TimerConfigDto config) {

        final JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(jobClass.getSimpleName(), config);

        return JobBuilder
                .newJob(jobClass)
                .withIdentity(jobClass.getSimpleName())
                .withDescription("")
                .setJobData(jobDataMap).build();
    }

    public static Trigger builderTrigger(final Class jobClass, final TimerConfigDto config) {
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(config.getRepeateTimeInMs());

        if (config.isRunForever()) {
            simpleScheduleBuilder = simpleScheduleBuilder.repeatForever();
        } else {
            simpleScheduleBuilder = simpleScheduleBuilder.withRepeatCount(config.getTotalFireCount() -1);
        }

        return TriggerBuilder
                .newTrigger()
                .withIdentity(jobClass.getSimpleName())
                .withDescription("")
                .withSchedule(simpleScheduleBuilder)
                .startAt(new Date(System.currentTimeMillis() + config.getOffsetTime()))
                .build();
    }
}
