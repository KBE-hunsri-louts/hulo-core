package com.huloteam.kbe.csv;

import com.huloteam.kbe.model.Product;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class creates CSV files and stores them into a folder.
 */
public class CSVExporterImpl implements CSVExporter {
    // TODO needs to be placed somewhere else.
    String PATH = "/Schule/AI Studium/KBE/Vorlesung/";
    String directoryName = PATH.concat("CSV Folder");
    String fileName;

    @Override
    public void createCSV(Product product) {
        String[] productData = new String[4];
        productData[0] = product.getProductName();
        productData[1] = product.getProvider();
        productData[2] = product.getProviderPrice() + "";
        productData[3] = product.getStoredSince() + "";

        String string = product.getProductName().concat(product.getProvider()).concat(String.valueOf(product.getProviderPrice())).concat(product.getStoredSince().toString());
        int hashFromString = string.hashCode();
        fileName = hashFromString + ".csv";

        createFolder();
        storeCSVFileIntoFolder(convertToCSV(productData));
    }

    /**
     * Creates a folder if it is not already exists.
     */
    private void createFolder() {
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdir();
        }
    }

    /**
     * Stores the product data into a file.
     * @param csvData is product data.
     */
    private void storeCSVFileIntoFolder(String csvData) {
        File file = new File(directoryName + "/" + fileName);
        try {
            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(csvData);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes all Strings in data (array) to one String.
     * @param data is a String-Array.
     * @return a String of product data.
     */
    private String convertToCSV(String[] data) {
        return Stream.of(data)
                .map(this::escapeSpecialCharacters)
                .collect(Collectors.joining(","));
    }

    /**
     * Handles problematic characters.
     * @param data is a String.
     * @return a String.
     */
    private String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");

        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }

        return escapedData;
    }
}



