package com.example.GestionInventarioNuevo.Controller;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.example.GestionInventarioNuevo.Model.GestionInv2;
import com.example.GestionInventarioNuevo.Service.GestionInventarioService;


@RestController
@RequestMapping("/api/v1/inventario")
public class GestionController {

    @Autowired
    private GestionInventarioService gestionInventarioService;

    @GetMapping
    @Operation(summary = "Listar inventario", description = "Obtiene la lista de todos los inventarios")
    public ResponseEntity<CollectionModel<EntityModel<GestionInv2>>> listarInventario() {
        List<GestionInv2> inventario = gestionInventarioService.listarInv();
        if (inventario.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<EntityModel<GestionInv2>> inventarioResources = inventario.stream()
                .map(item -> EntityModel.of(item,
                        linkTo(methodOn(GestionController.class).buscarInventarioPorId(String.valueOf(item.getId()))).withSelfRel(),
                        linkTo(methodOn(GestionController.class).listarInventario()).withRel("inventario-list")
                ))
                .toList();
        CollectionModel<EntityModel<GestionInv2>> collectionModel = CollectionModel.of(
                inventarioResources,
                linkTo(methodOn(GestionController.class).listarInventario()).withSelfRel()
        );
        return ResponseEntity.ok(collectionModel);
    }

    @PostMapping
    public ResponseEntity<EntityModel<GestionInv2>> agregarInventario(@RequestBody GestionInv2 gestionInv2) {
        try {
            GestionInv2 nuevo = gestionInventarioService.agregarInv(gestionInv2);
            EntityModel<GestionInv2> resource = EntityModel.of(nuevo,
                    linkTo(methodOn(GestionController.class).buscarInventarioPorId(String.valueOf(nuevo.getId()))).withSelfRel(),
                    linkTo(methodOn(GestionController.class).listarInventario()).withRel("inventario-list")
            );
            return ResponseEntity.ok(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{idprod}")
    @Operation(summary = "Buscar inventario por ID", description = "Obtiene un inventario específico por su ID de producto")
    public ResponseEntity<EntityModel<GestionInv2>> buscarInventarioPorId(@PathVariable String idprod) {
        GestionInv2 item = gestionInventarioService.buscarInventarioPorId(idprod);
        if (item != null) {
            EntityModel<GestionInv2> resource = EntityModel.of(item,
                    linkTo(methodOn(GestionController.class).buscarInventarioPorId(idprod)).withSelfRel(),
                    linkTo(methodOn(GestionController.class).listarInventario()).withRel("inventario-list")
            );
            return ResponseEntity.ok(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{idprod}")
    public ResponseEntity<EntityModel<GestionInv2>> actualizarInventario(@PathVariable String idprod, @RequestBody GestionInv2 gestionInv) {
        GestionInv2 existente = gestionInventarioService.buscarInventarioPorId(idprod);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        existente.setStock(gestionInv.getStock());
        GestionInv2 actualizado = gestionInventarioService.actualizarInventario(existente);
        EntityModel<GestionInv2> resource = EntityModel.of(actualizado,
                linkTo(methodOn(GestionController.class).buscarInventarioPorId(idprod)).withSelfRel(),
                linkTo(methodOn(GestionController.class).listarInventario()).withRel("inventario-list")
        );
        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{idprod}")
    public ResponseEntity<EntityModel<String>> eliminarInventario(@PathVariable String idprod) {
        String resultado = gestionInventarioService.eliminarInventario(idprod);
        if (resultado.contains("elimino")) {
            EntityModel<String> resource = EntityModel.of(resultado,
                    linkTo(methodOn(GestionController.class).listarInventario()).withRel("inventario-list")
            );
            return ResponseEntity.ok(resource);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

}
