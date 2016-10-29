package com.alex.multitask;

/**
 * Created by alex on 29.10.2016.
 */
public enum Level {
    USER("user"), ADMIN("admin");
    private String level;

    Level(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

}
