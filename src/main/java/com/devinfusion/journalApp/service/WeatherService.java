package com.devinfusion.journalApp.service;

import com.devinfusion.journalApp.cache.AppCache;
import com.devinfusion.journalApp.constants.Placeholders;
import com.devinfusion.journalApp.entity.WeatherResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class WeatherService {
    @Value("${weather.api.key}")
    private String apiKey;
    private String API;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    public WeatherResponse getWeather(String city){
        log.info("Called weather ");
        String finalApi = appCache.APP_CACHE.get(AppCache.keys.WEATHER_API.toString()).replace(Placeholders.CITY, city).replace(Placeholders.API_KEY , apiKey);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherResponse.class);
        return response.getBody();
    }

}
