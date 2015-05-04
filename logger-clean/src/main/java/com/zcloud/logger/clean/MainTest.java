package com.zcloud.logger.clean;

import com.zcloud.logger.clean.model.LoggerCleanConfiguration;
import com.zcloud.logger.clean.model.LoggerCleanConfigurationYaml;
import com.zcloud.logger.clean.utils.CommonUtils;
import com.zcloud.logger.clean.utils.Jsons;

/**
 * User: yuyangning
 * Date: 9/10/14
 * Time: 10:01 AM
 */
public class MainTest {


    public static void main(String[] args) {
//        ElasticIndexManager manager = new ElasticIndexManager(HttpClientManager.getInstance());
//
//        String perffix_1 = "logstash-zcloud-";
//        String perffix_2 = "logstash-nginx-";
//        String address = "http://localhost:9200";
//        int day = 20;
//
//        for (int i = 0; i < day; i++) {
//            String dateString = CommonUtils.dateString(i);
//            System.out.println(dateString);
//
//            manager.createIndex(address, perffix_1 + dateString);
//        }
//        for (int i = 0; i < day; i++) {
//            String dateString = CommonUtils.dateString(i);
//            System.out.println(dateString);
//
//            manager.createIndex(address, perffix_2 + dateString);
//        }

        LoggerCleanConfiguration cleanConfiguration = new LoggerCleanConfigurationYaml("logger-clean.yml");
        System.out.println(Jsons.objectToJSONStr(cleanConfiguration));
    }
}
