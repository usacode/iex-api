package com.iex.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


import com.iex.demo.service.impl.AlphaService;
import com.iex.demo.service.impl.IexService;
import com.iex.demo.util.HelperCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@ExtendWith(MockitoExtension.class)
class AlphaServiceTest {

    @Mock
    private IexService iexService;

    @InjectMocks
    private AlphaService alphaService;

    @BeforeEach
    public void setup() {
        alphaService = new AlphaService(iexService);
    }

    @Test
    public void testCalculateAlpha() throws InterruptedException, ExecutionException {
        // Mock data
        String symbol = "AAPL";
        String benchmark = "SPY";
        LocalDate from = LocalDate.of(2022, 1, 1);
        LocalDate to = LocalDate.of(2022, 1, 10);
        Map<LocalDate, Double> stockPrices = new HashMap<>();
        stockPrices.put(LocalDate.of(2022, 1, 1), 100.0);
        stockPrices.put(LocalDate.of(2022, 1, 2), 101.0);
        Map<LocalDate, Double> benchmarkPrices = new HashMap<>();
        benchmarkPrices.put(LocalDate.of(2022, 1, 1), 200.0);
        benchmarkPrices.put(LocalDate.of(2022, 1, 2), 201.0);

        // Mocking behavior
        when(iexService.getHistoricalPrices(symbol, from, to)).thenReturn(stockPrices);
        when(iexService.getHistoricalPrices(benchmark, from, to)).thenReturn(benchmarkPrices);

        // Calling the method under test
        Map<String, Object> alphaData = alphaService.calculateAlpha(symbol, benchmark, from, to);

        // Verifying behavior
        verify(iexService).getHistoricalPrices(symbol, from, to);
        verify(iexService).getHistoricalPrices(benchmark, from, to);

        double stockAvgReturn = HelperCalculator.calculateAverageReturn(stockPrices);
        double benchmarkAvgReturn = HelperCalculator.calculateAverageReturn(benchmarkPrices);
        double expectedAlpha = stockAvgReturn - benchmarkAvgReturn;

        assertEquals(symbol, alphaData.get("symbol"));
        assertEquals(benchmark, alphaData.get("benchmark"));
        assertEquals(expectedAlpha, alphaData.get("alpha"));
    }
}