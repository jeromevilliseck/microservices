package com.ecommerce.microcommerce.web.controller;

import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.model.Product;
import com.ecommerce.microcommerce.web.exceptions.ProduitIntrouvableException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class ProductController {
    @Autowired
    ProductDao dao;

    /**
     * Recupérer la liste des produits par le biais d'un filtre Jackson pour ne pas retourner certains champs
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
        Product produit = dao.findById(id);
        if(produit==null) throw new ProduitIntrouvableException
                ("Le produit avec l'id " + id + " est INTROUVABLE. Écran Bleu si je pouvais.");
        return produit;
    }

    @GetMapping(value="/test/Produits/{prix}")
    public List<Product> produitsSuperieur(@PathVariable int prix) {
        return dao.findByPrixGreaterThan(prix);
    }

    @GetMapping(value="/test/Produits/nom/{nom}")
    public List<Product> produitsNom(@PathVariable String nom){
        return dao.findByNomLike("%"+nom+"%");
    }

    @PostMapping(value="/Produits")
    public ResponseEntity<Void> ajouterProduit(@Valid @RequestBody Product product){
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

    @DeleteMapping(value="/Produits/{id}")
    public void supprimerProduit(@PathVariable int id) {
        dao.deleteById(id);
    }

    @PutMapping (value = "/Produits")
    public void updateProduit(@RequestBody Product product) {
        dao.save(product);
    }
}
