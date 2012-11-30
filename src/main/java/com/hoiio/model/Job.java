package com.hoiio.model;

import java.util.Date;

public class Job {
    public static enum Type {
        SMS, CALL, CONFERENCE;
    }

    private Type type;
    private String from;
    private String to;
    private Date date;

    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }
    public String getFrom() {
        return from;
    }
    public void setFrom(String from) {
        this.from = from;
    }
    public String getTo() {
        return to;
    }
    public void setTo(String to) {
        this.to = to;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
}
