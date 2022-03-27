package com.huloteam.kbe.service;

import com.huloteam.kbe.validator.Validator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class StorageServiceImpl implements StorageService {

    @Override
    public String getStorageProductInformation(String productName) {
        String storageUrlString = "http://localhost:8080/storage" +
                "?productName=" + productName;

        return openConnection(storageUrlString);
    }

    /**
     * Creates the url and starts the connection to the API.
     * @param urlString is a String which contains a URL.
     */
    private String openConnection(String urlString) {
        HttpURLConnection connection = null;

        try {
            URL distanceUrl = new URL(urlString);
            connection = (HttpURLConnection) distanceUrl.openConnection();
            connection.setRequestMethod("GET");

            return getResponse(connection);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        } finally {
            if (Validator.isObjectNotNull(connection)) {
                connection.disconnect();
            }
        }
    }

    private String getResponse(HttpURLConnection connection) throws IOException {
        Reader streamReader = null;

        if (Validator.isNumberBiggerLowerComparison(connection.getResponseCode(), 299)) {
            streamReader = new InputStreamReader(connection.getErrorStream());
        } else {
            streamReader = new InputStreamReader(connection.getInputStream());
        }

        BufferedReader bufferedReader = new BufferedReader(streamReader);
        String inputLine;
        StringBuilder content = new StringBuilder();

        while (Validator.isObjectNotNull(inputLine = bufferedReader.readLine())) {
            content.append(inputLine);
        }

        bufferedReader.close();
        return connection.toString();
    }
}
