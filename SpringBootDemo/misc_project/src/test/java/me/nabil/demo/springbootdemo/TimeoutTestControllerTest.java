package me.nabil.demo.springbootdemo;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketTimeoutException;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 测试HttpClient超时参数
 *
 * @author nabilzhang
 */
public class TimeoutTestControllerTest {

    /**
     * 1.connectionTimeout测试：IP无法链接，链接超时
     *
     * @throws Exception
     */
    @Test
    public void connectionTimeout() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://74.125.203.100");
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(1000)
                .setSocketTimeout(1000).setConnectTimeout(1000).build();
        httpGet.setConfig(requestConfig);
        try {
            httpclient.execute(httpGet);
        } catch (ConnectTimeoutException exception) {
            Assert.assertTrue(exception.getMessage().contains("connect timed out"));
        }

    }

    /**
     * 2.socketTimeout测试，服务端没有指定时间内任何响应，会超时
     *
     * @throws Exception
     */
    @Test
    public void socketTimeout() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://127.0.0.1:8080/test/socket_timeout");
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(1000).build();
        httpGet.setConfig(requestConfig);
        try {
            httpclient.execute(httpGet);
        } catch (SocketTimeoutException exception) {
            Assert.assertEquals("Read timed out", exception.getMessage());
        }
    }

    /**
     * 3.socketTimeout测试：服务端隔800ms返回一点数据,不会超时
     *
     * @throws Exception
     */
    @Test
    public void socketTimeoutNo() {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://127.0.0.1:8080/test/socket_timeout_2");
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(1000)
                .setSocketTimeout(1000).setConnectTimeout(1000).build();
        httpGet.setConfig(requestConfig);

        try {
            httpclient.execute(httpGet);
            CloseableHttpResponse response = httpclient.execute(httpGet);
            System.out.println(String.format("socketTimeoutNo, %s", EntityUtils.toString(response.getEntity())));
        } catch (Exception e) {
            Assert.fail("服务端隔800ms返回一点数据,不会超时");
        }


    }

    /**
     * 3.发送数据的超时，似乎不大好测啊
     *
     * @throws Exception
     */
    @Test
    public void socketTimeoutNoForSend() {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(1000)
                .setSocketTimeout(1000).setConnectTimeout(1000).build();

        HttpPost httpPost = new HttpPost("http://127.0.0.1:8080/test/return");
        httpPost.setConfig(requestConfig);
        httpPost.setEntity(new AbstractHttpEntity() {

            @Override
            public boolean isRepeatable() {
                return true;
            }

            @Override
            public long getContentLength() {
                return "hi".length();
            }

            @Override
            public InputStream getContent() throws IOException, UnsupportedOperationException {
                try {
                    Thread.sleep(1200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return new ByteArrayInputStream("hi".getBytes());
            }

            @Override
            public void writeTo(OutputStream outstream) throws IOException {
                if (outstream == null) {
                    throw new IllegalArgumentException("Output stream may not be null");
                } else {
                    outstream.write("hi".getBytes());
                    outstream.flush();
                    try {
                        Thread.sleep(1200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public boolean isStreaming() {
                return false;
            }
        });

        try {
            CloseableHttpResponse response = httpclient.execute(httpPost);
            System.out.println(String.format("socketTimeoutNoForSend, %s", EntityUtils.toString(response.getEntity())));
        } catch (Exception e) {
           // 理论上应该超时，但是没有超时。。。
        }

    }

    /**
     * 4.connectionRequestTimeout测试:指从连接管理器(例如连接池)中拿到连接的超时时间
     *
     * @throws Exception
     */
    @Test
    public void connectionRequestTimeoutWithPoolingConnectionManager() throws Exception {
        PoolingHttpClientConnectionManager conMgr = new PoolingHttpClientConnectionManager();
        conMgr.setMaxTotal(2);

        final CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(conMgr).build();
        final HttpGet httpGet = new HttpGet("http://127.0.0.1:8080/test/connection_request_timeout");

        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(1000)
                .setConnectionRequestTimeout(1000).setSocketTimeout(1000).build();
        httpGet.setConfig(requestConfig);

        // 如下多线程占满连接池
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        CloseableHttpResponse response = httpclient.execute(httpGet);
                        System.out.println(String.format("connectionRequestTimeoutTest: %s",
                                EntityUtils.toString(response.getEntity())));
                    } catch (SocketTimeoutException exception) {
                        System.out.println(exception.getMessage());
                    } catch (ClientProtocolException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        // 在连接池占满的情况下，拿不到就会抛异常
        try {
            CloseableHttpResponse response = httpclient.execute(httpGet);
            System.out.println(String.format("connectionRequestTimeoutTest: %s",
                    EntityUtils.toString(response.getEntity())));
            Assert.fail();
        } catch (Exception exception) {
            // 异常是从连接池拿到连接超时
            Assert.assertEquals("Timeout waiting for connection from pool", exception.getMessage());
            System.out.println(exception.getMessage());
        }

    }

    /**
     * 5.connectionRequestTimeout测试，指从连接管理器中拿到连接的超时时间，由于使用基本的连接管理器，链接被占用时，直接无法分配链接
     * connectionRequestTimeout并未生效，目前看来该参数只在连接池奏效.
     * 该链接管理器(BasicHttpClientConnectionManager)是单线程情况下可以使用，多线程情况下不要使用。
     *
     * @throws Exception
     */
    @Test
    public void connectionRequestTimeoutWithBasicConnectionManager() throws Exception {

        BasicHttpClientConnectionManager connManager = new BasicHttpClientConnectionManager();
        final CloseableHttpClient httpclient = HttpClients.custom()
                .setConnectionManager(connManager).setMaxConnPerRoute(1).build();
        final HttpGet httpGet = new HttpGet("http://127.0.0.1:8080/test/connection_request_timeout");

        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(100000)
                .setConnectionRequestTimeout(1000000).setSocketTimeout(1000000).build();
        httpGet.setConfig(requestConfig);

        // 如下多线程占满连接
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    CloseableHttpResponse response = null;
                    try {
                        response = httpclient.execute(httpGet);
                        System.out.println(String.format("connectionRequestTimeoutTest: %s",
                                EntityUtils.toString(response.getEntity())));
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    } finally {
                        if (response != null) {
                            try {
                                response.close();
                                httpclient.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }
            });
        }
        System.out.println(new Date());

        // 在连接池占满的情况下，拿不到就会抛异常
        try {
            CloseableHttpResponse response = httpclient.execute(httpGet);
            System.out.println(String.format("connectionRequestTimeoutTest: %s",
                    EntityUtils.toString(response.getEntity())));
            Assert.fail();
        } catch (Exception exception) {
            System.out.println(new Date());
            exception.printStackTrace();
            // 异常是从连接池拿到连接超时
            Assert.assertEquals("Connection is still allocated", exception.getMessage());
            System.out.println(exception.getMessage());
        }

    }

}