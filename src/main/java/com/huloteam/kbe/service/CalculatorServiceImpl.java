package com.huloteam.kbe.service;

import com.huloteam.kbe.validator.Validator;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class CalculatorServiceImpl implements CalculatorService {
    private final static int PORT = 8001;

    @Override
    public double getPriceWithTax(String vatID, String price) {
        String calculatorUrlString = "http://localhost:" + PORT + "/calculator" +
                "?vatID=" + vatID +
                "&price=" + price;

        return openConnection(calculatorUrlString);
    }

    /**
     * Creates the url and starts the connection to the API.
     * @param urlString is a String which contains a URL.
     */
    private double openConnection(String urlString) {
        HttpURLConnection connection = null;

        try {
            URL distanceUrl = new URL(urlString);
            connection = (HttpURLConnection) distanceUrl.openConnection();
            connection.setRequestMethod("GET");

            return Double.parseDouble(getResponse(connection));
        } catch (Exception exception) {
            exception.printStackTrace();
            // TODO is that legal?
            return -1;
        } finally {
            if (Validator.isObjectNotNull(connection)) {
                connection.disconnect();
            }
        }
    }

    /**
     * Uses the given connection to read the response of the API.
     * https://github.com/eugenp/tutorials/blob/master/core-java-modules/core-java-networking-2/src/main/java/com/baeldung/httprequest/FullResponseBuilder.java
     * @param connection is a HttpURLConnection and contains information of Nomination API.
     * @return a String with information of the connection.
     * @throws IOException might be thrown while using Streams.
     */
    private String getResponse(HttpURLConnection connection) throws IOException {
        // read response content
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
        return content.toString();
    }
}
