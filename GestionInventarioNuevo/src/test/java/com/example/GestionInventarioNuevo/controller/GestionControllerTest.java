package com.example.GestionInventarioNuevo.controller;

import com.example.GestionInventarioNuevo.Controller.GestionController;
import com.example.GestionInventarioNuevo.Model.GestionInv2;
import com.example.GestionInventarioNuevo.Service.GestionInventarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(GestionController.class)
public class GestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GestionInventarioService gestionInventarioService;

        @Test
        public void listarInventario_debeRetornarListaYStatus200() throws Exception {
        GestionInv2 producto = new GestionInv2();
        producto.setNombre("Producto Test");
        producto.setPrecio(100.0);
        producto.setDescripcion("Descripción de prueba");
        producto.setStock(10);
        producto.setCategoria("Categoria Test");
        producto.setDisponibleEnLinea(true);
        producto.setDisponibleEnTienda(false);

        when(gestionInventarioService.listarInv())
                .thenReturn(Arrays.asList(producto));

        mockMvc.perform(get("/api/v1/inventario")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.gestionInv2List[0].nombre").value("Producto Test"));
        }

    @Test
    void listarInventario_debeRetornarNoContentSiVacio() throws Exception {
        when(gestionInventarioService.listarInv())
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/inventario")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}