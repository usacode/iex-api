package com.iex.demo.service.impl;

import com.iex.demo.service.IVolatilityService;
import com.iex.demo.util.HelperCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author Paul Ngouabeu
 * This service handles the operation on volatility
 */
@Service
public class VolatilityService implements IVolatilityService {

    private final IexService iexService;

    @Autowired
    public VolatilityService(IexService iexService){
        this.iexService=iexService;
    }

    /**
     * This method calculates the volatility for a given symbol within a specified date range.
     * @param symbol - ticker symbol
     * @param from - start date
     * @param to - end date
     * @return It returns a list of volatility data points.
     */
    @Override
    public List<Map<String, Object>> calculateVolatility(String symbol, LocalDate from, LocalDate to) {
        Map<LocalDate, Double> historicalPrices = iexService.getHistoricalPrices(symbol, from, to);
        List<Map<String, Object>> volatilityDataList = new ArrayList<>();

        // Iterate over each date range and calculate volatility
         LocalDate currentDate = from;
        while (!currentDate.isAfter(to)) {
            // Example: Calculate volatility for 30-day period
            LocalDate endDate = currentDate.plusDays(30);
            if (endDate.isAfter(to)) {
                endDate = to;
            }

            LocalDate finalCurrentDate = currentDate;
            LocalDate finalEndDate = endDate;
            Map<LocalDate, Double> pricesInRange = historicalPrices.entrySet().stream()
                    .filter(entry -> !entry.getKey().isBefore(finalCurrentDate) && !entry.getKey().isAfter(finalEndDate))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            List<Map<String, Object>> returns = HelperCalculator.calculateDailyReturns(symbol, pricesInRange, currentDate, endDate);
            double meanReturn = calculateMeanReturn(returns);
            double sumSquaredDiff = calculateSumSquaredDifference(returns, meanReturn);
            double volatility = Math.sqrt(sumSquaredDiff / returns.size());

            Map<String, Object> volatilityData = new HashMap<>();
            volatilityData.put("date", currentDate);
            volatilityData.put("volatility", volatility);
            volatilityData.put("symbol", symbol);

            volatilityDataList.add(volatilityData);

            currentDate = endDate.plusDays(1);
        }

        return volatilityDataList;
    }

    /**
     * This method calculates the mean (average) return from a list of daily returns.
     * @param returns - List of daily return data.
     * @return The mean (average) return.
     */
    private double calculateMeanReturn(List<Map<String, Object>> returns) {
        // Initialize sum of returns
        double sumReturns = 0;

        // Iterate over each daily return
        for (Map<String, Object> returnData : returns) {
            // Add daily return to sum
            sumReturns += (double) returnData.get("dailyReturn");
        }

        // Calculate and return mean return
        return sumReturns / returns.size();
    }

    /**
     * This method calculates the sum of squared differences from the mean return.
     * @param returns - List of daily return data.
     * @param meanReturn - Mean (average) return.
     * @return The sum of squared differences from the mean return.
     */
    private double calculateSumSquaredDifference(List<Map<String, Object>> returns, double meanReturn) {
        // Initialize sum of squared differences
        double sumSquaredDiff = 0;

        // Iterate over each daily return
        for (Map<String, Object> returnData : returns) {
            // Calculate the squared difference from the mean return
            double dailyReturn = (double) returnData.get("dailyReturn");
            double squaredDiff = Math.pow(dailyReturn - meanReturn, 2);

            // Add squared difference to sum
            sumSquaredDiff += squaredDiff;
        }

        // Return the sum of squared differences
        return sumSquaredDiff;
    }
}
