package com.todocode.bazar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VentaClienteDTO {
    
    private Long codigo_venta;
    private Double total;
    private int  cant_productos;
    private String nombre_cliente;
    private String apellido_cliente;
}
