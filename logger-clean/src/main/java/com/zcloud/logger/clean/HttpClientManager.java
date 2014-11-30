package com.zcloud.logger.clean;


import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;

/**
 * User: yuyangning
 * Date: 5/28/14
 * Time: 6:28 PM
 */
public class HttpClientManager {
    private final HttpClient httpClient;
    private static HttpClientManager clientManager;

    private HttpClientManager() {
        ClientConnectionManager connManager = new PoolingClientConnectionManager();
        httpClient = new DefaultHttpClient(connManager);

    }

    public static HttpClientManager getInstance() {
        if (clientManager == null) {
            clientManager = new HttpClientManager();
        }
        return clientManager;
    }

    public HttpClient getClient() {
        return this.httpClient;
    }

    public void close() {
        if (this.httpClient != null) {
            this.httpClient.getConnectionManager().shutdown();
        }
    }
}
