package app.Controladores;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import app.Modelo.Cliente;
import app.Modelo.utils.DatabaseConfPizzeria;

public class ControladorClienteTest {

    private ControladorCliente controladorCliente;

    @BeforeEach
    void setUp() {
        controladorCliente = new ControladorCliente();
        try {
            DatabaseConfPizzeria.dropTables();
            DatabaseConfPizzeria.createTable();
            DatabaseConfPizzeria.cargarDatosEnLaBaseDatos();
        } catch (SQLException e) {
            System.out.println("Error al configurar la base de datos: " + e.getMessage());
        }
    }


    @Test
    void testDelete() throws SQLException {

        Cliente cliente = new Cliente(1, "12345678A", "Juan", "987654321", "juan@email.com", "password","Calle Falsa 123", null, false, "Pérez");
       
        controladorCliente.save(cliente);

        controladorCliente.delete(cliente);

        Cliente clienteNuevo = controladorCliente.loginCustomer("juan@email.com");

        assertNotEquals(cliente, clienteNuevo);

    }

    @Test
    void testFindByEmail() throws SQLException {
        String email = "juan@email.com";
        Cliente clienteEsperado = new Cliente(1, "12345678A", "Juan", "987654321", email, "password", "Calle Falsa 123", null, false, "Pérez");

        controladorCliente.save(clienteEsperado);

        Cliente cliente = controladorCliente.findByEmail(email);

        assertNotNull(cliente);
        assertEquals(clienteEsperado.getEmail(), cliente.getEmail());
    }

    @Test
    void testGetAllCustomers() throws SQLException {

        List<Cliente> clientes = controladorCliente.getAllCusturmers();

        assertEquals(clientes.size(), 10);

        String dni = "12345678A";
        String nombre = "Juan";
        String telefono = "987654321";
        String email = "juan@email.com";
        String direccion = "Calle Falsa 123";
        String password = "password";
        Boolean admin = false;
        String apellidos = "Pérez";

        controladorCliente.registerCustomer(dni, nombre, telefono, email, direccion, password, admin, apellidos);

        List<Cliente> clientesNuevos = controladorCliente.getAllCusturmers();

        assertEquals(clientesNuevos.size(), 11);
       
    }

    @Test
    void testLoginCustomer() throws SQLException {
        
        Cliente cliente = controladorCliente.loginCustomer("stiven.solano@gmail.com");
        assertNotNull(cliente.getNombre(), "El nombre del cliente no debería ser nulo");
    }

    @Test
    void testRegisterCustomer() throws SQLException {

        String dni = "12345678A";
        String nombre = "Juan";
        String telefono = "987654321";
        String email = "juan@email.com";
        String direccion = "Calle Falsa 123";
        String password = "password";
        Boolean admin = false;
        String apellidos = "Pérez";

        // Llamar al método registerCustomer y verificar que se invoque el save del DAO
        controladorCliente.registerCustomer(dni, nombre, telefono, email, direccion, password, admin, apellidos);

        Cliente clienteEsperado = new Cliente(dni, nombre, telefono, email, direccion, password, null, admin, apellidos);

        Cliente cliente = controladorCliente.loginCustomer(email);

        assertEquals(clienteEsperado.getNombre(), cliente.getNombre());

    }

    @Test
    void testSave() throws SQLException {
        
        Cliente cliente = new Cliente(1, "12345678A", "Juan", "987654321", "juan@email.com", "password","Calle Falsa 123", null, false, "Pérez");

        boolean estado = controladorCliente.save(cliente);

        assertEquals(estado, true);
    }

    @Test
    void testUpdate() throws SQLException {
        
       Cliente cliente = controladorCliente.getAllCusturmers().stream().findFirst().get();
       
        String nuevaDireccion = "Calle Nueva 456";
        String nuevoTelefono = "987654322";
        String nuevosApellidos = "Gómez";

        controladorCliente.update(cliente, nuevaDireccion, nuevoTelefono, nuevosApellidos);

        Cliente clienteNuevo = controladorCliente.loginCustomer(cliente.getEmail());

        assertNotEquals(cliente.getTelefono(), clienteNuevo.getTelefono());
    }
}
