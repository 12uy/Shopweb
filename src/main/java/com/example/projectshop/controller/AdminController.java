package com.example.projectshop.controller;

import com.example.projectshop.dto.ProductDTO;
import com.example.projectshop.dto.UserDTO;
import com.example.projectshop.model.CategoryModel;
import com.example.projectshop.model.ProductModel;
import com.example.projectshop.model.RoleModel;
import com.example.projectshop.model.UserModel;
import com.example.projectshop.service.imp.CategoryServiceImpl;
import com.example.projectshop.service.imp.ProductServiceImpl;
import com.example.projectshop.service.imp.RoleServiceImpl;
import com.example.projectshop.service.imp.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {
    public static String uplloadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";

    @Autowired
    private PasswordEncoder bCPasswordEncoder;


    @Autowired
    CategoryServiceImpl categoryService;


    @Autowired
    ProductServiceImpl productService;


    @Autowired
    UserServiceImpl userService;

    @Autowired
    RoleServiceImpl roleService;


    @GetMapping("/admin")
    public String adminHome(){
        return "adminHome";
    }
    // lấy tất cả tài khoản
    @GetMapping("/admin/users")
    public String getAllAcc(Model model){
        model.addAttribute("users", userService.getAllUser());
        return "users";
    }

    @GetMapping("/admin/users/add")
    public String getUserAdd(Model model){
        model.addAttribute("userDTO", new UserDTO());
        model.addAttribute("roles", roleService.getAllRole());
        return "usersAdd";
    }

    @PostMapping("/admin/users/add")
    public String postUserAdd(@ModelAttribute("userDTO") UserDTO userDTO){
        // dto -> entity
        UserModel userModel = new UserModel();
        userModel.setId(Long.valueOf(userDTO.getId()));
        userModel.setEmail(userDTO.getEmail());
        userModel.setPassword(bCPasswordEncoder.encode(userDTO.getPassword()));
        userModel.setFirstName(userDTO.getFirstName());
        userModel.setLastName(userDTO.getLastName());
        List<RoleModel> roles = new ArrayList<>();
        for (Integer roleId : userDTO.getReleIds()) {
            roles.add(roleService.getRoleById(Long.valueOf(roleId)).get());
        }
        userModel.setRoles(roles);
        userService.updateUser(userModel);
        return "redirect:/admin/users";
    }

    // xóa tài khoản
    @GetMapping("/admin/users/delete/{id}")
    public String deleteUser(@PathVariable Long id){
        userService.removeUserById(id);
        return "redirect:/admin/users";
    }

    // sửa tài khoản
    @GetMapping("/admin/users/update/{id}")
    public String editUser(@PathVariable Long id, Model model){
        Optional<UserModel> optionalUserModel = userService.getUserById(id);
        if (optionalUserModel.isPresent()){
            // entity -> dto
            UserModel userModel = optionalUserModel.get();
            UserDTO userDTO = new UserDTO();
            userDTO.setId(Math.toIntExact(userModel.getId()));
            userDTO.setEmail(userModel.getEmail());
            userDTO.setFirstName(userModel.getFirstName());
            userDTO.setLastName(userModel.getLastName());
            List<Integer> roleIds = new ArrayList<>();
            for (RoleModel roleModel : userModel.getRoles()) {
                roleIds.add(Math.toIntExact(roleModel.getId()));
            }
            userDTO.setReleIds(roleIds);
            model.addAttribute("userDTO", userDTO);
            model.addAttribute("roles", roleService.getAllRole());
            return "usersAdd";
        }
        return "404";
    }

    //Category
    @GetMapping("/admin/categories")
    public String getAllCategory(Model model){
        model.addAttribute("categories", categoryService.getAllCategory());
        return "categories";
    }

    @GetMapping("/admin/categories/add")
    public String getCategoryAdd(Model model){
        model.addAttribute("category", new UserDTO());
        return "categoriesAdd";
    }

    @PostMapping("/admin/categories/add")
    public String postCategoryAdd(@ModelAttribute("category") CategoryModel category){
        // dto -> entity
        categoryService.updateCategory(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/delete/{id}")
    public String deleteCategory(@PathVariable Long id){
        categoryService.deleteCategoryById(id);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/update/{id}")
    public String editCategory(@PathVariable Long id, Model model){
        Optional<CategoryModel> optionalCategoryModel = categoryService.getCategoryById(id);
        if (optionalCategoryModel.isPresent()){
            // entity -> dto
            model.addAttribute("category", optionalCategoryModel.get());
            return "categoriesAdd";
        }
        return "404";
    }

    //Product
    @GetMapping("/admin/products")
    public String getAllProduct(Model model){
        model.addAttribute("products", productService.getAllProduct());
        return "products";
    }

    @GetMapping("/admin/products/add")
    public String getProductAdd(Model model){
        model.addAttribute("product", new ProductDTO());
        model.addAttribute("categories", categoryService.getAllCategory());
        return "productsAdd";
    }

    @PostMapping("/admin/products/add")
    public String postProductAdd(@ModelAttribute("productDTO") ProductDTO productDTO,
                                 @RequestParam("productImage") MultipartFile fileProductImage,
                                 @RequestParam("imgName") String imgName) throws IOException {
        // dto -> entity
        ProductModel productModel = new ProductModel();
        productModel.setId(Long.valueOf(productDTO.getId()));
        productModel.setName(productDTO.getName());
        productModel.setPrice(productDTO.getPrice());
        productModel.setDescription(productDTO.getDescription());
        productModel.setCategory(categoryService.getCategoryById(Long.valueOf(productDTO.getCategoryId())).get());
        String imageUUID;
        if(fileProductImage.isEmpty()) {
            imageUUID = imgName;
        } else {
            imageUUID = fileProductImage.getOriginalFilename();
            Path fileNameAndPath = Paths.get(uplloadDir , imageUUID);
            Files.write(fileNameAndPath, fileProductImage.getBytes());
        }
        productModel.setImage(imageUUID);
        productService.updateProduct(productModel);
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id){
        productService.removeProductById(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/products/update/{id}")
    public String editProduct(@PathVariable Long id, Model model){
        Optional<ProductModel> optionalProductModel = productService.getProductById(id);
        if (optionalProductModel.isPresent()){
            // entity -> dto
            ProductModel productModel = optionalProductModel.get();
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(productModel.getId());
            productDTO.setName(productModel.getName());
            productDTO.setPrice(productModel.getPrice());
            productDTO.setDescription(productModel.getDescription());
            productDTO.setCategoryId(Math.toIntExact(productModel.getCategory().getId()));
            productDTO.setImage(productModel.getImage());
            model.addAttribute("productDTO", productDTO);
            model.addAttribute("categories", categoryService.getAllCategory());
            return "productsAdd";
        }
        return "404";
    }






}
