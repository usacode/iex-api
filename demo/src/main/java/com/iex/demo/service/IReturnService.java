package com.iex.demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IReturnService {
    List<Map<String, Object>> calculateReturns(String symbol, LocalDate from, LocalDate to);
}
