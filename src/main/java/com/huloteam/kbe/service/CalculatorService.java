package com.huloteam.kbe.service;

public interface CalculatorService {
    /**
     * Gets a price with tax from calculator component.
     * @param vatID is a specific tax
     * @param price is a number as a string
     * @return a price calculated with a specific tax
     */
    double getPriceWithTax(String vatID, String price);
}
