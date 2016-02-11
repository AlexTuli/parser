package com.epam.alex.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2/11/2016.
 *
 * @author Bocharnikov Alexander
 */
@Data
public class SubCategory {
    private String name;
    private List<Product> products;

    public SubCategory () {
        products = new ArrayList<>();
    }
}
