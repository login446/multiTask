package com.alex.multitask.taskscheduler;

/**
 * Created by alex on 30.10.2016.
 */
public enum StatusTask {
    NEW("new"), WORK("work"), MADE("made");

    private final String status;

    StatusTask(String status){
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
