package com.hoiio.controller.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hoiio.controller.httpTerminator.ApiConstants;
import com.hoiio.controller.httpTerminator.ApiRequest;
import com.hoiio.controller.httpTerminator.ApiResponse;
import com.hoiio.controller.httpTerminator.ApiUrls;
import com.hoiio.controller.httpTerminator.HttpUtil;


public class ConnectApi {

    static Logger log = LoggerFactory.getLogger(ConnectApi.class);
    private static String IN_CODE = "code";
    private static String IN_APP_SECRET = "3jO5gTIPcjht88BR";
    private static String IN_APP_ID = "YALI5o4R6mIQLd7v";

    private static String PARAM_TOKEN = "access_token";

    public static String getToken(String code) {
        ApiRequest request = new ApiRequest(ApiUrls.CONNECT_TOKEN);

        request.addParam(IN_CODE, code);
        request.addParam(IN_APP_SECRET, ApiConstants.APP_SECRET);
        request.addParam(IN_APP_ID, ApiConstants.APP_ID);

        ApiResponse response = HttpUtil.doHttpPost(request);
        String token = response.getStringParam(PARAM_TOKEN);
        log.info("Token is " + token);
        if(token == null) {
            log.info("Error with getting token for appid: " + ApiConstants.APP_ID + " Server response: " + response.getResponseString());
            if(response.getStringParam("status").equals("error_code_expired")) {
                log.info("Code has expired");
            } else if(response.getStringParam("status").equals("error_invalid_app_id")) {
                log.info("Invalid App id." + " " + ApiConstants.APP_ID);
            }
        }
        return token;
    }
}
