package com.huloteam.kbe.csv;

import com.huloteam.kbe.model.Product;

/**
 * Is an interface to create CSV file create
 */
public interface CSVExporter {

    /**
     * Calls all methods to create and save a CSV file.
     * @param product is a given product which data will be stored in a CSV file.
     */
    void createCSV(Product product);
}
