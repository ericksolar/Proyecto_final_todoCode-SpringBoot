package com.todocode.bazar.controller;

import com.todocode.bazar.exceptions.ResourceNotFoundException;
import java.util.ArrayList;
import java.util.List;
import com.todocode.bazar.model.Producto;
import com.todocode.bazar.service.IProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Producto")
public class ProductoController {

    @Autowired
    private IProductoService productService;

    @Operation(summary = "Obtener todos los productos")
    @GetMapping("/productos")
    public ResponseEntity<?> getProductos() {
        try {
            List<Producto> productos = productService.getProductos();
            if (productos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay productos disponibles.");
            }
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: No se pudo obtener la lista de productos.");
        }
    }

    @Operation(summary = "Obtener producto por codigo_producto")
    @GetMapping("/productos/{codigo_producto}")
    public ResponseEntity<?> findProducto(@PathVariable Long codigo_producto) {
        try {
            Producto producto = productService.findProducto(codigo_producto);
            return ResponseEntity.ok(producto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Producto no encontrado.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: No se pudo obtener el producto.");
        }
    }

    @Operation(summary = "Crear un producto")
    @PostMapping("/productos/crear")
    public ResponseEntity<String> saveProducto(@RequestBody Producto producto) {
        try {
            productService.saveProducto(producto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Producto creado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: No se pudo crear el producto.");
        }
    }

    @Operation(summary = "Eliminar un producto por codigo_producto")
    @DeleteMapping("/productos/eliminar/{codigo_producto}")
    public ResponseEntity<String> deleteProducto(@PathVariable Long codigo_producto) {
        try {
            productService.deleteProducto(codigo_producto);
            return ResponseEntity.ok("Producto eliminado exitosamente.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Producto no encontrado.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: No se pudo eliminar el producto.");
        }
    }

    @Operation(summary = "Actualizar un producto")
    @PutMapping("/productos/editar/{codigo_producto}")
    public ResponseEntity<String> updateProducto(@PathVariable Long codigo_producto, @RequestBody Producto productoDetalles) {
        try {
            productService.updateProducto(codigo_producto, productoDetalles);
            return ResponseEntity.ok("Producto actualizado exitosamente.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Producto no encontrado.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: No se pudo actualizar el producto.");
        }
    }

    @Operation(summary = "Obtener todos los productos cuya cantidad_disponible sea menor a 5")
    //Obtener todos los productos cuya cantidad_disponible sea menor a 5
    @GetMapping("/productos/falta_stock")
    public ResponseEntity<?> faltaStock() {
        try {
            List<Producto> productosBajoStock = productService.getProductosBajoStock();
            if (productosBajoStock.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body("No hay productos con bajo stock.");
            }
            return ResponseEntity.ok(productosBajoStock);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: No se pudo obtener la lista de productos.");
        }
    }

}
