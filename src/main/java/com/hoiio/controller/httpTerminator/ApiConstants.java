package com.hoiio.controller.httpTerminator;

/**
*
* This file contains important constants and keys. Do NOT change any of these strings
* or you will break the fax app.
* @author Max Tan
*/
public class ApiConstants {

   //Production server (LIVE)
   public static final String APP_ID = "1FXekqSRnDZ5p5hm";
   public static final String APP_SECRET = "b7s0o32mSj4A7zPS";

   //AMAZON SES
   public static final String AMAZON_FROM_EMAIL = "noreply@hoiio.com";
   public static final String AMAZON_ACCESS_KEY = "AKIAJ4TBCVC26DHBYSSA";
   public static final String AMAZON_SECRET_KEY = "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";

   //Incoming Fax forwarding Url
   public static final String FAX_FORWARDING_URL = "https://secure.hoiio.com/apps/fax/IncomingFax";
}