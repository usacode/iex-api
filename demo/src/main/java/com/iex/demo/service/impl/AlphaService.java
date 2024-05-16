package com.iex.demo.service.impl;

import com.iex.demo.exception.ServerRequestException;
import com.iex.demo.service.IAlphaService;
import com.iex.demo.util.HelperCalculator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author Paul Ngouabeu
 * This service handles the operation of Alpha calculation
 */
@Service
public class AlphaService implements IAlphaService {

    private final IexService iexService;

    public  AlphaService(IexService iexService){
        this.iexService=iexService;
    }

    /**
     * This method calculates the alpha for a given symbol and benchmark within a specified date range.
     * @param symbol - Ticker symbol.
     * @param benchmark - Ticker symbol of the benchmark.
     * @param from - Start date of the date range.
     * @param to - End date of the date range.
     * @return The calculated alpha.
     */
    @Override
    public Map<String, Object> calculateAlpha(String symbol, String benchmark, LocalDate from, LocalDate to) {
        // Fetch historical prices for the symbol and benchmark asynchronously
        CompletableFuture<Map<LocalDate, Double>> stockPricesFuture = CompletableFuture.supplyAsync(() ->
                iexService.getHistoricalPrices(symbol, from, to));

        CompletableFuture<Map<LocalDate, Double>> benchmarkPricesFuture = CompletableFuture.supplyAsync(() ->
                iexService.getHistoricalPrices(benchmark, from, to));

        try {
            // Wait for both futures to complete
            Map<LocalDate, Double> stockPrices = stockPricesFuture.get();
            Map<LocalDate, Double> benchmarkPrices = benchmarkPricesFuture.get();

            // Calculate average returns for the symbol and benchmark
            double stockAvgReturn = HelperCalculator.calculateAverageReturn(stockPrices);
            double benchmarkAvgReturn = HelperCalculator.calculateAverageReturn(benchmarkPrices);

            // Calculate alpha
            double alpha = stockAvgReturn - benchmarkAvgReturn;

            // Prepare the result
            Map<String, Object> alphaData = new HashMap<>();
            alphaData.put("symbol", symbol);
            alphaData.put("benchmark", benchmark);
            alphaData.put("alpha", alpha);

            // Return the calculated alpha
            return alphaData;
        } catch (InterruptedException | ExecutionException e) {
            // Handle exceptions appropriately
            throw new ServerRequestException(HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getMessage());
        }
    }
}
