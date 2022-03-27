package com.huloteam.kbe.validator;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * Contains multiple methods to validate objects, strings and more.
 */
public class Validator {
    /**
     * Checks if a string only contains numbers.
     * https://www.baeldung.com/java-check-string-number
     * @param numberString is a string of numbers
     * @return true if string contains only numbers, false if not
     */
    public static boolean isStringOnlyContainingNumbers(String numberString) {
        return NumberUtils.isCreatable(numberString);
    }

    /**
     * Checks if the first number is bigger than the second.
     * @param biggerNumber is a long number which should be bigger
     * @param lowerNumber is a long number which should be smaller
     * @return true if the first parameter is smaller than the second, false if not
     */
    public static boolean isNumberBiggerLowerComparison(long biggerNumber, long lowerNumber) {
        return biggerNumber > lowerNumber;
    }

    /**
     * Checks if an object is not null.
     * @param object can be everything related to object-class
     * @return true if object is not null, false if it is null
     */
    public static boolean isObjectNotNull(Object object) {
        return object != null;
    }
}

