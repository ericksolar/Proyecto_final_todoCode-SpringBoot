package com.todocode.bazar.service;

import com.todocode.bazar.model.Cliente;
import java.util.List;

public interface IClienteService {

    public List<Cliente> getClientes();

    public Cliente findCliente(Long id_cliente);

    public void saveCliente(Cliente cliente);

    public void deleteCliente(Long id_cliente);

    public Cliente updateCliente(Long id_cliente, Cliente clienteDetalle);

}
