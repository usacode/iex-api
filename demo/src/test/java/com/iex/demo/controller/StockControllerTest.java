package com.iex.demo.controller;

import com.iex.demo.service.*;
import com.iex.demo.validation.IValidationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StockControllerTest {

    @Mock
    private IAlphaService alphaService;

    @Mock
    private IValidationService validation;

    @Mock
    private IReturnService returnService;

    @Mock
    private IVolatilityService volatilityService;

    @InjectMocks
    private StockController stockController;

    @Test
    void testGetReturns_DefaultDateRange() {
        String symbol = "AAPL";
        LocalDate startDate = LocalDate.now().withDayOfYear(1);
        LocalDate endDate = LocalDate.now();
        List<Map<String, Object>> returns = Collections.singletonList(Collections.singletonMap("return", 0.05));

        doNothing().when(validation).textValidation(symbol, "symbol");
        when(returnService.calculateReturns(symbol, startDate, endDate)).thenReturn(returns);

        ResponseEntity<List<Map<String, Object>>> response = stockController.getReturns(symbol, null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(returns, response.getBody());
        verify(validation).textValidation(symbol, "symbol");
        verify(returnService).calculateReturns(symbol, startDate, endDate);
    }

    @Test
    void testGetAlpha_SpecifiedDateRange() {
        String symbol = "AAPL";
        String benchmark = "SPY";
        String from = "2023-01-01";
        String to = "2023-12-31";
        LocalDate startDate = LocalDate.parse(from);
        LocalDate endDate = LocalDate.parse(to);
        Map<String, Object> alpha = Collections.singletonMap("alpha", 0.1);

        doNothing().when(validation).textValidation(symbol, "symbol");
        doNothing().when(validation).textValidation(benchmark, "benchmark");
        doNothing().when(validation).dateValidation(from, to);
        when(alphaService.calculateAlpha(symbol, benchmark, startDate, endDate)).thenReturn(alpha);

        ResponseEntity<Map<String, Object>> response = stockController.getAlpha(symbol, benchmark, from, to);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(alpha, response.getBody());
        verify(validation).textValidation(symbol, "symbol");
        verify(validation).textValidation(benchmark, "benchmark");
        verify(validation).dateValidation(from, to);
        verify(alphaService).calculateAlpha(symbol, benchmark, startDate, endDate);
    }

    @Test
    void testGetVolatility_BlankDateRange() {
        String symbol = "AAPL";
        LocalDate startDate = LocalDate.now().withDayOfYear(1);
        LocalDate endDate = LocalDate.now();
        List<Map<String, Object>> volatility = Collections.singletonList(Collections.singletonMap("volatility", 0.2));

        doNothing().when(validation).textValidation(symbol, "symbol");
        when(volatilityService.calculateVolatility(symbol, startDate, endDate)).thenReturn(volatility);

        ResponseEntity<List<Map<String, Object>>> response = stockController.getVolatility(symbol, null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(volatility, response.getBody());
        verify(validation).textValidation(symbol, "symbol");
        verify(volatilityService).calculateVolatility(symbol, startDate, endDate);
    }

    @Test
    void testGetReturns_SpecifiedDateRange() {
        String symbol = "AAPL";
        String from = "2023-01-01";
        String to = "2023-12-31";
        LocalDate startDate = LocalDate.parse(from);
        LocalDate endDate = LocalDate.parse(to);
        List<Map<String, Object>> returns = Collections.singletonList(Collections.singletonMap("return", 0.05));

        doNothing().when(validation).textValidation(symbol, "symbol");
        doNothing().when(validation).dateValidation(from, to);
        when(returnService.calculateReturns(symbol, startDate, endDate)).thenReturn(returns);

        ResponseEntity<List<Map<String, Object>>> response = stockController.getReturns(symbol, from, to);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(returns, response.getBody());
        verify(validation).textValidation(symbol, "symbol");
        verify(validation).dateValidation(from, to);
        verify(returnService).calculateReturns(symbol, startDate, endDate);
    }
}