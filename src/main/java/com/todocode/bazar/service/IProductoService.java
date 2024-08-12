package com.todocode.bazar.service;

import com.todocode.bazar.model.Producto;
import java.util.List;

public interface IProductoService {

    public List<Producto> getProductos();

    public Producto findProducto(Long codigo_producto);

    public void saveProducto(Producto producto);

    public void deleteProducto(Long codigo_producto);

    public Producto updateProducto(Long codigo_producto, Producto productoDetalle);

    public List<Producto> getProductosBajoStock();

}
