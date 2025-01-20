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

    public void update(Cliente cliente) throws SQLException {
        clienteDao.update(cliente);
    }

    public Cliente loginCustomer(String gmail, String nombre) throws SQLException {
        JpaClienteDao jpaClienteDao = ((JpaClienteDao) clienteDao);
        return  jpaClienteDao.getClienteByEmailAndName(nombre, gmail);
    }


}
