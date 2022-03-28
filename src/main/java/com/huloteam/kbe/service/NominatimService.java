package com.huloteam.kbe.service;

public interface NominatimService {
    void startApi(String street,String houseNumber, String city, String zip);
    double getToLat();
    double getToLon();
    String getResponse();
}
