package com.iex.demo.service;

import java.time.LocalDate;
import java.util.Map;

public interface IIexService {
    Map<LocalDate, Double> getHistoricalPrices(String symbol, LocalDate from, LocalDate to);
}
