package com.huloteam.kbe.service;

public interface OpenStreetMapService {
    void startApi(String street, String city, String zip);
    double getDuration();
    String getResponse();
}
