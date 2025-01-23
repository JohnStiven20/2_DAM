package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.model.Cliente;
import com.example.demo.repository.ClienteRepository;

public class ClienteServiceImpl implements ClienteService{

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public Cliente save(Cliente empleado) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente findClienteById(long id) {
       return clienteRepository.findById(id).orElseThrow(() -> 
       new ResponseStatusException(HttpStatus.NOT_FOUND, "El cliente no encontrado id"));
    }

    @Override
    public Cliente findClienteByDni(String dni) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findClienteByDni'");
    }

    @Override
    public Cliente updateCliente(Cliente cliente) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateCliente'");
    }

    @Override
    public void deleteCliente(long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteCliente'");
    }

    @Override
    public List<Cliente> findByMarca(String marca) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByMarca'");
    }
    
}
