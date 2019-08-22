package com.ecommerce.microcommerce.web.controller;

import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    ProductDao dao;

    @GetMapping(value="/Produits")
    public List<Product> listeProduits() {
        return dao.findAll();
    }

    @GetMapping(value="/Produits/{id}")
    public Product afficherUnProduit(@PathVariable int id) {
        return dao.productfindById(id);
    }

    @PostMapping(value="/Produits")
    public void ajouterProduit(@RequestBody Product product){
        dao.save(product);
    }
}
