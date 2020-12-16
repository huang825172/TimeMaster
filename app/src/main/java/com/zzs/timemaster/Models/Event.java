package com.zzs.timemaster.Models;

public class Event implements Comparable<Event>{
    private String event_time;
    private String event_name;
    private String event_location;

    public Event(String event_time,String event_name,String event_location){
        this.event_time=event_time;
        this.event_name=event_name;
        this.event_location=event_location;
    }
    public String getEvent_time(){
        return event_time;
    }
    public String getEvent_name(){return event_name;}
    public String getEvent_location(){return event_location;}

@Override
    public int compareTo(Event event){
        return Integer.valueOf(this.getEvent_time())-Integer.valueOf(event.getEvent_time());
    }
}
