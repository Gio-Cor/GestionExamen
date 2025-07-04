package com.example.GestionInventarioNuevo.Service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.GestionInventarioNuevo.Model.GestionInv2;
import com.example.GestionInventarioNuevo.Repository.GestionRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional

public class GestionInventarioService {
	@Autowired
    private GestionRepository gestionRepository;

    public List<GestionInv2> listarInv() {
        return gestionRepository.findAll();
    }

    public GestionInv2 agregarInv(GestionInv2 gestionInv) {
        return gestionRepository.save(gestionInv);
    }

    public GestionInv2 buscarInventarioPorId(String id) {
        Optional<GestionInv2> item = gestionRepository.findById(id);
        return item.orElse(null);
    }

    public GestionInv2 actualizarInventario(GestionInv2 gestionInv) {
        return gestionRepository.save(gestionInv);
    }

    public String eliminarInventario(String idprod) {
        if (gestionRepository.existsById(idprod)) {
            gestionRepository.deleteById(idprod);
            return "El inventario se elimino";
        }
        return "Inventario no encontrado";
    }

    public int totalInventario() {
        return (int) gestionRepository.count();
    }
}
