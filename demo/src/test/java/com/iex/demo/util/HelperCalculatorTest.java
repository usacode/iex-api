package com.iex.demo.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HelperCalculatorTest {

    @Test
    void testCalculateAverageReturn() {
        Map<LocalDate, Double> prices = Map.of(
                LocalDate.of(2021, 1, 1), 100.0,
                LocalDate.of(2021, 1, 2), 200.0,
                LocalDate.of(2021, 1, 3), 300.0
        );

        double average = HelperCalculator.calculateAverageReturn(prices);
        assertEquals(200.0, average, 0.001, "The average return should be correctly calculated.");
    }

    @Test
    void testCalculateDailyReturns() {
        String symbol = "XYZ";
        Map<LocalDate, Double> historicalPrices = Map.of(
                LocalDate.of(2021, 1, 1), 100.0,
                LocalDate.of(2021, 1, 2), 110.0
        );

        LocalDate from = LocalDate.of(2021, 1, 1);
        LocalDate to = LocalDate.of(2021, 1, 2);

        List<Map<String, Object>> results = HelperCalculator.calculateDailyReturns(symbol, historicalPrices, from, to);
        assertEquals(1, results.size(), "There should be one return calculated.");
        assertEquals(0.1, (Double)results.get(0).get("dailyReturn"), 0.001, "The daily return should be correctly calculated.");
    }
}