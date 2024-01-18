package com.kivred.web.service;

import com.kivred.web.config.AppStaticConfig;
import com.kivred.web.db.CountryVpnRepository;
import com.kivred.web.db.PhoneNumberRepository;
import com.kivred.web.db.SmsRepository;
import com.kivred.web.db.entity.CountryVpnEntity;
import com.kivred.web.db.entity.PhoneNumberEntity;
import com.kivred.web.db.entity.SmsEntity;
import com.kivred.web.utility.ScrapingUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScrapeScheduleService {
    private final SmsRepository smsRepository;
    private final CountryVpnRepository countryVpnRepository;
    private final PhoneNumberRepository phoneNumberRepository;

    public void fetchVpn(boolean force) {
        if (force || AppStaticConfig.LAST_VPN_SCRAPE == null || (new Date().getTime() - AppStaticConfig.LAST_VPN_SCRAPE.getTime()) / 1000 > AppStaticConfig.FETCH_NUMBER_INTERVAL) {
            AppStaticConfig.LAST_VPN_SCRAPE = new Date();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    List<CountryVpnEntity> countryVpnList = ScrapingUtility.fetchAllCountryVpnData("http://www.proxynova.com/proxy-server-list/elite-proxies/");
                    countryVpnRepository.deleteAllByIdentityIn(countryVpnList.stream().map(CountryVpnEntity::getIdentity).collect(Collectors.toList()));
                    countryVpnRepository.saveAll(countryVpnList);
                }
            }, 1000);
        }
    }

    public List<CountryVpnEntity> getCountryVpnByCountryCode(String countryCode) {
        return ScrapingUtility.fetchAllCountryVpnDataProxynova("https://www.proxynova.com/proxy-server-list/country-" + countryCode, "all");
    }

    public void fetchNumber(boolean force) {
        if (force || AppStaticConfig.LAST_SMS_SCRAPE == null || (new Date().getTime() - AppStaticConfig.LAST_SMS_SCRAPE.getTime()) / 1000 > AppStaticConfig.FETCH_NUMBER_INTERVAL) {
            AppStaticConfig.LAST_SMS_SCRAPE = new Date();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    List<PhoneNumberEntity> phoneNumbersDB = phoneNumberRepository.findAll();
                    List<PhoneNumberEntity> phoneNumberScrape = ScrapingUtility.fetchAllSmsPhone();
                    for (PhoneNumberEntity scrape : phoneNumberScrape) {
                        boolean found = false;
                        for (PhoneNumberEntity db : phoneNumbersDB) {
                            if (scrape.getNumber().equals(db.getNumber())) {
                                db.setMessage(scrape.getMessage());
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            phoneNumbersDB.add(scrape);
                        }
                    }
                    phoneNumberRepository.saveAll(phoneNumbersDB);
                }
            }, 1000);
        }
    }

    public void fetchAllSms(PhoneNumberEntity phoneNumber, String token) {
        if ((new Date().getTime() - phoneNumber.getUpdated().getTime()) / 1000 > AppStaticConfig.NUMBER_INTERVAL) {
            phoneNumber.setHitCount(phoneNumber.getHitCount() + 1);
            phoneNumber.setUpdated(new Date());
            phoneNumberRepository.save(phoneNumber);
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    List<SmsEntity> phoneNumberMessageList = ScrapingUtility.fetchNumberAllSms(phoneNumber.getNumber(), token);
                    smsRepository.deleteAllByIdentity(phoneNumber.getNumber());
                    smsRepository.saveAll(phoneNumberMessageList);
                }
            }, 1000);
        }
    }
}
