package com.example.GestionInventarioNuevo;

import com.example.GestionInventarioNuevo.Model.GestionInv2;
import com.example.GestionInventarioNuevo.Repository.GestionRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Random;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private GestionRepository gestionRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();

        // Generar 20 productos de inventario de prueba
        for (int i = 0; i < 20; i++) {
            GestionInv2 producto = new GestionInv2();
            producto.setNombre(faker.commerce().productName());
            producto.setPrecio(Double.parseDouble(faker.commerce().price()));
            producto.setDescripcion(faker.lorem().sentence());
            producto.setStock(random.nextInt(100) + 1);
            producto.setCategoria(faker.commerce().department());
            producto.setDisponibleEnLinea(faker.bool().bool());
            producto.setDisponibleEnTienda(faker.bool().bool());
            gestionRepository.save(producto);
        }
    }
}