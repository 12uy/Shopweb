package com.example.projectshop.repository;

import com.example.projectshop.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductRepository extends JpaRepository<ProductModel,Long> {
    List<ProductModel> findAllByCategory_Id (Long id);

}
