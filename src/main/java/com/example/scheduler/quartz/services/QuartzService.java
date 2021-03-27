package com.example.scheduler.quartz.services;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.example.scheduler.quartz.config.TimerConfigDto;
import com.example.scheduler.quartz.utils.TimerUtils;
import com.example.scheduler.schedulers.ScheduledTasks;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static java.util.stream.Collectors.toList;

@Service
public class QuartzService {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
    private final Scheduler scheduler;

    public QuartzService(final Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void schedule(final Class jobClass, final TimerConfigDto timer) {
        final JobDetail jobDetail = TimerUtils.buildJobDetails(jobClass, timer);
        final Trigger jobTrigger = TimerUtils.builderTrigger(jobClass, timer);

        try {
            scheduler.scheduleJob(jobDetail, jobTrigger);
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
        }
    }

    public List<TimerConfigDto> getRunningTimers() {
        try {
            return scheduler.getJobKeys(GroupMatcher.anyGroup()).stream().map(jobKey -> {
                try {
                    JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                    return (TimerConfigDto) jobDetail.getJobDataMap().get(jobKey.getName());
                } catch (SchedulerException e) {
                    log.error(e.getMessage(), e);
                    return null;
                }
            }).filter(Objects::nonNull).collect(toList());
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public TimerConfigDto getRunningTimer(final String timerId) {
        try {
            final JobDetail jobDetail = scheduler.getJobDetail(new JobKey(timerId));
            if (jobDetail == null) {
                log.error("Failed to find timer with ID: {}", timerId);
                return null;
            }
            return (TimerConfigDto) jobDetail.getJobDataMap().get(timerId);
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public void updateTimer(final String timerId, final TimerConfigDto config) {
        try {
            final JobDetail jobDetail = scheduler.getJobDetail(new JobKey(timerId));
            if (jobDetail == null) {
                log.error("Failed to find timer with ID: {}", timerId);
                return;
            }
            jobDetail.getJobDataMap().put(timerId, config);
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
        }
    }

    public Boolean deleteTimer(final String timerId) {
        try {
            return scheduler.deleteJob(new JobKey(timerId));
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    @PostConstruct
    public void init() {
        try {
            scheduler.start();
            scheduler.getListenerManager().addTriggerListener(new SimpleTriggerListener(this));
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
        }
    }

    @PreDestroy
    public void shutDown() {
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
        }
    }
}
