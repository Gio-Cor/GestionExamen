package com.example.GestionInventarioNuevo.service;

import com.example.GestionInventarioNuevo.Model.GestionInv2;
import com.example.GestionInventarioNuevo.Repository.GestionRepository;
import com.example.GestionInventarioNuevo.Service.GestionInventarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class GestionInventarioServiceTest {

    @Autowired
    private GestionInventarioService gestionInventarioService;

    @MockBean
    private GestionRepository gestionRepository;

    @Test
    void listarInv_debeRetornarListaDeProductos() {
        GestionInv2 producto = new GestionInv2();
        producto.setNombre("Producto Test");
        producto.setPrecio(100.0);
        producto.setDescripcion("Descripción de prueba");
        producto.setStock(10);
        producto.setCategoria("Categoria Test");
        producto.setDisponibleEnLinea(true);
        producto.setDisponibleEnTienda(false);

        when(gestionRepository.findAll()).thenReturn(Arrays.asList(producto));

        List<GestionInv2> resultado = gestionInventarioService.listarInv();

        assertEquals(1, resultado.size());
        assertEquals("Producto Test", resultado.get(0).getNombre());
    }
}