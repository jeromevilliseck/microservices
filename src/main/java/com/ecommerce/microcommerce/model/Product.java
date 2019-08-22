package com.ecommerce.microcommerce.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString
public class Product {
    private int id;
    private String nom;
    private int prix;
}
