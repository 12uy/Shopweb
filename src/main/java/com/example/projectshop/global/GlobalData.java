package com.example.projectshop.global;

import com.example.projectshop.model.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class GlobalData {
    public static List<ProductModel> cartList;

    static {
        cartList = new ArrayList<>();
    }
}
