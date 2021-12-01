package com.huloteam.kbe.service;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NominatimService {

    private String lonLatResponse;
    private String street = "bogenstrasse 1";
    private String city = "brandenburg";
    private String zip = "15366";
    private static double distance;
    private static double toLat = 51.60227345;
    private static double toLon = 14.473361149999999;

    public void startApi() {

        String lonLatUrlString = "https://nominatim.openstreetmap.org/search?format=json"
                + "&street=" + street
                + "&city=" + city
                + "&postalcode=" + zip;

        openConnection(lonLatUrlString);

    }

    private void openConnection(String urlString) {

        HttpURLConnection connection = null;

        try {

            URL distanceUrl = new URL(urlString);
            connection = (HttpURLConnection) distanceUrl.openConnection();
            connection.setRequestMethod("GET");

            lonLatResponse = getResponse(connection, "lat", "lon");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (connection != null) {
                connection.disconnect();
            }

        }

    }

    /**
     *
     * https://github.com/eugenp/tutorials/blob/master/core-java-modules/core-java-networking-2/src/main/java/com/baeldung/httprequest/FullResponseBuilder.java
     * @param connection
     * @return
     * @throws IOException
     */
    private String getResponse(HttpURLConnection connection, String pathName, String searchInformation) throws IOException {

        StringBuilder fullResponseBuilder = new StringBuilder();

        // read response content
        Reader streamReader = null;

        if (connection.getResponseCode() > 299) {
            streamReader = new InputStreamReader(connection.getErrorStream());
        } else {
            streamReader = new InputStreamReader(connection.getInputStream());
        }

        BufferedReader bufferedReader = new BufferedReader(streamReader);
        String inputLine;
        StringBuilder content = new StringBuilder();

        while ((inputLine = bufferedReader.readLine()) != null) {
            content.append(inputLine);
        }

        bufferedReader.close();

        getSpecificJsonInformation(content, pathName, searchInformation);
        fullResponseBuilder.append("Response: ").append(content);

        return fullResponseBuilder.toString();

    }

    private static void getSpecificJsonInformation(StringBuilder content, String searchLat, String searchLon) {

        JSONArray array = new JSONArray(content.toString());

        String lonString = "";

        for (int i = 0; i < array.length(); i++) {
            lonString = array.getJSONObject(i).getString(searchLon);
        }

        System.out.println(lonString);

        String latString = "";

        for (int i = 0; i < array.length(); i++) {
            latString = array.getJSONObject(i).getString(searchLat);
        }

        System.out.println(latString);

        toLon = Double.parseDouble(lonString);
        toLat = Double.parseDouble(latString);

    }


    public double getToLat() {
        return toLat;
    }

    public double getToLon() {
        return toLon;
    }

    public String getLonLatResponse() {
        return lonLatResponse;
    }

}
