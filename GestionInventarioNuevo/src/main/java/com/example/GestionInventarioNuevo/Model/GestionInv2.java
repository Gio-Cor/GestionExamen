package com.example.GestionInventarioNuevo.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="productos")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class GestionInv2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable=false, unique=true, length=50)
    private String nombre;

    @Column(nullable=false)
    private double precio;

    @Column(nullable=true, length=255)
    private String descripcion;

    @Column(nullable=false)
    private int stock;

    @Column(nullable=false)
    private String categoria;

    @Column(nullable=false)
    private boolean disponibleEnLinea;

    @Column(nullable=false)
    private boolean disponibleEnTienda;
}



