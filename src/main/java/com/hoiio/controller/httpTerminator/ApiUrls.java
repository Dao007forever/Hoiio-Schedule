package com.hoiio.controller.httpTerminator;

public class ApiUrls {

    // public static String DOMAIN = "http://uat.hoiio.com/";
    public static String DOMAIN             = "https://secure.hoiio.com/";

    // public static String DOMAIN = "http://live-uat.hoiio.com/";
    public static String BASE_URL           = DOMAIN + "public/";

    // Connect Urls
    public static String CONNECT_TOKEN      = DOMAIN + "open/connect/get_token";

    // Profile Urls
    public static String GET_PROFILE        = DOMAIN + "open/user/get_info";

    // Fax Urls
    public static String GET_HISTORY        = DOMAIN + "open/fax/get_history";
    public static String SEND_FAX           = DOMAIN + "open/fax/send";

    // Number Urls
    public static String SUBSCRIBED_NUMBERS = DOMAIN + "open/number/get_active";
    public static String CONFIGURE_FORWARD  = DOMAIN
                                                    + "open/number/update_forwarding";
}