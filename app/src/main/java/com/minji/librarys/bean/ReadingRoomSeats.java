package com.minji.librarys.bean;

/**
 * Created by user on 2016/8/22.
 */
public class ReadingRoomSeats {

    public String orderState;

    public String seatsNumber;

    public ReadingRoomSeats(String orderState, String seatsNumber) {
        this.orderState = orderState;
        this.seatsNumber = seatsNumber;
    }

    public ReadingRoomSeats() {
    }

    public String getOrderState() {
        return orderState;
    }

    public String getSeatsNumber() {
        return seatsNumber;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public void setSeatsNumber(String seatsNumber) {
        this.seatsNumber = seatsNumber;
    }
}
