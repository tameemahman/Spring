package com.tameem.SpringBoot55.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="products")
@NoArgsConstructor
@AllArgsConstructor
@Data



public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String description;
    private float price;
    private int stockQuantity;



}
