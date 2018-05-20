package com.adiel.gogogicha;

/**
 * Created by hp on 29/04/2018.
 */

public class History {
    private String title;
    private String boardingTime;
    private String arrivedTime;
    private String origin;
    private String destination;
    private String cost;

    public History(String title, String boardingTime, String arrivedTime, String origin, String destination,String cost){
        this.title = title;
        this.boardingTime = boardingTime;
        this.arrivedTime = arrivedTime;
        this.origin = origin;
        this.destination = destination;
        this.cost = cost;
    }

    public String getTitle(){
        return this.title;
    }

    public String getBoardingTime(){
        return this.boardingTime;
    }

    public String getArrivedTime(){
        return this.arrivedTime;
    }

    public String getOrigin(){
        return this.origin;
    }

    public String getDestination(){
        return this.destination;
    }
}
