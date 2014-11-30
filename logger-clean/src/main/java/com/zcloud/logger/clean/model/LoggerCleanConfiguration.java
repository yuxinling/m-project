package com.zcloud.logger.clean.model;

/**
 * User: yuyangning
 * Date: 5/29/14
 * Time: 6:24 PM
 */
public class LoggerCleanConfiguration {

    private String[] address;

    private int reserveDays;

    private int timeOut = 120000;

    private String[] perffix;

    public String[] getAddress() {
        return address;
    }

    public void setAddress(String[] address) {
        this.address = address;
    }

    public int getReserveDays() {
        return reserveDays;
    }

    public void setReserveDays(int reserveDays) {
        this.reserveDays = reserveDays;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public String[] getPerffix() {
        return perffix;
    }

    public void setPerffix(String[] perffix) {
        this.perffix = perffix;
    }
}
