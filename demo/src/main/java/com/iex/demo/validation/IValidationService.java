package com.iex.demo.validation;

public interface IValidationService {

    void dateValidation(String startDate,String endDate);
    void textValidation(String text,String field);

}
