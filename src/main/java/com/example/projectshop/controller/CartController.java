package com.example.projectshop.controller;

import com.example.projectshop.global.GlobalData;
import com.example.projectshop.model.ProductModel;
import com.example.projectshop.service.IProductService;
import com.example.projectshop.service.imp.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CartController {
    @Autowired
    ProductServiceImpl productService;

    @GetMapping("/cart")
    public String getCart(Model model){
        model.addAttribute("cartCount", GlobalData.cartList.size());
        model.addAttribute("total",GlobalData.cartList.stream().mapToDouble(ProductModel::getPrice).sum());
        model.addAttribute("cart",GlobalData.cartList);
        return "cart";
    }

    @GetMapping("/addToCart/{id}")
    public String addToCart(@PathVariable("id") Long id){
        GlobalData.cartList.add(productService.getProductById(id).get());
        return "redirect:/shop";
    }

    @GetMapping("/cart/removeItem/{index}")
    public String removeCart(@PathVariable("index") Long index){
        GlobalData.cartList.remove(index.intValue());
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String checkout(Model model){
        model.addAttribute("cartCount", GlobalData.cartList.size());
        model.addAttribute("total",GlobalData.cartList.stream().mapToDouble(ProductModel::getPrice).sum());
        return "checkout";
    }
}
