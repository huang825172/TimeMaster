package com.zzs.timemaster.Models;

public class Arrangement {
    private final String time;
    private final String title;
    private final String subtitle;

    public Arrangement(String time, String title, String subtitle) {
        this.time = time;
        this.title = title;
        this.subtitle = subtitle;
    }

    public String getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }
}
