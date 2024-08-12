package com.todocode.bazar.service;

import com.todocode.bazar.dto.VentaClienteDTO;
import com.todocode.bazar.dto.VentaDelDiaDTO;
import com.todocode.bazar.model.Producto;
import com.todocode.bazar.model.Venta;
import java.util.List;
import java.time.LocalDate;
import java.util.Optional;

public interface IVentaService {

    public List<Venta> getVentas();

    public Venta findVenta(Long codigo_venta);

    public void saveVenta(Venta venta);

    public void deleteVenta(Long codigo_venta);

    public Venta updateVenta(Long codigo_venta, Venta ventaDetalle);

    public List<Producto> getProductosByCodigoVenta(Long codigoVenta);

    public VentaDelDiaDTO getVentaDelDia(LocalDate fecha_venta);
    
    public VentaClienteDTO obtenerVentaConMontoMasAlto();
}
