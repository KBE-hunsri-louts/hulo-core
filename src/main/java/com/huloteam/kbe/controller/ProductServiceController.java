package com.huloteam.kbe.controller;

import com.huloteam.kbe.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Is the MVC-Controller. Which handles data transfer between view and com.huloteam.kbe.model.*
 */
public class ProductServiceController {

    /*
    @Autowired
    ProductService productService;

    // READ
    @RequestMapping(value = "/products")
    public ResponseEntity<Object> provideProduct() {
        return new ResponseEntity<>(productService.getProducts(), HttpStatus.OK);
    }

    // CREATE
    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public ResponseEntity<Object> createProduct(@RequestBody Product product) {

        productService.createProduct(product);
        return new ResponseEntity<>("Product is created successfully", HttpStatus.CREATED);

    }

    // UPDATE
    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> replaceProduct(@RequestBody Product updatedProduct, @PathVariable("id") long id) {

        productService.updateProduct(id, updatedProduct);
        return new ResponseEntity<>("Product is updated successfully", HttpStatus.OK);

    }

    // DELETE
    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("id") long id) {

        productService.deleteProduct(id);
        return new ResponseEntity<>("Product is deleted successfully", HttpStatus.OK);

    }
     */
}
