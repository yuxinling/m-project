package com.zcloud.logger.clean;

import com.google.common.collect.Lists;
import com.zcloud.logger.clean.model.IndexItem;
import com.zcloud.logger.clean.utils.Jsons;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * User: yuyangning
 * Date: 5/29/14
 * Time: 11:21 AM
 */
public class ElasticIndexManager {

    private static final Logger logger = LoggerFactory.getLogger(ElasticIndexManager.class);

    public final HttpClient httpClient;

    public ElasticIndexManager(HttpClientManager httpClientManager) {
        this.httpClient = httpClientManager.getClient();
    }

    private List<String> getIndexs(final String address) {

        try {

            String url = address + "/_aliases?pretty=1";
            HttpGet httpGet = new HttpGet(url);

            return httpClient.execute(httpGet, new ResponseHandler<List<String>>() {

                @Override
                public List<String> handleResponse(HttpResponse response) throws IOException {

                    if (response.getStatusLine().getStatusCode() == 200) {
                        String result = EntityUtils.toString(response.getEntity());
                        Map<String, Object> data = Jsons.objectFromJSONStr(result, Map.class);
                        if (data != null) {
                            List<String> indexs = Lists.newArrayList();
                            for (String key : data.keySet()) {
                                indexs.add(key);
                            }

                            logger.debug("Get From Host[{}] Indexs [{}] .", address, indexs);
                            return indexs;
                        }
                    }
                    return null;
                }
            });

        } catch (Exception e) {
            logger.error("Get for all Index error.");
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public List<IndexItem> getIndexItem(String address, List<IndexItem> items) {

        List<String> indexs = getIndexs(address);
        if (items == null || items.size() == 0) return null;

        if (indexs != null && indexs.size() > 0) {

            List<IndexItem> outs = Lists.newArrayList();
            for (String index : indexs) {
                for (IndexItem item : items) {
                    if (index.startsWith(item.getPerffix())
                            && index.trim().length() == item.getPerffix().trim().length() + 10) {

                        logger.info("Get Host[{}] index [{}] for perffix [{}] .", address, index, item.getPerffix());
                        IndexItem iitem = new IndexItem();
                        iitem.setDays(item.getDays());
                        iitem.setIndex(index);
                        iitem.setPerffix(item.getIndex());

                        outs.add(iitem);
                        continue;
                    }
                }
            }

            return outs;
        }
        return null;
    }

    public void deleteIndex(final String address, final String indexName) {

        try {

            boolean exist = isExist(address, indexName);
            if (!exist) {
                logger.debug("Not exist index [{}] in Host[{}]", indexName, address);
                return;
            }

            final String url = address + "/" + indexName;
            logger.debug("Start delete [{}] ", url);

            HttpDelete delete = new HttpDelete(url);
            httpClient.execute(delete, new ResponseHandler<Void>() {

                @Override
                public Void handleResponse(HttpResponse response) throws IOException {
                    String result = EntityUtils.toString(response.getEntity());
                    if (response.getStatusLine().getStatusCode() == 200) {
                        Map data = Jsons.objectFromJSONStr(result, Map.class);  //"ok":true, "acknowledged":true  //V 1.2 -> {acknowledged=true}
                        if (data != null) {
                            //Boolean ok = (Boolean) data.get("ok");
                            Boolean acknowledged = (Boolean) data.get("acknowledged");
                            if (acknowledged) {
                                logger.debug("Delete [{}] success from Host[{}].", url, address);
                                return null;
                            }
                        }
                    }

                    logger.error("Delete Index [{}] error from Host[{}],reason:{}", indexName, address, result);
                    return null;
                }
            });

        } catch (Exception e) {
            logger.error("Delete Index [{}] error from Host[{}].", indexName, address);
            logger.error(e.getMessage(), e);
        }
    }

    public boolean isExist(String address, String indexName) {
        try {
            String url = address + "/" + indexName;
            HttpHead httpHead = new HttpHead(url);
            return httpClient.execute(httpHead, new ResponseHandler<Boolean>() {
                @Override
                public Boolean handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
                    int statusCode = response.getStatusLine().getStatusCode();
                    if (statusCode == 200) {
                        return true;
                    }
                    return false;
                }
            });

        } catch (IOException e) {
            logger.error("Check exist Index [{}] error in Host[{}].", indexName, address);
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    public void createIndex(final String address, final String indexName) {
        try {

            String url = address + "/" + indexName;

            HttpPost post = new HttpPost(url);

            httpClient.execute(post, new ResponseHandler<Void>() {
                @Override
                public Void handleResponse(HttpResponse response) throws IOException {
                    int statusCode = response.getStatusLine().getStatusCode();
                    if (statusCode == 200) {
                        logger.info("Cretae Index [{}] success in Host[{}].", indexName, address);
                    } else {
                        logger.error("Cretae Index [{}] error in Host[{}] reason: {}.", indexName, address, EntityUtils.toString(response.getEntity()));
                    }
                    return null;
                }
            });
        } catch (IOException e) {
            logger.error("Cretae Index [{}] error in Host[{}].", indexName, address);
            logger.error(e.getMessage(), e);
        }
    }
}
