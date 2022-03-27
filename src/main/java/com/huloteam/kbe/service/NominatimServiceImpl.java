package com.huloteam.kbe.service;

import com.huloteam.kbe.validator.Validator;
import org.json.JSONArray;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Service class which communicates with the OpenStreetMap API called Nominatim.
 */
@Service
public class NominatimServiceImpl implements NominatimService {
    private String response;
    private static double toLat;
    private static double toLon;

    /**
     * Is the entry point to access our service.
     */
    @Override
    public void startApi(String street, String city, String zip) {
        String lonLatUrlString = "https://nominatim.openstreetmap.org/search?format=json"
                + "&street=" + street
                + "&city=" + city
                + "&postalcode=" + zip;

        openConnection(lonLatUrlString);
    }

    /**
     * Creates the url and starts the connection to the API.
     * @param urlString is a String which contains a URL.
     */
    private void openConnection(String urlString) {
        HttpURLConnection connection = null;

        try {
            URL distanceUrl = new URL(urlString);
            connection = (HttpURLConnection) distanceUrl.openConnection();
            connection.setRequestMethod("GET");

            response = getResponse(connection);
        } catch (Exception exception) {
            exception.printStackTrace();
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

        getSpecificJsonInformation(content);
        fullResponseBuilder.append("Response: ").append(content);
        return fullResponseBuilder.toString();
    }

    /**
     * Reads valuable information out of a StringBuilder.
     * @param content is a StringBuilder which contains JSON data.
     */
    private static void getSpecificJsonInformation(StringBuilder content) {
        JSONArray array = new JSONArray(content.toString());
        String lonString = "";

        for (int index = 0; index < array.length(); index++) {
            lonString = array.getJSONObject(index).getString("lon");
        }

        System.out.println(lonString);

        String latString = "";

        for (int index = 0; index < array.length(); index++) {
            latString = array.getJSONObject(index).getString("lat");
        }

        System.out.println(latString);
        toLon = Double.parseDouble(lonString);
        toLat = Double.parseDouble(latString);
    }

    @Override
    public double getToLat() {
        return toLat;
    }

    @Override
    public double getToLon() {
        return toLon;
    }

    @Override
    public String getResponse() {
        return response;
    }
}
