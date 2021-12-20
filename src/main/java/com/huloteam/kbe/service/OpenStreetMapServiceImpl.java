package com.huloteam.kbe.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Service class which communicates with the OpenStreetMap API called ODRM.
 */
@Service
public class OpenStreetMapServiceImpl implements OpenStreetMapService {

    private String response;
    private static double duration;
    private final double homeLat = 52.0879085;
    private final double homeLon = 13.1697905;

    /**
     * Is the entry point to access our service.
     */
    @Override
    public void startApi(String street, String city, String zip) {

        NominatimServiceImpl nominatimServiceImpl = new NominatimServiceImpl();
        nominatimServiceImpl.startApi(street, city, zip);

        String durationUrlString = "http://router.project-osrm.org/trip/v1/driving/"
                + homeLat + ","
                + homeLon + ";"
                + nominatimServiceImpl.getToLat() + ","
                + nominatimServiceImpl.getToLon();

        openConnection(durationUrlString);

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

            response = getResponse(connection, "trips", "duration");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (connection != null) {
                connection.disconnect();
            }

        }

    }

    /**
      * Uses the given connection to read the response of the API.
      * https://github.com/eugenp/tutorials/blob/master/core-java-modules/core-java-networking-2/src/main/java/com/baeldung/httprequest/FullResponseBuilder.java
      * https://github.com/Project-OSRM/osrm-backend/blob/master/docs/http.md#general-options
      * @param connection is a HttpURLConnection and contains information of Nomination API.
      * @param pathName is a name of a JSON array or a JSON object.
      * @param searchInformation is a name of the important value which we are looking for.
      * @return a String with information of the connection.
      * @throws IOException might be thrown while using Streams.
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

    /**
     * Reads valuable information out of a StringBuilder.
     * @param content is a StringBuilder which contains JSON data.
     * @param pathName is a name of a JSON array or a JSON object.
     * @param searchInformation is a name of the important value which we are looking for.
     */
    private void getSpecificJsonInformation(StringBuilder content, String pathName, String searchInformation) {

        JSONObject object = new JSONObject(content.toString());
        JSONArray array = object.getJSONArray(pathName);

        String durationString = "";

        for (int i = 0; i < array.length(); i++) {
            durationString = array.getJSONObject(i).getString(searchInformation);
        }

        System.out.println(durationString);

        if (!durationString.isEmpty()) {
            duration = Double.parseDouble(durationString);
        }

    }


    @Override
    public double getDuration() {
        return (duration / 1000) / 60;
    }

    @Override
    public String getResponse() {
       return response;
   }

}
