package com.hoiio.controller.httpTerminator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***
 * This class allows a user to perform HTTP requests
 * @author Max Tan
 */
public class HttpUtil {

    public static final String IN_APP_ID = "appId";
    public static final String IN_APP_VERSION = "appVersion";
    public static final String IN_IP_ADDRESS = "ipAddress";
    public static final String OUT_STATUS = "status";
    public static final String OUT_ERROR_CODE = "errorCode";
    public static final String STATUS_FAILED = "fail";
    public static final String STATUS_OK = "OK";
    private static final String APP_ID_WEB = "web";
    private static final String APP_VERSION = "1.0.0";
    private static Logger log = LoggerFactory.getLogger(HttpUtil.class);

    public static ApiResponse doHttpPost(ApiRequest request) {

        String str = doHttpPostString(request);
        log.info(request.getRequestUrl()+"?"+request.getPostString());

        //System.out.println(str);
        return new ApiResponse(str);
    }

    public static String doHttpPostString(ApiRequest request) {

        if(request.getParam(IN_APP_ID) == null) {
            request.addParam(IN_APP_ID, APP_ID_WEB);
        }
        if(request.getParam(IN_APP_VERSION) == null) {
            request.addParam(IN_APP_VERSION, APP_VERSION);
        }
        if(request.getParam(IN_IP_ADDRESS) == null) {
            request.addParam(IN_IP_ADDRESS, getIp());
        }

        //log.info(request.getPostString());

        String urlString = request.getRequestUrl();
        String content = request.getPostString();

        //log.debug("Request to " + urlString + " " + content);
        String output = "";
        HttpURLConnection urlConn = null;
        if (!urlString.toLowerCase().startsWith("http://") && !urlString.toLowerCase().startsWith("https://")) {
            urlString = "http://" + urlString;
        }
        try {
            URL url = new URL(urlString);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setDoInput(true);
            urlConn.setDoOutput(true);
            urlConn.setUseCaches(false);
            urlConn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded; charset=UTF-8");
            output = doHttpURLConnectionPost(urlConn, content);
            log.debug("Response " + output);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConn != null) {
                urlConn.disconnect();
            }
        }
        return output;
    }

    private static String doHttpURLConnectionPost(HttpURLConnection con, String content) {
        String output = "";

        BufferedReader br = null;
        InputStream is = null;
        OutputStream os = null;

        try {
            os = con.getOutputStream();
            os.write(content.getBytes("UTF-8"));
            os.flush();

            boolean error = false;
            if (con.getResponseCode() >= 400) {
                error = true;
                log.error("Response: " + con.getResponseCode() + " - "
                        + con.getResponseMessage());
                is = con.getErrorStream();
            } else {
                is = con.getInputStream();
            }

            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            StringBuilder sb = new StringBuilder();
            String str;
            while (null != ((str = br.readLine()))) {
                sb.append(str);
            }
            output = sb.toString();

            if (!error) {
                // log.debug("Output: {}", output);
            } else {
                log.debug("Error: " + output);
            }
        } catch (java.net.MalformedURLException e) {
            e.printStackTrace();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return output;
    }

    private static String getIp() {
        return "123.123.123.123";
    }
    TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {

        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[0];
        }

        public void checkClientTrusted(
                java.security.cert.X509Certificate[] certs, String authType) {
        }

        public void checkServerTrusted(
                java.security.cert.X509Certificate[] certs, String authType) {
        }
    }};
}

