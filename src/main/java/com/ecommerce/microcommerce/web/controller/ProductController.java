package com.ecommerce.microcommerce.web.controller;

import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.model.Product;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class ProductController {
    @Autowired
    ProductDao dao;

    /**
     * Recupérer la liste des produits
     * @return la liste des produits
     */
    @GetMapping(value="/Produits")
    public MappingJacksonValue listeProduits() {
        List<Product> produits = dao.findAll();

        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");
        FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);
        MappingJacksonValue produitsFiltres = new MappingJacksonValue(produits);
        produitsFiltres.setFilters(listDeNosFiltres);

        return produitsFiltres;
    }

    /**
     * @param id produit que l'on souhaite récupérer
     * @return un objet produit
     */
    @GetMapping(value="/Produits/{id}")
    public Product afficherUnProduit(@PathVariable int id) {
        return dao.productfindById(id);
    }

    @PostMapping(value="/Produits")
    public ResponseEntity<Void> ajouterProduit(@RequestBody Product product){
        Product productAdded = dao.save(product);

        if(productAdded == null){
            return ResponseEntity.noContent().build(); //Code 204 No Content
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productAdded.getId())
                .toUri();

        return ResponseEntity.created(location).build(); //Code 201 confirmation de l'ajout de la ressource
    }
}
