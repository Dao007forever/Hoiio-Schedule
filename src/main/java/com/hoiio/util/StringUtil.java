package com.hoiio.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;

public final class StringUtil {

    private static final Pattern rfc2822 = Pattern
            .compile(
            "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$"
            );

    public static String convertListToCsv(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            if (sb.length() != 0) {
                sb.append(','); // separator
            }
            if (s != null) {
                sb.append("\"" + s.replace("\"", "\"\"") + "\""); // escape and enclose
            }
        }
        return sb.toString();
    }

    public static String stripOffPlusSign(String mobileNumber) {
        return mobileNumber.replaceAll("\\+", "");
    }

    public static Map<String, String> parseNvp(final String inputString) {
        if (inputString == null || inputString.trim().equals("")) {
            return new HashMap<String, String>();
        }

        final Map<String, String> hm = new HashMap<String, String>();

        final StringTokenizer st = new StringTokenizer(inputString, "&;");

        while (st.hasMoreTokens()) {
            final String s = st.nextToken();
            final String[] nvp = s.split("=", 2);
            try {
                hm.put(nvp[0], URLDecoder.decode(String.valueOf(nvp[1]), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
            }
        }
        return hm;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static boolean isNonEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean verifyEmail(String email) {

        if (!rfc2822.matcher(email).matches()) {
            return false;
        }
        return true;
    }

    public static JSONArray toJSONArray(String commaDelimitedString) {
        JSONArray jsonArray = new JSONArray();
        String[] stringArray = commaDelimitedString.split(",");
        for (String entry : stringArray) {
            jsonArray.add(entry);
        }

        return jsonArray;

    }

    public static String toCommaDelimitedString(JSONArray jsonArray) {
        Object[] stringArray = jsonArray.toArray();
        StringBuffer output = new StringBuffer("");

        for (Object entry : stringArray) {
            output.append(entry.toString() + ",");
        }

        if (output.toString().endsWith(",")) {
            return output.toString().substring(0,
                    output.toString().length() - 1);
        }
        return output.toString();
    }

    public static String stripPhoneNumber(String phoneNumber) {
        if (isEmpty(phoneNumber)) {
            return "";
        }

        /** Characters allowed: <space>, <brackets>, "-", "+", "," **/
        String strippedNumber = phoneNumber.replaceAll("[\\+ \\-\\(\\),]", "");

        if (isEmpty(strippedNumber)) {
            return "";
        }

        return "+" + strippedNumber;
    }

    public static String generateRandomNumericString(int length) {
        StringBuilder sb = new StringBuilder();

        while (length > 0) {
            char ch = (char) rand('0', '9');
            if (Character.isDigit(ch)) {
                sb.append(ch);
                length--;
            }
        }
        return sb.toString();
    }

    public static String generateRandomAlphaNumericString(int length) {
        StringBuilder sb = new StringBuilder();

        while (length > 0) {
            char ch = (char) rand('0', 'z');
            if (Character.isLetterOrDigit(ch)) {
                sb.append(ch);
                length--;
            }
        }
        return sb.toString();
    }

    public static int rand(int lo, int hi) {
        int n = hi - lo + 1;
        Random rn = new Random();
        int i = rn.nextInt() % n;
        if (i < 0) {
            i = -i;
        }
        return lo + i;
    }
}
