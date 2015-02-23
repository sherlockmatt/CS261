package com.cs261.main;

import java.util.Date;

public class Query {

    private String uuid;
    private String x;
    private String y;
    private Date date;
    private int time;

    public Query(String uuid, String x, String y, Date date, int time) {
        this.uuid = uuid;
        this.x = x;
        this.y = y;
        this.setDate(date);
        this.setTime(time);
    }

    public String getUUID() {
        return this.uuid;
    }
    //N.B. There is no setUUID() method, as this mustn't change

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        Date currentDate = new Date();
        Date cutoffDate = new Date(currentDate.getTime() - 14 * 24 * 3600 * 1000);
        if (date.before(cutoffDate)) { //Simple clamp
            this.date = cutoffDate;
        } else if (date.after(currentDate)) {
            this.date = currentDate;
        } else {
            this.date = date;
        }
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        if (time < 1) { //Simple clamp
            this.time = 1;
        } else if (time > 23) {
            this.time = 23;
        } else {
            this.time = time;
        }
    }
}
