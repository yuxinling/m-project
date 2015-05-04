package com.zcloud.logger.clean.model;

import java.util.List;

/**
 * User: yuyangning
 * Date: 5/29/14
 * Time: 6:24 PM
 */
public class LoggerCleanConfiguration {

    private String[] address;
    private List<IndexItem> items;

    private int timeOut = 120000;


    public String[] getAddress() {
        return address;
    }

    public void setAddress(String[] address) {
        this.address = address;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public List<IndexItem> getItems() {
        return items;
    }

    public void setItems(List<IndexItem> items) {
        this.items = items;
    }
}
