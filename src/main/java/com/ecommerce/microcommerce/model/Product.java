package com.ecommerce.microcommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString
@JsonIgnoreProperties("monFiltreDynamique")
public class Product {
    private int id;
    private String nom;
    private int prix;
    private int prixAchat;
}
