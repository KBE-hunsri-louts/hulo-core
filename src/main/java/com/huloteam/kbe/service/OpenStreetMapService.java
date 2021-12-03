package com.huloteam.kbe.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

@Service
public class OpenStreetMapService {

    private String durationResponse;
    private static double duration;
    private double toLat = 51.60227345;
    private double toLon = 14.473361149999999;
    private final double homeLat = 52.0879085;
    private final double homeLon = 13.1697905;

    public void startApi() {

        String durationUrlString = "http://router.project-osrm.org/trip/v1/driving/"
                + homeLat + ","
                + homeLon + ";"
                + toLat + ","
                + toLon;

        openConnection(durationUrlString);

    }

    private void openConnection(String urlString) {

        HttpURLConnection connection = null;

        try {

            URL distanceUrl = new URL(urlString);
            connection = (HttpURLConnection) distanceUrl.openConnection();
            connection.setRequestMethod("GET");

            durationResponse = getResponse(connection, "trips", "duration");

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

        // read status and message
        fullResponseBuilder.append(connection.getResponseCode())
                .append(" ")
                .append(connection.getResponseMessage())
                .append("\n");

        // read headers
        connection.getHeaderFields()
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey() != null)
                .forEach(entry -> {

                    fullResponseBuilder.append(entry.getKey())
                            .append(": ");

                    List<String> headerValues = entry.getValue();
                    Iterator<String> it = headerValues.iterator();
                    if (it.hasNext()) {
                        fullResponseBuilder.append(it.next());

                        while (it.hasNext()) {
                            fullResponseBuilder.append(", ")
                                    .append(it.next());
                        }
                    }

                    fullResponseBuilder.append("\n");
                });

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


    public double getDuration() {
        return (duration / 1000) / 60;
    }

    public String getDurationResponse() {
       return durationResponse;
   }

}
