package com.iex.demo.service.impl;


import com.iex.demo.service.IReturnService;
import com.iex.demo.util.HelperCalculator;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * @author Paul Ngouabeu
 * This service handles the operation of Return calculation
 */
@Service
public class ReturnService implements IReturnService {

    private final IexService iexService;


    public ReturnService(IexService iexService){
        this.iexService=iexService;
    }

    /**
     * This method calculates the daily return for a given symbol within a specified date range.
     * @param symbol - ticker symbol
     * @param from - start date
     * @param to - end date
     * @return It returns the daily return.
     */
    @Override
    public List<Map<String, Object>> calculateReturns(String symbol, LocalDate from, LocalDate to) {
        Map<LocalDate, Double> historicalPrices = iexService.getHistoricalPrices(symbol, from, to);
       return HelperCalculator.calculateDailyReturns(symbol, historicalPrices, from, to);
    }

}
