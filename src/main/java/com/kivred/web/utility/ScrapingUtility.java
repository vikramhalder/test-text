package com.kivred.web.utility;

import com.kivred.web.db.entity.CountryVpnEntity;
import com.kivred.web.db.entity.PhoneNumberEntity;
import com.kivred.web.db.entity.SmsEntity;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.util.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
public class ScrapingUtility {
    public static List<CountryVpnEntity> fetchAllCountryVpnData(String url) {
        return fetchAllCountryVpnDataProxynova(url, null);
    }

    public static List<CountryVpnEntity> fetchAllCountryVpnDataProxynova(String url, String pType) {
        List<CountryVpnEntity> list = new ArrayList<>();
        try {
            Document doc = Jsoup.parse(StringUtility.getUrlDataByHtmlUnit(url));
            for (Element domElement : Objects.requireNonNull(doc.getElementById("tbl_proxy_list")).getElementsByTag("tbody").get(0).getElementsByTag("tr")) {
                try {
                    String ip = domElement.getElementsByTag("td").get(0).getElementsByTag("abbr").get(0).attributes().get("title").trim();
                    String port = domElement.getElementsByTag("td").get(1).text().trim();
                    String speed = domElement.getElementsByTag("td").get(3).text().trim().replace(" ms", "");
                    String type = domElement.getElementsByTag("td").get(6).text().trim();
                    String countryCode = "";
                    try {
                        countryCode = domElement.getElementsByTag("td").get(5).getElementsByTag("a").get(0).attr("href").trim();
                        countryCode = countryCode.replace("/proxy-server-list/country-", "");
                        countryCode = countryCode.replace("/", "");
                    } catch (Exception ignored) {
                    }
                    if (ip.length() >= 10) {
                        CountryVpnEntity countryVpn = new CountryVpnEntity();
                        countryVpn.setIdentity(ip + "_" + port);
                        countryVpn.setCountryCode(countryCode);
                        countryVpn.setIp(ip);
                        countryVpn.setPort(port);
                        countryVpn.setActive(true);
                        countryVpn.setSpeed(speed);
                        countryVpn.setCity("");
                        countryVpn.setType(pType == null ? "Elite" : type);
                        countryVpn.setCreated(new Date());
                        countryVpn.setUpdated(new Date());
                        list.add(countryVpn);
                    }
                } catch (Exception e) {
                    log.error("SCAPE ERROR: {}", e.getMessage());
                }
            }
        } catch (Exception e) {
            log.error("SCAPE ERROR: {}", e.getMessage());
        }
        return list;
    }

    public static List<PhoneNumberEntity> fetchAllSmsPhone() {
        List<PhoneNumberEntity> list = new ArrayList<>();
        try {
            Document doc = Jsoup.parse(StringUtility.getUrlDataByHtmlUnit("https://receive-smss.com/"));
            for (Element domElement : doc.getElementsByAttributeValueContaining("class", "number-boxes-item d-flex")) {
                try {
                    final String number = domElement.getElementsByClass("number-boxes-itemm-number").get(0).ownText();
                    final String country = domElement.getElementsByClass("number-boxes-item-country").get(0).ownText();
                    assert domElement.parent() != null;
                    final String url = "api/sms-reader/read?token=" + StringUtility.base64Encode("https://receive-smss.com" + domElement.parent().attributes().get("href"));
                    final String message = domElement.parent().attributes().get("href").contains("sms/") ? "OPEN" : "CLOSE";
                    if (number.trim().startsWith("+")) {
                        PhoneNumberEntity phoneNumber = new PhoneNumberEntity();
                        phoneNumber.setIdentity(number.trim());
                        phoneNumber.setNumber(number.trim());
                        phoneNumber.setCountry(country.trim());
                        phoneNumber.setUrl(url.trim());
                        phoneNumber.setMessage(message.trim());
                        phoneNumber.setValid(phoneNumber.getMessage().equals("OPEN"));
                        phoneNumber.setCreated(new Date());
                        phoneNumber.setUpdated(new Date());
                        list.add(phoneNumber);
                    }
                } catch (Exception e) {
                    log.error("SCAPE ERROR: {}", e.getMessage());
                }
            }
        } catch (Exception ex) {
            log.error("SCAPE ERROR: {}", ex.getMessage());
        }
        return list;
    }

    public static List<SmsEntity> fetchNumberAllSms(String id, String token) {
        List<SmsEntity> list = new ArrayList<>();
        try {
            final String htmlText = StringUtility.getUrlDataByHtmlUnit(StringUtility.base64Decode(token));
            Document doc = Jsoup.parse(htmlText);
            for (Element domElement1 : doc.getElementsByClass("list-view")) {
                for (Element domElement : domElement1.getElementsByClass("message_details")) {
                    try {
                        SmsEntity smsNumberMessage = new SmsEntity();
                        String numberF = domElement.getElementsByClass("sender").get(0).text().replaceAll("^Sender","").trim();
                        String timeMes = domElement.getElementsByClass("time").get(0).text().replaceAll("^Time","").trim();
                        String message = domElement.getElementsByClass("msg").get(0).text().replaceAll("^Message","").trim();

                        smsNumberMessage.setPhoneNumber(String.valueOf(id));
                        smsNumberMessage.setIdentity(id);
                        smsNumberMessage.setSender(numberF);
                        smsNumberMessage.setMessage(message);
                        try {
                            smsNumberMessage.setTime(timeMes.split("ago")[0] + " ago");
                        } catch (Exception ex) {
                            smsNumberMessage.setTime(message);
                        }
                        smsNumberMessage.setCreated(new Date());
                        if (!StringUtil.isBlank(message)) {
                            boolean isValid = true;
                            if (message.toLowerCase().contains("<script>")) {
                                isValid = false;
                            } else if ((message.startsWith(".") && message.endsWith("."))) {
                                isValid = false;
                            }
                            if (isValid) {
                                list.add(smsNumberMessage);
                            }
                        }
                    } catch (Exception e) {
                        log.error("SCAPE ERROR: {}", e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            log.error("SCAPE ERROR: {}", e.getMessage());
        }
        return list;
    }
}
