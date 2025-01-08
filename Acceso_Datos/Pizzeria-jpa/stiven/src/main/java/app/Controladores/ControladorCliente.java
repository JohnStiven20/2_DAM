package app.Controladores;

import java.sql.SQLException;
import java.util.List;

import app.Controladores.dao.ClienteDao;
import app.Controladores.dao.impl.JpaClienteDao;
import app.Modelo.Cliente;

public class ControladorCliente {

    ClienteDao clienteDao;
    
    public  ControladorCliente() {
        clienteDao = new JpaClienteDao();
        
    }

    public boolean  save(Cliente cliente) throws SQLException {
        return  clienteDao.save(cliente);
    }

    public Cliente findByEmail(String email) throws SQLException {
        return clienteDao.getClienteByEmail(email);
    }

    public void delete(Cliente cliente) throws SQLException {
       clienteDao.delete(cliente);
    }

    public List<Cliente> getAllCusturmers() throws SQLException {
        return clienteDao.getAllCustomers();
    }

    public void update(Cliente cliente, String direccion, String telefono, String apellidos) throws SQLException {
        cliente.setApellidos(apellidos);
        cliente.setDireccion(direccion);
        cliente.setTelefono(telefono);
        clienteDao.update(cliente);
    }

    public void registerCustomer(String dni, String nombre, String telefono, String email,String direccion , String password, Boolean admin, String apellidos) throws SQLException {
        Cliente cliente = new Cliente(dni, nombre, telefono, email, direccion, password, null, admin, apellidos);
        clienteDao.save(cliente);
    }

    public Cliente loginCustomer(String gmail) throws SQLException {
        return  clienteDao.getClienteByEmail(gmail);
    }

}
