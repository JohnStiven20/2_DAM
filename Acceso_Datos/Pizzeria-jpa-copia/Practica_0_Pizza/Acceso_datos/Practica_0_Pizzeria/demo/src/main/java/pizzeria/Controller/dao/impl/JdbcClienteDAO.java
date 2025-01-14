package pizzeria.Controller.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pizzeria.Controller.dao.InnerClienteDAO;
import pizzeria.Modelo.Cliente;
import pizzeria.Modelo.Conexion;
import static pizzeria.utils.DatabaseConfig.*;

public class JdbcClienteDAO implements InnerClienteDAO {

    @Override
    public void save(Cliente cliente) throws SQLException, ClassNotFoundException {
        try (Connection con = new Conexion().getConexion();) {
            PreparedStatement preparedStatement = con.prepareStatement(INNER_CLIENTE);
            preparedStatement.setString(1, cliente.getDni());
            preparedStatement.setString(2, cliente.getNombre());
            preparedStatement.setString(3, cliente.getDireccion());
            preparedStatement.setString(4, cliente.getEmail());
            preparedStatement.setString(5, cliente.getPassword());
            preparedStatement.setBoolean(6, cliente.getAdmin());
            preparedStatement.setString(7, cliente.getTelefono());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public Cliente finClienteByEmail(String email) throws SQLException, ClassNotFoundException {

        Cliente cliente = null;
        try (Connection con = new Conexion().getConexion();
                PreparedStatement preparedStatement = con.prepareStatement(SELECT_CLIENTE)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultado = preparedStatement.executeQuery()) {

                if (resultado.next()) {
                    cliente = buildClienteFromResultSet(resultado);
                }
            }
        }
        return cliente;
    }

    @Override
    public Cliente login(String password, String nombre) throws SQLException, ClassNotFoundException {
        Cliente cliente = null;

        // Utiliza solo PreparedStatement sin Statement adicional
        try (Connection con = new Conexion().getConexion();
                PreparedStatement preparedStatement = con.prepareStatement(SELECT_LOGIN_CLIENTE)) {
            con.setAutoCommit(false);
            // Configuración de parámetros en la consulta
            preparedStatement.setString(1, nombre); // Nombre del cliente (suponiendo que sea el email o nombre)
            preparedStatement.setString(2, password); // Contraseña

            try (ResultSet resultado = preparedStatement.executeQuery()) {
                if (resultado.next()) {
                    // Si se encuentra el cliente, construimos el objeto cliente
                    cliente = buildClienteFromResultSet(resultado);
                }
            }
            con.commit();
        }
        return cliente; // Devuelve el cliente si se encontró, o null si no se encontró
    }

    private Cliente buildClienteFromResultSet(ResultSet resultado) throws SQLException {
        int idCliente = resultado.getInt("id");
        String dniCliente = resultado.getString("dni");
        String nombreCliente = resultado.getString("nombre");
        String direccionCliente = resultado.getString("direccion");
        String emailCliente = resultado.getString("email");
        String passwordCliente = resultado.getString("password");
        String telefonoCliente = resultado.getString("telefono");
        Boolean adminCliente = resultado.getBoolean("admin");

        return new Cliente(
                idCliente,
                dniCliente,
                nombreCliente,
                direccionCliente,
                telefonoCliente,
                passwordCliente,
                emailCliente,
                adminCliente);
    }

    @Override
    public void update(Cliente cliente) throws SQLException, ClassNotFoundException {

        try (Connection con = new Conexion().getConexion();
                PreparedStatement preparedStatement = con.prepareStatement(UPDATE_CLIENTE)) {
            preparedStatement.setString(1, cliente.getNombre());
            preparedStatement.setString(2, cliente.getDireccion());
            preparedStatement.setString(3, cliente.getPassword());
            preparedStatement.setBoolean(4, cliente.getAdmin());
            preparedStatement.setString(5, cliente.getTelefono());
            preparedStatement.setInt(6, cliente.getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<Cliente> getAllCliente() throws SQLException, ClassNotFoundException {

        List<Cliente> clientes = new ArrayList<Cliente>();
        Cliente cliente;
        try (Connection con = new Conexion().getConexion();
                PreparedStatement preparedStatement = con.prepareStatement(SELECT_ALL_CLIENTE)) {

            try (ResultSet resultado = preparedStatement.executeQuery()) {

                while (resultado.next()) {
                    cliente = buildClienteFromResultSet(resultado);
                    clientes.add(cliente);
                }
            }
        }
        return clientes;
    }

    @Override
    public void delete(int idCliente) throws SQLException, ClassNotFoundException {
        try (Connection con = new Conexion().getConexion();
                PreparedStatement preparedStatement = con.prepareStatement(DELETE_CLIENTE)) {
            preparedStatement.setInt(1, idCliente);
        }
    }

}