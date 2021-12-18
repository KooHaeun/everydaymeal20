package com.example.everydaymeal;

public class Alarm {
    String inDay, breakfast, lunch, dinner, useCal, alarmTime;

    public Alarm(){
    }
    public Alarm(String inDay, String breakfast, String lunch, String dinner, String useCal, String alarmTime) {

        this.inDay = inDay;
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
        this.useCal = useCal;
        this.alarmTime = alarmTime;
    }

    /*public Alarm(String inDay, String useCal, String use) {
        this.num = num;
        this.inDay = inDay;
        this.useCal = useCal;
        this.use = use;
    }*/

    public String getInDay() {
        return inDay;
    }

    public void setInDay(String inDay) {
        this.inDay = inDay;
    }

    public String getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(String breakfast) {
        this.breakfast = breakfast;
    }

    public String getLunch() {
        return lunch;
    }

    public void setLunch(String lunch) {
        this.lunch = lunch;
    }

    public String getDinner() {
        return dinner;
    }

    public void setDinner(String dinner) {
        this.dinner = dinner;
    }

    public String getUseCal() {
        return useCal;
    }

    public void setUseCal(String useCal) {
        this.useCal = useCal;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String use) {
        this.alarmTime = alarmTime;
    }
}
