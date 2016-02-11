package com.epam.alex.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;

/**
 * Created on 2/11/2016.
 *
 * @author Bocharnikov Alexander
 */
@Data
@NoArgsConstructor
public class Product {
    private String name;
    private String provider;
    private String model;
    private Calendar dateOfIssue;
    private Boolean notInStock;
    private String color;
    private Double price;
}
