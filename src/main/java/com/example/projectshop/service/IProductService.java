package com.example.projectshop.service;

import com.example.projectshop.model.ProductModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface IProductService {
    List<ProductModel> getAllProductByCategoryId(Long id);
    Optional<ProductModel> getProductById(Long id);
    List<ProductModel> getAllProduct();
    void removeProductById(Long id);
    void updateProduct(ProductModel productModel);

}
