package com.iex.demo.validation;

import static org.junit.jupiter.api.Assertions.*;

import com.iex.demo.exception.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ValidationTest {

    @InjectMocks
    private ValidationService validation;

    @BeforeEach
    public void setup() {
        validation = new ValidationService();
    }

    @Test
    public void testTextValidationNotBlank() {
        assertDoesNotThrow(() -> validation.textValidation("example", "field"));
    }

    @Test
    public void testTextValidationBlank() {
        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> validation.textValidation("", "field"));
        assertEquals("Required parameter 'field' can't be blank or null.", exception.getMessage());
    }

    @Test
    public void testDateValidationValidDates() {
        assertDoesNotThrow(() -> validation.dateValidation("2022-01-01", "2022-01-10"));
    }

    @Test
    public void testDateValidationInvalidFormat() {
        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> validation.dateValidation("01-01-2022", "01-10-2022"));
        assertTrue(exception.getMessage().contains("Invalid date format."));
    }

    @Test
    public void testDateValidationEndDateBeforeStartDate() {
        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> validation.dateValidation("2022-01-10", "2022-01-01"));
        assertEquals("End date cannot be before start date. Either don't provide any dates so the default one year will be consider.", exception.getMessage());
    }

    @Test
    public void testDateValidationDateRangeExceedsOneYear() {
        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> validation.dateValidation("2021-01-01", "2023-01-01"));
        assertEquals("Date range cannot exceed one year. Keep date range empty for default on year search. Either don't provide any dates so the default one year will be consider.", exception.getMessage());
    }

    @Test
    public void testDateValidationValidDateFormatAlternative() {
        assertDoesNotThrow(() -> validation.dateValidation("01/01/2022", "01/10/2022"));
    }
}