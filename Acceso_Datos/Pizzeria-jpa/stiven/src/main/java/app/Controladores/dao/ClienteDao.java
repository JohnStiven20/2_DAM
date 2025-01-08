package app.Controladores.dao;

import java.sql.SQLException;
import java.util.List;

import app.Modelo.Cliente;

public interface ClienteDao {

    void delete(Cliente cliente) throws SQLException;

    void update(Cliente cliente) throws SQLException;

    boolean save(Cliente cliente) throws SQLException;

    Cliente getClienteByEmail(String gmail) throws SQLException;

    List<Cliente> getAllCustomers() throws SQLException;


}