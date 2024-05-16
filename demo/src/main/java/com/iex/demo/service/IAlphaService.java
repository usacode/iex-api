package com.iex.demo.service;

import java.time.LocalDate;
import java.util.Map;

public interface IAlphaService {
    Map<String, Object> calculateAlpha(String symbol, String benchmark, LocalDate from, LocalDate to);
}
