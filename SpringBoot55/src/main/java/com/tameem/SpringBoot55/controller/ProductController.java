package com.tameem.SpringBoot55.controller;

import com.tameem.SpringBoot55.model.Product;
import com.tameem.SpringBoot55.repository.Productrepo;
import com.tameem.SpringBoot55.service.CategoryService;
import com.tameem.SpringBoot55.service.Productservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private Productservice productSevice;

    @Autowired
    private Productrepo productrepo;

    @Autowired
    private CategoryService dService;


    @GetMapping()
    public String allProduct(Model m) {
        List<Product> productList = productrepo.findAll();
        m.addAttribute("productList", productList);
        m.addAttribute("title", "All Product");
        return "shopPage";
    }

//    @GetMapping("/page")
//
//    public String allshopProduct(Model m) {
//        List<Product> productList = productSevice.getAllProduct();
//        m.addAttribute("prouctList", productList);
//        m.addAttribute("title", "All Product");
//        return "shopPage";
//    }

    @GetMapping("/saveform")
    public String saveForm(Model m) {
        m.addAttribute("product", new Product());
        return "addproduct";
    }

    @RequestMapping(value = "/pro_save", method = RequestMethod.POST)
    public String addPro(@ModelAttribute("product") Product product) {
        productSevice.saveProduct(product);
        return "redirect:/product";
    }

//    @RequestMapping(value = "/403")
//    public String error() {
//        return "403";
//    }

}


