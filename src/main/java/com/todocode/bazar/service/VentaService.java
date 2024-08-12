package com.todocode.bazar.service;

import com.todocode.bazar.dto.VentaClienteDTO;
import com.todocode.bazar.dto.VentaDelDiaDTO;
import com.todocode.bazar.exceptions.ResourceNotFoundException;
import com.todocode.bazar.model.Producto;
import com.todocode.bazar.model.Venta;
import com.todocode.bazar.repository.IVentaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Comparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VentaService implements IVentaService {

    @Autowired
    private IVentaRepository ventaRepo;

    @Override
    public List<Venta> getVentas() {
        List<Venta> listaVentas = ventaRepo.findAll();
        return listaVentas;
    }

    @Override
    public Venta findVenta(Long codigo_venta) {
        return ventaRepo.findById(codigo_venta)
                .orElseThrow(() -> new ResourceNotFoundException("Venta no encontrada con ID: " + codigo_venta));
    }

    @Override
    public void saveVenta(Venta venta) {
        ventaRepo.save(venta);
    }

    @Override
    public void deleteVenta(Long codigo_venta) {
        Venta venta = ventaRepo.findById(codigo_venta)
                .orElseThrow(() -> new ResourceNotFoundException("Venta no encontrada con ID: " + codigo_venta));
        ventaRepo.delete(venta);
    }

    @Override
    public Venta updateVenta(Long codigo_venta, Venta ventaDetalle) {
        return ventaRepo.findById(codigo_venta)
                .map(venta -> {
                    venta.setFecha_venta(ventaDetalle.getFecha_venta());
                    venta.setTotal(ventaDetalle.getTotal());
                    venta.setListaProductos(ventaDetalle.getListaProductos());
                    venta.setCliente(ventaDetalle.getCliente());
                    // Actualiza otros campos según sea necesario
                    return ventaRepo.save(venta);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Venta no encontrado con ID: " + codigo_venta));
    }

    @Override
    public List<Producto> getProductosByCodigoVenta(Long codigoVenta) {
        Venta venta = ventaRepo.findById(codigoVenta)
                .orElseThrow(() -> new ResourceNotFoundException("Venta no encontrada con código: " + codigoVenta));
        return venta.getListaProductos();
    }

    @Override
    public VentaDelDiaDTO getVentaDelDia(LocalDate fecha_venta) {
        List<Venta> ventas = this.getVentas();

        int cant_ventas = 0;
        int monto = 0;

        for (Venta v : ventas) {
            if (v.getFecha_venta().equals(fecha_venta)) {
                monto += v.getTotal();
                cant_ventas++;
            }
        }

        return new VentaDelDiaDTO(monto, cant_ventas);
    }

    /**
     * Obtener el codigo_venta, el total, la cantidad de productos, el nombre
     * del cliente y el apellido del cliente de la venta con el monto más alto
     * de todas.
     *
     */
    public VentaClienteDTO obtenerVentaConMontoMasAlto() {
        List<Venta> listaVentas = ventaRepo.findAll();

        if (listaVentas.isEmpty()) {
            throw new RuntimeException("No hay ventas disponibles");
        }

        Venta ventaConMontoMasAlto = null;
        for (Venta venta : listaVentas) {
            if (ventaConMontoMasAlto == null || venta.getTotal() > ventaConMontoMasAlto.getTotal()) {
                ventaConMontoMasAlto = venta;
            }
        }
        
        /**
         * Venta ventaConMontoMasAlto = listaVentas.stream()
         * .max(Comparator.comparing(Venta::getTotal)) .orElseThrow(() -> new
         * RuntimeException("No hay ventas disponibles"));
        *
         */

        return new VentaClienteDTO(
                ventaConMontoMasAlto.getCodigo_venta(),
                ventaConMontoMasAlto.getTotal(),
                ventaConMontoMasAlto.getListaProductos().size(),
                ventaConMontoMasAlto.getCliente().getNombre(),
                ventaConMontoMasAlto.getCliente().getApellido()
        );
    }

}
