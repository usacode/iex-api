package com.iex.demo.controller;

import com.iex.demo.service.*;
import com.iex.demo.validation.IValidationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * @author Paul Ngouabeu
 * This class handles user requests from frontend services.
 */
@RestController
@RequestMapping("/api/v1")
@Tag(name="Stocks API",description="Operations related to stock items.")
public class StockController {

    private final IAlphaService alphaService;

    private final IValidationService validation;

    private final IReturnService returnService;

    private final IVolatilityService volatilityService;

    private final static String SYMBOL="symbol";


    public StockController(IReturnService returnService, IAlphaService alphaService, IVolatilityService volatilityService, IValidationService validation){
        this.returnService=returnService;
        this.alphaService=alphaService;
        this.volatilityService=volatilityService;
        this.validation=validation;
    }

    /**
     * This method handles the request for calculating the daily return for a given symbol within a specified date range.
     * @param symbol - ticker symbol
     * @param from - start date
     * @param to - end date
     * @return It returns the daily return.
     */
    @CrossOrigin
    @Operation(summary="Get a list of daily returns from ticker symbol.")
    @GetMapping("/returns")
    public ResponseEntity<List<Map<String, Object>>> getReturns(
            @RequestParam String symbol,
            @RequestParam(required = false)  String from,
            @RequestParam(required = false) String  to
    ) {
        validation.textValidation(symbol,SYMBOL);
        LocalDate startDate;
        LocalDate endDate;
        if (StringUtils.isAnyBlank(from) && StringUtils.isAnyBlank(to)) {
            // Default date range to Year-to-Date (YTD)
            startDate = LocalDate.now().withDayOfYear(1);
            endDate = LocalDate.now();
        }else{
            validation.dateValidation(from,to);
            startDate = LocalDate.parse(from);
            endDate = LocalDate.parse(to);
        }
        List<Map<String, Object>> returns = returnService.calculateReturns(symbol, startDate, endDate);
        return new ResponseEntity<>(returns, HttpStatus.OK);
    }

    /**
     * This method handles the request for calculating the alpha for a given symbol and benchmark within a specified date range.
     * @param symbol - Ticker symbol.
     * @param benchmark - Ticker symbol of the benchmark.
     * @param from - Start date of the date range.
     * @param to - End date of the date range.
     * @return The calculated alpha.
     */
    @CrossOrigin
    @Operation(summary="Get alpha from ticker symbol and ticker benchmark.")
    @GetMapping("/alpha")
    public ResponseEntity<Map<String, Object>> getAlpha(
            @RequestParam String symbol,
            @RequestParam String benchmark,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to
    ) {
        validation.textValidation(symbol,SYMBOL);
        validation.textValidation(benchmark,"benchmark");
        LocalDate startDate;
        LocalDate endDate;

        if (StringUtils.isAnyBlank(from) && StringUtils.isAnyBlank(to)) {
            // Default date range to Year-to-Date (YTD)
            startDate = LocalDate.now().withDayOfYear(1);
            endDate = LocalDate.now();
        }else{
            validation.dateValidation(from,to);
            startDate = LocalDate.parse(from);
            endDate = LocalDate.parse(to);
        }
        Map<String, Object> alpha = alphaService.calculateAlpha(symbol, benchmark, startDate, endDate);
        return new ResponseEntity<>(alpha, HttpStatus.OK);
    }

    /**
     * This method handles the request for calculating the volatility for a given symbol within a specified date range.
     * @param symbol - ticker symbol
     * @param from - start date
     * @param to - end date
     * @return It returns a list of volatility data points.
     */
    @CrossOrigin
    @Operation(summary="Get a volatility from ticker symbol.")
    @GetMapping("/volatility")
    public ResponseEntity<List<Map<String, Object>>> getVolatility(
            @RequestParam String symbol,
            @RequestParam(required = false)  String from,
            @RequestParam(required = false) String  to
    ) {
        validation.textValidation(symbol,SYMBOL);
        LocalDate startDate;
        LocalDate endDate;
        if (StringUtils.isAnyBlank(from) && StringUtils.isAnyBlank(to)) {
            // Default date range to Year-to-Date (YTD)
            startDate = LocalDate.now().withDayOfYear(1);
            endDate = LocalDate.now();
        }else{
            validation.dateValidation(from,to);
            startDate = LocalDate.parse(from);
            endDate = LocalDate.parse(to);
        }
        List<Map<String, Object>>  volatility = volatilityService.calculateVolatility(symbol, startDate, endDate);
        return new ResponseEntity<>(volatility, HttpStatus.OK);
    }
}
