package com.todocode.bazar.controller;

import com.todocode.bazar.dto.VentaClienteDTO;
import com.todocode.bazar.dto.VentaDelDiaDTO;
import com.todocode.bazar.exceptions.ResourceNotFoundException;
import com.todocode.bazar.model.Producto;
import com.todocode.bazar.model.Venta;
import com.todocode.bazar.service.IVentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.time.LocalDate;
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
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RestController
@Slf4j
@Tag(name = "Venta")
public class VentaController implements WebMvcConfigurer {

    @Autowired
    private IVentaService ventaService;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/swagger-ui/index.html");
    }

    @Operation(summary = "Obtener todas las ventas")
    @GetMapping("/ventas")
    public ResponseEntity<?> getVentas() {
        try {
            List<Venta> ventas = ventaService.getVentas();
            if (ventas.isEmpty()) {
                return ResponseEntity.ok("No hay ventas disponibles.");
            }
            return ResponseEntity.ok(ventas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: No se pudo obtener la lista de ventas.");
        }
    }

    @Operation(summary = "Encontrar una venta por codigo_venta")
    @GetMapping("/ventas/{codigo_venta}")
    public ResponseEntity<?> findVenta(@PathVariable("codigo_venta") Long codigo_venta) {
        try {
            Venta venta = ventaService.findVenta(codigo_venta);
            return ResponseEntity.ok(venta);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Venta no encontrada.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: No se pudo obtener el venta.");
        }
    }

    @Operation(summary = "Crear una venta")
    @PostMapping("/ventas/crear")
    public ResponseEntity<String> saveVenta(@RequestBody Venta venta) {
        try {
            ventaService.saveVenta(venta);
            return ResponseEntity.status(HttpStatus.CREATED).body("Venta creado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: No se pudo crear el venta.");
        }
    }

    @Operation(summary = "Eliminar una Venta por codigo_venta")
    @DeleteMapping("/ventas/eliminar/{codigo_venta}")
    public ResponseEntity<String> deleteVenta(@PathVariable Long codigo_venta) {
        try {
            ventaService.deleteVenta(codigo_venta);
            return ResponseEntity.ok("Venta eliminado exitosamente.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Venta no encontrado.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: No se pudo eliminar el venta.");
        }
    }

    @Operation(summary = "Actualizar una venta")
    @PutMapping("/ventas/editar/{codigo_venta}")
    public ResponseEntity<String> updateVenta(@PathVariable Long codigo_venta, @RequestBody Venta ventaDetalles) {
        try {
            ventaService.updateVenta(codigo_venta, ventaDetalles);
            return ResponseEntity.ok("Venta actualizada exitosamente.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Venta no encontrada.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: No se pudo actualizar el venta.");
        }
    }

    //Obtener la lista de productos de una determinada venta
    @Operation(summary = "Obtener la lista de productos de una determinada venta")
    @GetMapping("/ventas/productos/{codigo_venta}")
    public ResponseEntity<?> getProductosByCodigoVenta(@PathVariable("codigo_venta") Long codigo_venta) {
        try {
            List<Producto> listaProductos = ventaService.getProductosByCodigoVenta(codigo_venta);
            if (listaProductos.isEmpty()) {
                return ResponseEntity.ok("No hay lista de productos disponibles.");
            }
            return ResponseEntity.ok(listaProductos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: No se pudo obtener la lista de ventas.");
        }
    }

    // Obtener la sumatoria del monto y también cantidad total de ventas de un determinado día
    @Operation(summary = "Obtener la sumatoria del monto y también cantidad total de ventas de un determinado día")
    @GetMapping("/ventas/fecha/{fecha_venta}")
    public VentaDelDiaDTO getVentaDelDia(@PathVariable LocalDate fecha_venta) {
        return ventaService.getVentaDelDia(fecha_venta);
    }

    /**
     * Obtener el codigo_venta, el total, la cantidad de productos, el nombre
     * del cliente y el apellido del cliente de la venta con el monto más alto
     * de todas.
     */
    @Operation(summary = "Obtener el codigo_venta, el total, la cantidad de productos, "
            + "el nombre del cliente y el apellido del cliente de la venta con el monto más alto de todas.")
    @GetMapping("/ventas/mayor_venta")
    public VentaClienteDTO obtenerVentaConMontoMasAlto() {
        return ventaService.obtenerVentaConMontoMasAlto();
    }

}
