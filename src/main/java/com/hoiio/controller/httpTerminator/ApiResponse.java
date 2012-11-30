package com.hoiio.controller.httpTerminator;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
/***
 * This class encapsulates and parses a HTTP Response String
 * @author Max Tan
 */
public class ApiResponse {

    public static String OUT_STATUS = "status";
    ResponseMap params;
    String responseStr;

    /***
     * Creates an API response that is generated after submitted as an API call.
     * @param responseStr
     */
    public ApiResponse(String responseStr) {
        params = new ResponseMap();
        this.responseStr = responseStr;
        //System.out.println("Setting response " +responseStr);
        setResponse(responseStr);
    }

    public String getStatus()
    {
        return getStringParam(OUT_STATUS);
    }

    public boolean isInvalidAccessToken()
    {
        return getStatus() != null && getStatus().equalsIgnoreCase("error_invalid_access_token");
    }

    /***
     * Checks if the status returned by the API response is OK
     * @return true if response indicates a success
     */
    public boolean isOK() {
        if (getStatus() == null) {
            return false;
        } else {
            return getStatus().equalsIgnoreCase("ok") || getStatus().equalsIgnoreCase("success_ok");
        }
    }

    public String getResponseString()
    {
        return responseStr;
    }
    /***
     * Gets a parameter value returned by the API call.
     * @param key Parameter name to get.
     * @return Parameter value of specified parameter as a string.
     */
    public String getStringParam(String key) {
        return params.getString(key);
    }

    /***
     * Gets a parameter value returned by the API call.
     * @param key Parameter name to get.
     * @return Parameter value of specified parameter as an integer value.
     */
    public int getIntegerParam(String key) {
        return params.getInt(key);
    }

    /***
     * Gets a parameter value returned by the API call.
     * @param key Parameter name to get.
     * @return Parameter value of specified parameter as a double value.
     */
    public double getDoubleParam(String key) {
        return params.getDouble(key);
    }

    /***
     * Gets a parameter value returned by the API call.
     * @param key Parameter name to get.
     * @return Parameter value of specified parameter as a boolean value.
     */
    public boolean getBooleanParam(String key) {
        return params.getBool(key);
    }

    public ResponseMap[] getArray(String key) {
        return params.getArray(key);
    }

    public String[] getValues(String key) {
        return params.getValues(key);
    }

    /***
     * Parses the http response string into a map of name-value pairs.
     * @param inputString The input string to parse.
     */
    public void setResponse(final String inputString) {
        if (inputString == null || inputString.trim().equals("")) {
            return;
        }

        //Checks if it is a json request
        try {
            //System.out.println("Processing json string " + inputString);
            JSONObject output = JSONObject.fromObject(inputString);
            params.clear();
            processJSON(params, output);

        } catch (JSONException je) {
            //je.printStackTrace();

            //Possibly name value pair
            final StringTokenizer st = new StringTokenizer(inputString, "&;");

            while (st.hasMoreTokens()) {
                final String s = st.nextToken();
                final String[] nvp = s.split("=", 2);
                try {
                    params.put(nvp[0], URLDecoder.decode(String.valueOf(nvp[1]), "UTF-8"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /***
     * Currently, this can only process simple JSON strings which does not include
     * Json arrays
     * @param container The map to put the key-value pairs
     * @param obj The Json object that represents the response string
     */
    @SuppressWarnings("rawtypes")
    private void processJSON(ResponseMap container, JSONObject obj) {
        Iterator iter = obj.keys();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            try {

                JSONArray arr = obj.getJSONArray(key);
                ResponseMap items[] = new ResponseMap[arr.size()];
                String items2[] = new String[arr.size()];
                boolean objMap = true;
                for (int i = 0; i < arr.size(); i++) {
                    if (arr.get(i) instanceof String) {
                        objMap = false;
                        items2[i] = arr.getString(i);
                    }
                    else {
                        items[i] = new ResponseMap();
                        JSONObject obj2 = arr.getJSONObject(i);
                        processJSON(items[i], obj2);
                    }
                }
                if(objMap) {
                    container.put(key, items);
                } else {
                    container.put(key, items2);
                }
            } catch (JSONException e) {
                container.put(key, obj.getString(key));
            }
        }
    }

    public static class ResponseMap extends HashMap<String, Object> {

        /**
         *
         */
        private static final long serialVersionUID = 4821741251242643924L;

        public String getString(String key) {
            return (String) super.get(key);
        }

        public double getDouble(String key) {
            return Double.parseDouble(getString(key));
        }

        public int getInt(String key) {
            return Integer.parseInt(getString(key));
        }

        public boolean getBool(String key) {
            return Boolean.parseBoolean(getString(key));
        }

        public ResponseMap[] getArray(String key) {
            return (ResponseMap[]) super.get(key);
        }

        public String[] getValues(String key) {

            if(!(super.get(key) instanceof String[]))
            {
                return new String[]{};
            } else {
                return (String[]) super.get(key);
            }
        }
    }
}