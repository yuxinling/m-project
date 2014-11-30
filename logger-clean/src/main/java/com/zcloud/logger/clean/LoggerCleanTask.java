package com.zcloud.logger.clean;

import com.zcloud.logger.clean.model.LoggerCleanConfiguration;
import com.zcloud.logger.clean.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * User: yuyangning
 * Date: 9/9/14
 * Time: 6:15 PM
 */
public class LoggerCleanTask extends TimerTask {
    private static final Logger logger = LoggerFactory.getLogger(LoggerCleanTask.class);

    private LoggerCleanConfiguration cleanConfig;
    final ElasticIndexManager elasticManager = new ElasticIndexManager(HttpClientManager.getInstance());

    public LoggerCleanTask(LoggerCleanConfiguration cleanConfig) {
        this.cleanConfig = cleanConfig;
    }

    public void start() {
        deleteHistory();

        Timer timer = new Timer();
        timer.schedule(this, 0, 1000 * 3600 * 24);
        //timer.schedule(this, 0, 1000 * 10);
    }

    public void deleteHistory() {
        for (String address : cleanConfig.getAddress()) {
            List<String> indexs = elasticManager.getIndexsForPerffix(address, cleanConfig.getPerffix());

            if (indexs == null || indexs.size() == 0) continue;
            for (String index : indexs) {

                String dateString = index.substring(index.lastIndexOf("-") + 1, index.length());
                //logger.info("Date String : " + dateString);
                if (CommonUtils.isNotIn(cleanConfig.getReserveDays(), dateString)) {

                    try {
                        elasticManager.deleteIndex(address, index);
                        Thread.sleep(cleanConfig.getTimeOut());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void run() {
        String dateString = CommonUtils.dateString(cleanConfig.getReserveDays());

        for (String perffix : cleanConfig.getPerffix()) {

            for (String address : cleanConfig.getAddress()) {
                elasticManager.deleteIndex(address, perffix + dateString);
            }

            try {
                Thread.sleep(cleanConfig.getTimeOut());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
