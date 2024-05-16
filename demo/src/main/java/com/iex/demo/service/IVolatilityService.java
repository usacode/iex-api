package com.iex.demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IVolatilityService {
    List<Map<String, Object>> calculateVolatility(String symbol, LocalDate from, LocalDate to);
}
