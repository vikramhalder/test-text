package com.kivred.web.utility;


import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.util.Base64;

@Slf4j
@SuppressWarnings("all")
public class StringUtility {
    public static String getObjectToJSON(Object object) {
        try {
            return new Gson().toJson(object);
        } catch (Exception e) {
            log.info("ERROR: {}", e.getMessage());
        }
        return null;
    }


    public static String randomString(int len) {
        final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

    public static String base64Encode(String token) {
        return Base64.getMimeEncoder().encodeToString(token.getBytes());
    }


    public static String base64Decode(String token) {
        return new String(Base64.getMimeDecoder().decode(token));
    }

    public static String getUrlDataByHtmlUnit(String url) {
        String returnString = "";
        try {
            WebClient webClient = new WebClient(BrowserVersion.CHROME);
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setJavaScriptEnabled(false);
            returnString = ((HtmlPage) webClient.getPage(url)).asXml();
            webClient.close();
        } catch (Exception ex) {
            log.info("ERROR: {}", ex.getMessage());
        }
        return returnString;
    }

    public static String getUrlDataByString(String _url) {
        String returnString = "";
        try {
            URL url = new URL(_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                returnString = returnString + line;
            }
            rd.close();
        } catch (Exception ex) {
            log.info("ERROR: {}", ex.getMessage());
        }
        return returnString;
    }
}
