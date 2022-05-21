package com.example.projectshop.service;

import com.example.projectshop.model.CategoryModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface ICategoryService {
    List<CategoryModel> getAllCategory();
    Optional<CategoryModel> getCategoryById(Long id);

    void updateCategory(CategoryModel categoryModel);
    void deleteCategoryById(Long id);
}
