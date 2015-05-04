package com.zcloud.logger.clean.model;

import com.zcloud.logger.clean.common.SunYaml;

import java.util.List;

/**
 * User: yuyangning
 * Date: 9/9/14
 * Time: 5:52 PM
 */
public class LoggerCleanConfigurationYaml extends LoggerCleanConfiguration {

    private LoggerCleanConfiguration loggerCleanConfiguration;

    public LoggerCleanConfigurationYaml(String fileName) {
        SunYaml sunYaml = new SunYaml();
        loggerCleanConfiguration = sunYaml.load(LoggerCleanConfiguration.class
                , this.getClass().getClassLoader().getResourceAsStream(fileName));
    }

    @Override
    public void setTimeOut(int timeOut) {
        super.setTimeOut(timeOut);
    }

    @Override
    public int getTimeOut() {
        return loggerCleanConfiguration.getTimeOut();
    }

    @Override
    public void setAddress(String[] address) {
        loggerCleanConfiguration.setAddress(address);
    }

    @Override
    public String[] getAddress() {
        return loggerCleanConfiguration.getAddress();
    }

    @Override
    public void setItems(List<IndexItem> items) {
        loggerCleanConfiguration.setItems(items);
    }

    @Override
    public List<IndexItem> getItems() {
        return loggerCleanConfiguration.getItems();
    }
}
