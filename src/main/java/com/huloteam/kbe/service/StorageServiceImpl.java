package com.huloteam.kbe.service;

import com.huloteam.kbe.validator.Validator;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class StorageServiceImpl implements StorageService {
    private final static int PORT = 8300;

    @Override
    public Object[] getStorageProductInformation(String productName) {
        try {
            String storageUrlString = "http://localhost:" + PORT + "/storage" +
                    "?productName=" + productName;

            return getSpecificJsonInformation(getResponse(openConnection(storageUrlString)));
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        }
    }

    /**
     * Creates the url and starts the connection to the API.
     * @param urlString is a String which contains a URL.
     */
    private HttpURLConnection openConnection(String urlString) {
        HttpURLConnection connection = null;

        try {
            URL distanceUrl = new URL(urlString);
            connection = (HttpURLConnection) distanceUrl.openConnection();
            connection.setRequestMethod("GET");

            return connection;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        } finally {
            if (Validator.isObjectNotNull(connection)) {
                connection.disconnect();
            }
        }
    }

    /**
     * Uses the given connection to read the response of the API.
     * https://github.com/eugenp/tutorials/blob/master/core-java-modules/core-java-networking-2/src/main/java/com/baeldung/httprequest/FullResponseBuilder.java
     * @param connection is a HttpURLConnection and contains information of Storage API.
     * @return a String with information of the connection.
     * @throws IOException might be thrown while using Streams.
     */
    private StringBuilder getResponse(HttpURLConnection connection) throws IOException {
        StringBuilder fullResponseBuilder = new StringBuilder();
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

        fullResponseBuilder.append(content);
        return fullResponseBuilder;
    }

    /**
     * Reads valuable information out of a StringBuilder.
     * @param content is a StringBuilder which contains JSON data.
     */
    private Object[] getSpecificJsonInformation(StringBuilder content) {
        if (Validator.isObjectNotNull(content)) {
            Object[] returnObject = new Object[3];
            JSONArray jsonArray = new JSONArray(content.toString());

            for (int i = 0; i < jsonArray.length(); i++) {
                returnObject[i] = jsonArray.get(i);
            }

            return returnObject;
        } else {
            return null;
        }
    }
}
