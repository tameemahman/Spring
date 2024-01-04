package com.tameem.SpringBoot55.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Department {

@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;
@Column(unique = true)
    private String name;
private String shortCode;


}
