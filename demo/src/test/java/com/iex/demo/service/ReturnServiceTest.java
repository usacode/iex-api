package com.iex.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.iex.demo.service.impl.IexService;
import com.iex.demo.service.impl.ReturnService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class ReturnServiceTest {

    @Mock
    private IexService iexService;

    @InjectMocks
    private ReturnService returnService;

    @BeforeEach
    public void setup() {
        returnService = new ReturnService(iexService);
    }

    @Test
    public void testCalculateReturns() {
        // Mock data
        String symbol = "AAPL";
        LocalDate from = LocalDate.of(2022, 1, 1);
        LocalDate to = LocalDate.of(2022, 1, 10);
        Map<LocalDate, Double> historicalPrices = new HashMap<>();
        historicalPrices.put(LocalDate.of(2022, 1, 1), 100.0);
        historicalPrices.put(LocalDate.of(2022, 1, 2), 101.0);

        // Stubbing behavior
        when(iexService.getHistoricalPrices(symbol, from, to)).thenReturn(historicalPrices);

        // Calling the method under test
        List<Map<String, Object>> returns = returnService.calculateReturns(symbol, from, to);

        // Verifying behavior
        verify(iexService).getHistoricalPrices(symbol, from, to);
        assertEquals(1, returns.size());
    }
}