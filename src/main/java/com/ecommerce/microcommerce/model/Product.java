package com.ecommerce.microcommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString
//@JsonIgnoreProperties("monFiltreDynamique")
@Entity
public class Product {
    @Id
    @GeneratedValue
    private int id;
    @Length(min=3, max=20, message = "Nom trop long ou trop court.")
    private String nom;
    @Min(value=1)
    private int prix;
    private int prixAchat;
}
