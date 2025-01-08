package app.Controladores.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import app.Controladores.dao.ClienteDao;
import app.Modelo.Cliente;
import static app.Modelo.utils.DatabaseConfPizzeria.DELETE_CLIENTE;
import static app.Modelo.utils.DatabaseConfPizzeria.INSERT_CLIENTE;
import static app.Modelo.utils.DatabaseConfPizzeria.PASS;
import static app.Modelo.utils.DatabaseConfPizzeria.SELECT_CLIENTE;
import static app.Modelo.utils.DatabaseConfPizzeria.SELECT_CLIENTE_ALL;
import static app.Modelo.utils.DatabaseConfPizzeria.URL;
import static app.Modelo.utils.DatabaseConfPizzeria.USER;

public class JdbcClienteDao implements ClienteDao {

    @Override
    public void delete(Cliente cliente) throws SQLException {
        try (Connection connection = getConnection(); 
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CLIENTE);) {
            preparedStatement.setInt(1, cliente.getId());
            preparedStatement.execute();
        }
    }

    @Override
    public boolean save(Cliente cliente) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CLIENTE,
                    Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, cliente.getDni());
            preparedStatement.setString(2, cliente.getNombre());
            preparedStatement.setString(3, cliente.getTelefono());
            preparedStatement.setString(4, cliente.getApellidos());
            preparedStatement.setString(5, cliente.getDireccion());
            preparedStatement.setString(6, cliente.getPassword());
            preparedStatement.setBoolean(7, cliente.getAdmin());
            preparedStatement.setString(8, cliente.getEmail());

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public Cliente getClienteByEmail(String findEmail) throws SQLException {

        Cliente cliente = null;

        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CLIENTE);
            preparedStatement.setString(1, findEmail); // Usamos el parámetro findEmail

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) { // Si encontramos un cliente

                    int id = resultSet.getInt("cliente.id");
                    String dni = resultSet.getString("cliente.dni");
                    String nombre = resultSet.getString("cliente.nombre");
                    String apellidos = resultSet.getString("cliente.apellidos");
                    String direccion = resultSet.getString("cliente.direccion");
                    String password = resultSet.getString("cliente.password");
                    Boolean admin = resultSet.getBoolean("cliente.admin");
                    String email = resultSet.getString("cliente.email");

                    // Creamos el objeto cliente con los datos obtenidos
                    cliente = new Cliente(id, dni, nombre, direccion, email, email, password, null, admin, apellidos);
                }
            }
        }

        return cliente; // Si no se encuentra, retorna null
    }

    @Override
    public List<Cliente> getAllCustomers() throws SQLException {

        List<Cliente> listaClientes = new ArrayList<>();

        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CLIENTE_ALL);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {

                    int id = resultSet.getInt("cliente.id");
                    String dni = resultSet.getString("cliente.dni");
                    String nombre = resultSet.getString("cliente.nombre");
                    String apellidos = resultSet.getString("cliente.apellidos");
                    String dirrecion = resultSet.getString("cliente.direccion");
                    String password = resultSet.getString("cliente.password");
                    Boolean admin = resultSet.getBoolean("cliente.admin");
                    String email = resultSet.getString("cliente.email");
                    String telefono = resultSet.getString("cliente.telefono");

                    listaClientes.add(
                            new Cliente(id, dni, nombre, telefono, email, password, dirrecion, null, admin, apellidos));

                }
            }
        }

        return listaClientes;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    @Override
    public void update(Cliente cliente) throws SQLException {

        // try (Connection connection = getConnection()) {
        //     PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CLIENTE);
        //     preparedStatement.setString(1, dirrecion);
        //     preparedStatement.setString(2, apellidos);
        //     preparedStatement.setString(3, telefono);
        //     preparedStatement.setInt(4, cliente.getId());
        //     preparedStatement.execute();

        // }
    }
}
