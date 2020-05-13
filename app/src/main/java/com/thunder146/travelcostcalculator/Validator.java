package com.thunder146.travelcostcalculator;

public class Validator
{
    public static boolean validateValues(float distance, float consumption, float price, int people)
    {
        return validateValue(distance) && validateValue(consumption) && validateValue(price) && validateValue(people);
    }

    private static boolean validateValue(float value)
    {
        if(value > 0)
            return true;
        else
            return false;
    }
}
