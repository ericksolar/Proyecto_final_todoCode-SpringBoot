package com.todocode.bazar.service;

import com.todocode.bazar.exceptions.ResourceNotFoundException;
import com.todocode.bazar.model.Producto;
import com.todocode.bazar.repository.IProductoRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoService implements IProductoService {

    @Autowired
    private IProductoRepository productRepo;

    @Override
    public List<Producto> getProductos() {
        List<Producto> listaProduct = productRepo.findAll();
        return listaProduct;
    }

    @Override
    public Producto findProducto(Long codigo_producto) {
        return productRepo.findById(codigo_producto)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con código: " + codigo_producto));
    }

    @Override
    public void saveProducto(Producto producto) {
        productRepo.save(producto);
    }

    @Override
    public void deleteProducto(Long codigo_producto) {
        productRepo.deleteById(codigo_producto);
    }

    public Producto updateProducto(Long codigo_producto, Producto productoDetalle) {
        return productRepo.findById(codigo_producto)
                .map(producto -> {
                    producto.setNombre(productoDetalle.getNombre());
                    producto.setMarca(productoDetalle.getMarca());
                    producto.setCosto(productoDetalle.getCosto());
                    producto.setCantidad_disponible(productoDetalle.getCantidad_disponible());
                    return productRepo.save(producto);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con código: " + codigo_producto));
    }

    public List<Producto> getProductosBajoStock() {
        List<Producto> productos = getProductos();
        return productos.stream()
                .filter(producto -> producto.getCantidad_disponible()< 5)
                .collect(Collectors.toList());
    }

}
