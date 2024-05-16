package com.iex.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.iex.demo.config.ServiceConfig;
import com.iex.demo.service.impl.IexService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


@ExtendWith(MockitoExtension.class)
class IexServiceTest {

    @Mock
    private ServiceConfig serviceConfig;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private IexService iexService;

    @BeforeEach
    public void setup() {
        iexService = new IexService(serviceConfig, restTemplate);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetHistoricalPrices() {
        // Mock data
        String symbol = "AAPL";
        LocalDate from = LocalDate.of(2022, 1, 1);
        LocalDate to = LocalDate.of(2022, 1, 10);
        Map<String, Object> response1 = new HashMap<>();
        response1.put("priceDate", "2022-01-01");
        response1.put("close", 100.0);
        Map<String, Object> response2 = new HashMap<>();
        response2.put("priceDate", "2022-01-02");
        response2.put("close", 101.0);
        Map<String, Object>[] responseArray = new Map[]{response1, response2};

        // Mocking behavior without unnecessary stubbings
        when(restTemplate.getForObject(anyString(), any())).thenReturn(responseArray);

        // Calling the method under test
        Map<LocalDate, Double> historicalPrices = iexService.getHistoricalPrices(symbol, from, to);

        // Verifying behavior
        verify(restTemplate).getForObject(anyString(), eq(Map[].class));
        assertEquals(2, historicalPrices.size());
        assertEquals(100.0, historicalPrices.get(LocalDate.parse("2022-01-01")));
        assertEquals(101.0, historicalPrices.get(LocalDate.parse("2022-01-02")));
    }
}