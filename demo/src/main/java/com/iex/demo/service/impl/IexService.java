package com.iex.demo.service.impl;

import com.iex.demo.config.ServiceConfig;
import com.iex.demo.service.IIexService;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;

/**
 * @author Paul Ngouabeu
 * This service makes the external request to IEX API.
 */
@Component
public class IexService implements IIexService {


    private final ServiceConfig serviceConfig;
    private final RestTemplate restTemplate;


    public IexService(ServiceConfig serviceConfig, RestTemplate restTemplate) {
        this.serviceConfig = serviceConfig;
        this.restTemplate = restTemplate;
    }

    /**
     * This method requests the historical prices for given symbol,start date and end date.
     * @param symbol - ticker symbol
     * @param from - start date
     * @param to - end date
     * @return It returns key/pair of historical prices.
     */
    @Override
    public Map<LocalDate, Double> getHistoricalPrices(String symbol, LocalDate from, LocalDate to) {
        String url = String.format("%s/%s", serviceConfig.iexUrl, symbol);
        String queryParams = String.format("?token=%s&from=%s&to=%s", serviceConfig.iexToken,from,to);
        String fullUrl = url + queryParams;
        Map[] response = restTemplate.getForObject(fullUrl, Map[].class);

        Map<LocalDate, Double> historicalPrices = new HashMap<>();
        if (response != null) {
            for (Map<String, Object> entry : response) {
                LocalDate date = LocalDate.parse(entry.get("priceDate").toString());
                double closePrice = Double.parseDouble(entry.get("close").toString());
                historicalPrices.put(date, closePrice);
            }
        }
        return historicalPrices;
    }


}
