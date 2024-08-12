package com.todocode.bazar.service;

import com.todocode.bazar.exceptions.ResourceNotFoundException;
import com.todocode.bazar.model.Cliente;
import com.todocode.bazar.repository.IClienteRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService implements IClienteService {

    @Autowired
    private IClienteRepository clienteRepo;

    @Override
    public List<Cliente> getClientes() {
        List<Cliente> listaClientes = clienteRepo.findAll();
        return listaClientes;
    }

    @Override
    public Cliente findCliente(Long id_cliente) {
        return clienteRepo.findById(id_cliente)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + id_cliente));
    }

    @Override
    public void saveCliente(Cliente cliente) {
        clienteRepo.save(cliente);
    }

    @Override
    public void deleteCliente(Long id_cliente) {
        Cliente cliente = clienteRepo.findById(id_cliente)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + id_cliente));
        clienteRepo.delete(cliente);
    }

    @Override
    public Cliente updateCliente(Long id_cliente, Cliente clienteDetalle) {
        return clienteRepo.findById(id_cliente)
                .map(cliente -> {
                    cliente.setNombre(clienteDetalle.getNombre());
                    cliente.setApellido(clienteDetalle.getApellido());
                    cliente.setDni(clienteDetalle.getDni());
                    // Actualiza otros campos según sea necesario
                    return clienteRepo.save(cliente);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + id_cliente));
    }

}
