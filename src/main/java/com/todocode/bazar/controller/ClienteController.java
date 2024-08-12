package com.todocode.bazar.controller;

import com.todocode.bazar.exceptions.ResourceNotFoundException;
import com.todocode.bazar.model.Cliente;
import com.todocode.bazar.service.IClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Tag(name = "Cliente")
public class ClienteController {

    @Autowired
    private IClienteService clienteService;

    @Operation(summary = "Obtener todos los clientes")
    @GetMapping("/clientes")
    public ResponseEntity<?> getClientes() {
        try {
            List<Cliente> clientes = clienteService.getClientes();
            if (clientes.isEmpty()) {
                return ResponseEntity.ok("No hay clientes disponibles.");
            }
            return ResponseEntity.ok(clientes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: No se pudo obtener la lista de clientes.");
        }
    }

    @Operation(summary = "Obtener cliente por id_cliente")
    @GetMapping("/clientes/{id_cliente}")
    public ResponseEntity<?> findCliente(@PathVariable("id_cliente") Long id_cliente) {
        try {
            Cliente cliente = clienteService.findCliente(id_cliente);
            return ResponseEntity.ok(cliente);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Cliente no encontrado.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: No se pudo obtener el cliente.");
        }
    }

    @Operation(summary = "Crear un cliente")
    @PostMapping("/clientes/crear")
    public ResponseEntity<String> saveCliente(@RequestBody Cliente cliente) {
        try {
            clienteService.saveCliente(cliente);
            return ResponseEntity.status(HttpStatus.CREATED).body("Cliente creado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: No se pudo crear el cliente.");
        }
    }

    @Operation(summary = "Eliminar un cliente por id_cliente")
    @DeleteMapping("/clientes/eliminar/{id_cliente}")
    public ResponseEntity<String> deleteCliente(@PathVariable Long id_cliente) {
        try {
            clienteService.deleteCliente(id_cliente);
            return ResponseEntity.ok("Cliente eliminado exitosamente.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Cliente no encontrado.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: No se pudo eliminar el cliente.");
        }
    }

    @Operation(summary = "Actualizar un cliente")
    @PutMapping("/clientes/editar/{id_cliente}")
    public ResponseEntity<String> updateCliente(@PathVariable Long id_cliente, @RequestBody Cliente clienteDetalles) {
        try {
            clienteService.updateCliente(id_cliente, clienteDetalles);
            return ResponseEntity.ok("Cliente actualizado exitosamente.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Cliente no encontrado.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: No se pudo actualizar el cliente.");
        }
    }

}
