package app.Controladores;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import app.Modelo.Cliente;

public class ControladorClienteTest {

    private ControladorCliente controladorCliente;

    @BeforeEach
    void setUp() {
        controladorCliente = new ControladorCliente();
        insertarUsuarios();
        
    }

    @Test
    void testDelete() throws SQLException {

        Cliente cliente = new Cliente(
            "67890123F", // DNI
            "Ana", // Nombre
            "650987321", // Teléfono
            "ana.nueva@example.com", // Email
            "Calle Nueva 456", // Dirección
            "securePassword", // Contraseña
            new ArrayList<>(), // Lista de pedidos vacía
            false, // No es admin
            "Gómez" // Apellidos
    );
        controladorCliente.save(cliente);

        Cliente clienteBaseDatos = controladorCliente.findByEmail("ana.nueva@example.com");

        controladorCliente.delete(clienteBaseDatos);

        Cliente clienteNuevo = controladorCliente.loginCustomer("ana.nueva@example.com", "securePassword");

        assertNotEquals(clienteBaseDatos, clienteNuevo);

    }

    @Test
    void testFindByEmail() throws SQLException {
        Cliente clienteNuevo = new Cliente(
            "67890123F", // DNI
            "Ana", // Nombre
            "650987321", // Teléfono
            "ana.nueva@example.com", // Email
            "Calle Nueva 456", // Dirección
            "securePassword", // Contraseña
            new ArrayList<>(), // Lista de pedidos vacía
            false, // No es admin
            "Gómez" // Apellidos
    );
        controladorCliente.save(clienteNuevo);

        Cliente cliente = controladorCliente.findByEmail("ana.nueva@example.com");

        assertNotNull(cliente);
        assertEquals(clienteNuevo.getEmail(), cliente.getEmail());
    }

    @Test
    void testGetAllCustomers() throws SQLException {

        List<Cliente> clientes = controladorCliente.getAllCusturmers();

        assertEquals(clientes.size(), 5);

        Cliente clienteNuevo = new Cliente(
                "67890123F", // DNI
                "Ana", // Nombre
                "650987321", // Teléfono
                "ana.nueva@example.com", // Email
                "Calle Nueva 456", // Dirección
                "securePassword", // Contraseña
                new ArrayList<>(), // Lista de pedidos vacía
                false, // No es admin
                "Gómez" // Apellidos
        );

        controladorCliente.save(clienteNuevo);

        List<Cliente> clientesNuevos = controladorCliente.getAllCusturmers();

        assertEquals(clientesNuevos.size(),6);

    }

    @Test
    void testLoginCustomer() throws SQLException {

        Cliente cliente = controladorCliente.loginCustomer("juan@example.com", "password123");
        assertNotNull(cliente.getNombre(), "El nombre del cliente no debería ser nulo");
    }

    @Test
    void testRegisterCustomer() throws SQLException {

        Cliente clienteNuevo = new Cliente(
                "67890123F", // DNI
                "Ana", // Nombre
                "650987321", // Teléfono
                "ana.nueva@example.com", // Email
                "Calle Nueva 456", // Dirección
                "securePassword", // Contraseña
                new ArrayList<>(), // Lista de pedidos vacía
                false, // No es admin
                "Gómez" // Apellidos
        );

        // Llamar al método registerCustomer y verificar que se invoque el save del DAO
        controladorCliente.save(clienteNuevo);

        Cliente clienteEsperado = new Cliente("67890123F", "Ana", "650987321", "ana.nueva@example.com", "Calle Nueva 456", "securePassword", new ArrayList<>(), false, "Gómez");

        Cliente cliente = controladorCliente.loginCustomer("ana.nueva@example.com", "securePassword");

        assertEquals(clienteEsperado.getNombre(), cliente.getNombre());

    }

    @Test
    void testSave() throws SQLException {

        Cliente cliente = new Cliente(1, "12345678A", "Juan", "987654321", "juan@email.com", "password", "Calle Falsa 123", null, false, "Pérez");

        boolean estado = controladorCliente.save(cliente);

        assertEquals(estado, true);
    }

    @Test
    void testUpdate() throws SQLException {

        Cliente cliente = controladorCliente.getAllCusturmers().stream().findFirst().get();

        cliente.setTelefono("602547789");
        
        controladorCliente.update(cliente);

        Cliente clienteNuevo = controladorCliente.loginCustomer(cliente.getEmail(), cliente.getPassword());

        assertEquals(cliente.getTelefono(), clienteNuevo.getTelefono());
    }



    public void insertarUsuarios() {
        // Crear una lista de usuarios
        List<Cliente> usuarios = new ArrayList<>();

        usuarios.add(new Cliente(
                "12345678A",
                "Juan",
                "600123456",
                "juan@example.com",
                "Calle Falsa 123",
                "password123",
                new ArrayList<>(),
                false,
                "Pérez"));

        usuarios.add(new Cliente(
                "23456789B",
                "María",
                "610987654",
                "maria@example.com",
                "Avenida Siempre Viva 45",
                "password456",
                new ArrayList<>(),
                false,
                "López"));

        usuarios.add(new Cliente(
                "34567890C",
                "Carlos",
                "620345678",
                "carlos@example.com",
                "Plaza Mayor 10",
                "password789",
                new ArrayList<>(),
                false,
                "García"));

        usuarios.add(new Cliente(
                "45678901D",
                "Laura",
                "630876543",
                "laura@example.com",
                "Calle del Sol 22",
                "password012",
                new ArrayList<>(),
                true,
                "Martínez"));

        usuarios.add(new Cliente(
                "56789012E",
                "Luis",
                "640234567",
                "luis@example.com",
                "Paseo de la Luna 8",
                "password345",
                new ArrayList<>(),
                false,
                "Fernández"));

        for (Cliente usuario : usuarios) {
            try {
                controladorCliente.save(usuario);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

}
