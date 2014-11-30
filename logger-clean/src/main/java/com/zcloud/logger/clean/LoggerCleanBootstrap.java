package com.zcloud.logger.clean;

import com.zcloud.logger.clean.model.LoggerCleanConfiguration;
import com.zcloud.logger.clean.model.LoggerCleanConfigurationYaml;
import com.zcloud.logger.clean.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * User: yuyangning
 * Date: 5/29/14
 * Time: 9:26 AM
 */
public class LoggerCleanBootstrap {
  private static final Logger logger = LoggerFactory.getLogger(LoggerCleanBootstrap.class);

  private final static String address = "http://localhost:9200";
  private final static int reserveDays = 7;
  private final static String perffix = "logstash-";    //"logstash-2014.05.10";
  private final static int timeout = 1000 * 60 * 3;


  public static void main(String[] args) {

    logger.info("Loggger cleaner starting...");

    LoggerCleanConfiguration cleanConfig;
    //args = new String[1];
    //args[0] = "logger-clean.yml";

    if (args != null && args.length > 0) {

      //config = "logger-clean.yml";
      String config = args[0].trim();
      logger.info("Load the config from {} .", config);
      cleanConfig = new LoggerCleanConfigurationYaml(config);


    } else {

      logger.info("Using the default config.");
      cleanConfig = new LoggerCleanConfiguration();


      cleanConfig.setAddress(new String[]{address});
      cleanConfig.setReserveDays(reserveDays);
      cleanConfig.setPerffix(new String[]{perffix});
      cleanConfig.setTimeOut(timeout);
    }

    LoggerCleanTask task = new LoggerCleanTask(cleanConfig);
    task.start();

    logger.info("Logger clean task started.");
  }
}
