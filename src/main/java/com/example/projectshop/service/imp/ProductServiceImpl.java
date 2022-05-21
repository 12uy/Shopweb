package com.example.projectshop.service.imp;

import com.example.projectshop.model.ProductModel;
import com.example.projectshop.repository.ProductRepository;
import com.example.projectshop.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductModel> getAllProductByCategoryId(Long id) {
        return productRepository.findAllByCategory_Id(id);
    }

    @Override
    public Optional<ProductModel> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<ProductModel> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public void removeProductById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public void updateProduct(ProductModel productModel) {
        productRepository.save(productModel);
    }

}
