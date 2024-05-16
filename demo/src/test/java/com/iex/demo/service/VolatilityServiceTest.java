package com.iex.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


import com.iex.demo.service.impl.IexService;
import com.iex.demo.service.impl.VolatilityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class VolatilityServiceTest {

    @Mock
    private IexService iexService;

    private VolatilityService volatilityService;

    @BeforeEach
    public void setup() {
        volatilityService = new VolatilityService(iexService);
    }

    @Test
    public void testCalculateVolatility() {
        // Mock data
        String symbol = "AAPL";
        LocalDate from = LocalDate.of(2022, 1, 1);
        LocalDate to = LocalDate.of(2022, 1, 10);
        Map<LocalDate, Double> historicalPrices = new HashMap<>();
        historicalPrices.put(LocalDate.of(2022, 1, 1), 100.0);
        historicalPrices.put(LocalDate.of(2022, 1, 2), 101.0);
        // Mocking behavior
        when(iexService.getHistoricalPrices(symbol, from, to)).thenReturn(historicalPrices);

        // Calling the method under test
        List<Map<String, Object>> volatilityDataList = volatilityService.calculateVolatility(symbol, from, to);

        // Verifying behavior
        verify(iexService).getHistoricalPrices(symbol, from, to);
        assertEquals(1, volatilityDataList.size());
    }
}