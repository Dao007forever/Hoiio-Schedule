package com.hoiio.controller.httpTerminator;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/***
 * This object encapsulates a HTTP Request
 * @author Max Tan
 */
public class ApiRequest {
    String requestUrl;
    Map<String, String> params;
    /***
     * Creates an API request which contains information for the RPC URL and parameters.
     * @param requestUrl
     */
    public ApiRequest(String requestUrl) {
        super();
        params = new HashMap<String, String>();
        this.requestUrl = requestUrl;
    }


    /***
     * This method allows a user to add request parameters and their corresponding value.
     * If a param already exists, then it will be overridden by the new value specified in the method call.
     * @param param The parameter name.
     * @param value The value of the parameter.
     */
    public void addParam(String param, String value)
    {
        params.put(param, value);
    }

        /***
         * This method allows a user to retrieve an added parameter's value
         * @param param The parameter name
         */
        public String getParam(String param)
        {
            return params.get(param);
        }

    /***
     * Gets the http post string of the api request.
     * @return The http post string.
     */
    protected String getPostString() {
        if (params != null) {
            StringBuilder content = new StringBuilder();

            for (Entry<String, String> item : params.entrySet()) {
                if (content.length() > 0) {
                    content.append("&");
                }

                try {
                    content.append(item.getKey()
                            + "="
                            + URLEncoder.encode(
                                    String.valueOf(item.getValue()), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            return content.toString();
        } else {
            return "";
        }
    }


    /***
     * Gets the request URL of the request
     * @return The request URL.
     */
    public String getRequestUrl() {
        return requestUrl;
    }

}
