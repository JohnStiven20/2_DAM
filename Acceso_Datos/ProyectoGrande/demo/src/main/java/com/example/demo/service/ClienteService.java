package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Cliente;

public interface ClienteService {

    Cliente save(Cliente empleado);
    List<Cliente> getAllClientes();
    Cliente findClienteById(long id);
    Cliente findClienteByDni(String dni);
    Cliente updateCliente(Cliente cliente);
    void deleteCliente(long id);
    List<Cliente> findByMarca(String marca);

} 
    


