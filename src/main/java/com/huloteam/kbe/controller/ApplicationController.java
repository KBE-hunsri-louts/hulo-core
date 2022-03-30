package com.huloteam.kbe.controller;

import com.huloteam.kbe.application.Application;
import com.huloteam.kbe.model.Product;
import com.huloteam.kbe.validator.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ApplicationController {
    private final static String IS_EMPTY_TEXT = "Every parameter must be filled!";
    private final static String NOT_A_NUMBER = "The zip or house number can only contain numbers!";
    private final static String NOT_A_STRING = "The street and city name can only contain letters!";
    private final static String NAME_NOT_NULL = "Product name can not be null!";
    private final static String SAVED = "Product has been saved!";

    private final Application application = new Application();

    // GET product
    @RequestMapping(value = "/application/product")
    public ResponseEntity<Object> provideOneProduct(@RequestParam(name="searchCategory") String genre,
                                                    @RequestParam(name="searchValue") String productGenre) {
        if (!genre.isEmpty() && !productGenre.isEmpty()) {
            return new ResponseEntity<>(application.getSpecificProduct(genre, productGenre), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(IS_EMPTY_TEXT, HttpStatus.BAD_REQUEST);
        }
    }

    // POST product
    @PostMapping(value = "/application/product")
    public ResponseEntity<Object> postProduct(@RequestBody Product product) {
        if (Validator.isObjectNotNull(product.getProductName())) {
            application.registerProduct(product);
            return new ResponseEntity<>(SAVED, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(NAME_NOT_NULL, HttpStatus.BAD_REQUEST);
        }
    }

    // GET location
    @RequestMapping(value = "/application/location")
    public ResponseEntity<Object> provideLongitudeLatitude(@RequestParam(name="street") String street,
                                                           @RequestParam(name="houseNumber") String houseNumber,
                                                           @RequestParam(name="city") String city,
                                                           @RequestParam(name="zip") String zip) {
        if (!street.isEmpty() && !city.isEmpty() && !zip.isEmpty()) {
            if (Validator.isStringOnlyContainingNumbers(zip) && Validator.isStringContainingNumbers(houseNumber)) {
                if (!Validator.isStringContainingNumbers(city)) {
                    return new ResponseEntity<>(application.getNominatimData(street, houseNumber, city, zip), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(NOT_A_STRING, HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>(NOT_A_NUMBER, HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(IS_EMPTY_TEXT, HttpStatus.BAD_REQUEST);
        }
    }

    // GET duration
    @RequestMapping(value = "/application/duration")
    public ResponseEntity<Object> provideDuration(@RequestParam(name="street") String street,
                                                  @RequestParam(name="houseNumber") String houseNumber,
                                                  @RequestParam(name="city") String city,
                                                  @RequestParam(name="zip") String zip) {
        if (!street.isEmpty() && !city.isEmpty() && !zip.isEmpty()) {
            if (Validator.isStringOnlyContainingNumbers(zip) && Validator.isStringContainingNumbers(houseNumber)) {
                if (!Validator.isStringContainingNumbers(city)) {
                    return new ResponseEntity<>(application.getORSMDurationViaLocationInformation(street, houseNumber, city, zip), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(NOT_A_STRING, HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>(NOT_A_NUMBER, HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(IS_EMPTY_TEXT, HttpStatus.BAD_REQUEST);
        }
    }
}
