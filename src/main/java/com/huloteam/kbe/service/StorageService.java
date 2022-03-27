package com.huloteam.kbe.service;

import java.util.List;

public interface StorageService {
    /**
     * Gets product information from storage component.
     * @param productName is a string which is needed to get the right information
     * @return a list of product information
     */
    String getStorageProductInformation(String productName);
}
