package com.nexusvault.mscatalog.controller;

import com.nexusvault.mscatalog.model.ModelProduct;
import com.nexusvault.mscatalog.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<ModelProduct> getAllProducts() {
        return productRepository.findAll();
    }
}
