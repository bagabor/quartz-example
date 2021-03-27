package com.example.scheduler.quartz.services;

import com.example.scheduler.quartz.config.TimerConfigDto;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;

public class SimpleTriggerListener implements TriggerListener {

    private final QuartzService quartzService;

    public SimpleTriggerListener(final QuartzService quartzService) {this.quartzService = quartzService;}

    @Override
    public String getName() {
        return SimpleTriggerListener.class.getSimpleName();
    }

    @Override
    public void triggerFired(final Trigger trigger, final JobExecutionContext jobExecutionContext) {
        final String timerId = trigger.getKey().getName();

        final JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        final TimerConfigDto timerConfigDto = (TimerConfigDto) jobDataMap.get(timerId);

        if(!timerConfigDto.isRunForever()) {
            int remainingFireCount = timerConfigDto.getRemainingFireCount();
            if(remainingFireCount == 0) {
                return;
            }
            timerConfigDto.setRemainingFireCount(remainingFireCount - 1);
        }

        quartzService.updateTimer(timerId, timerConfigDto);
    }

    @Override
    public boolean vetoJobExecution(final Trigger trigger, final JobExecutionContext jobExecutionContext) {
        return false;
    }

    @Override
    public void triggerMisfired(final Trigger trigger) {

    }

    @Override
    public void triggerComplete(final Trigger trigger, final JobExecutionContext jobExecutionContext, final Trigger.CompletedExecutionInstruction completedExecutionInstruction) {

    }
}
