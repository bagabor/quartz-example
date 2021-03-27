package com.example.scheduler.quartz.config;

public class TimerConfigDto {
    private int totalFireCount;
    private int remainingFireCount;
    private boolean runForever;
    private long repeateTimeInMs;
    private long offsetTime;
    private String callbackData;

    public long getOffsetTime() {
        return offsetTime;
    }

    public void setOffsetTime(final long offsetTime) {
        this.offsetTime = offsetTime;
    }

    public int getTotalFireCount() {
        return totalFireCount;
    }

    public void setTotalFireCount(final int totalFireCount) {
        this.totalFireCount = totalFireCount;
    }

    public boolean isRunForever() {
        return runForever;
    }

    public void setRunForever(final boolean runForever) {
        this.runForever = runForever;
    }

    public long getRepeateTimeInMs() {
        return repeateTimeInMs;
    }

    public void setRepeateTimeInMs(final long repeateTimeInMs) {
        this.repeateTimeInMs = repeateTimeInMs;
    }

    public String getCallbackData() {
        return callbackData;
    }

    public void setCallbackData(final String callbackData) {
        this.callbackData = callbackData;
    }

    public int getRemainingFireCount() {
        return remainingFireCount;
    }

    public void setRemainingFireCount(final int remainingFireCount) {
        this.remainingFireCount = remainingFireCount;
    }
}
