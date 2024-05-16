package com.iex.demo.validation;


import com.iex.demo.exception.BadRequestException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


import java.time.format.DateTimeFormatter;



/**
 * @author Paul Ngouabeu
 * This class handles the validations from user inputs.
 */
@Component
public class ValidationService implements IValidationService {

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
    private final DateTimeFormatter alternativeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private final static String GENERIC_MSG="Either don't provide any dates so the default one year will be consider.";

    /**
     * This method validates the user text inputs
     * @param text - User input.
     * @param field - Parameter name.
     */
    @Override
    public void textValidation(String text,String field){
        if(StringUtils.isAnyBlank(text)){
            String errorMessage = String.format("Required parameter '%s' can't be blank or null.", field);
            throw new BadRequestException(HttpStatus.BAD_REQUEST.value(),errorMessage,field);
        }
    }
    /**
     * This method validates the user date inputs
     * @param startDate - start date
     * @param endDate - end date
     */
    @Override
    public void dateValidation(String startDate, String endDate) {
        LocalDate sDate;
        LocalDate eDate;

        //Validate start date otherwise throw BadRequestException
        try {
            sDate = LocalDate.parse(startDate, dateFormatter);
        } catch (Exception e) {
            try {
                sDate = LocalDate.parse(startDate, alternativeFormatter);
            } catch (Exception e2) {
                throw new BadRequestException(HttpStatus.BAD_REQUEST.value(),"Invalid date format. Please use yyyy-MM-dd or mm/dd/yyyy format. "+GENERIC_MSG);
            }
        }
        //Validate end date otherwise throw BadRequestException
        try {
            eDate = LocalDate.parse(endDate, dateFormatter);
        } catch (Exception e) {
            try {
                eDate = LocalDate.parse(endDate, alternativeFormatter);
            } catch (Exception e2) {
                throw new BadRequestException(HttpStatus.BAD_REQUEST.value(),"Invalid date format. Please use yyyy-MM-dd or mm/dd/yyyy format. "+GENERIC_MSG);
            }
        }

        //Validate end date is not before start date otherwise throw BadRequestException
        if (eDate.isBefore(sDate)) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST.value(),"End date cannot be before start date. "+GENERIC_MSG);
        }

        long diffInYears = eDate.toEpochDay() - sDate.toEpochDay();
        diffInYears = diffInYears / 365; // Approximate conversion to years
        if (diffInYears > 1) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST.value(),"Date range cannot exceed one year. Keep date range empty for default on year search. "+GENERIC_MSG);

        }

    }
}
