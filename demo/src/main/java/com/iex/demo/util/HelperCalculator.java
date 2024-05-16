package com.iex.demo.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Paul Ngouabeu
 * This class handles the operations of diverse purposes.
 */
public class HelperCalculator {
    private HelperCalculator(){}

    /**
     * This method calculates the average return from historical prices.
     * @param prices - Map of historical prices.
     * @return It returns the average return.
     */
    public static double calculateAverageReturn(Map<LocalDate, Double> prices) {
        return prices.values().stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    /**
     * This method calculates the daily returns for a given symbol within a specified date range.
     * @param symbol - Ticker symbol.
     * @param historicalPrices - Map of historical prices.
     * @param from - Start date of the date range.
     * @param to - End date of the date range.
     * @return List of daily return data.
     */
    public static List<Map<String, Object>> calculateDailyReturns(String symbol, Map<LocalDate, Double> historicalPrices, LocalDate from, LocalDate to) {
        // Initialize list to store daily return data
        List<Map<String, Object>> returns = new ArrayList<>();

        // Initialize current date to start date of the date range
        LocalDate currentDate = from;

        // Iterate over each date within the date range
        while (!currentDate.isAfter(to)) {
            // Determine the next date
            LocalDate nextDate = currentDate.plusDays(1);

            // Check if historical prices contain data for the next date
            if (historicalPrices.containsKey(nextDate)) {
                // Retrieve the prices for the current and next date
                Double todayPrice = historicalPrices.get(currentDate);
                Double tomorrowPrice = historicalPrices.get(nextDate);

                // Check if prices are not null and today's price is not zero
                if (todayPrice != null && tomorrowPrice != null && !todayPrice.equals(0.0)) {
                    // Calculate the daily return
                    double dailyReturn = (tomorrowPrice - todayPrice) / todayPrice;

                    // Create a map to store daily return data
                    Map<String, Object> returnData = new HashMap<>();
                    returnData.put("symbol", symbol);
                    returnData.put("priceDate", currentDate);
                    returnData.put("dailyReturn", dailyReturn);

                    // Add daily return data to the list
                    returns.add(returnData);
                }
            }

            // Move to the next date
            currentDate = nextDate;
        }

        // Return the list of daily return data
        return returns;
    }

}
