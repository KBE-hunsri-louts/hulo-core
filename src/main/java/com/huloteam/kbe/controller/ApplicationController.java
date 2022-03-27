package com.huloteam.kbe.controller;

import com.huloteam.kbe.application.Application;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ApplicationController {
    private final Application application = new Application();

    // GET
    @RequestMapping(value = "/application")
    public ResponseEntity<Object> provideOneProduct(@RequestParam(name="genre") String genre,
                                                    @RequestParam(name="productGenre") String productGenre,
                                                    @RequestParam(name="vatID") String vatID) {
        if (!genre.isEmpty() && !productGenre.isEmpty() && !vatID.isEmpty()) {
            return new ResponseEntity<>(application.getSpecificProduct(genre, productGenre, vatID), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Every parameter must be filled!", HttpStatus.BAD_REQUEST);
        }
    }
}
