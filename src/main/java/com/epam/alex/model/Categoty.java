package com.epam.alex.model;

import com.sun.istack.internal.NotNull;
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
public class Categoty {
    private String name;
    private List<SubCategory> subCategories;

    public Categoty () {
        subCategories = new ArrayList<>();
    }
}
