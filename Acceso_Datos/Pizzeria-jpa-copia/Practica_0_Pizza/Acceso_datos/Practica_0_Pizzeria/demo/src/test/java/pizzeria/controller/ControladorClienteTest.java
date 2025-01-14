package pizzeria.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static pizzeria.utils.DatabaseConfig.CREATE_ALL_TABLES;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pizzeria.Controller.ContraladorCliente;
import pizzeria.Exceptions.ExceptionFoundCliente;
import pizzeria.Modelo.Alergeno;
import pizzeria.Modelo.Cliente;
import pizzeria.Modelo.Conexion;
import pizzeria.Modelo.EstadoPedido;
import pizzeria.Modelo.Ingredientes;
import pizzeria.Modelo.LineaPedido;
import pizzeria.Modelo.Pedido;
import pizzeria.Modelo.Pizza;
import pizzeria.Modelo.SizeApp;

public class ControladorClienteTest {

    ContraladorCliente contraladorCliente;

    @BeforeEach
    void setUp() throws ClassNotFoundException, SQLException {
        // CREATE_ALL_TABLES();
        contraladorCliente = new ContraladorCliente();
    }

 

    @Test
    void testInsertarCliente() throws ClassNotFoundException, SQLException, ExceptionFoundCliente {
        String dni = "48642963N";
        String nombre = "Jhon2";
        String apellido = "Solano Macass";
        String telefono = "681207536e";
        String email = "solanomacascristoferr@gmail.com";
        String password = "123453";
        Boolean admin = false;

        Cliente cliente = new Cliente(dni, nombre, apellido, telefono, email, password, admin);
        contraladorCliente.registrarCliente(cliente);

        assertEquals(true, true);
    }

    @Test
    void testEncontrarCLiente() throws ClassNotFoundException, SQLException, ExceptionFoundCliente {

        testInsertarCliente();

        Cliente clienteEncontrado = contraladorCliente.encontrarCliente("solanomacascristoferr@gmail.com");
        assertNotEquals(null, clienteEncontrado);
    }

    @Test
    void testLoginCliente() throws ClassNotFoundException, SQLException, ExceptionFoundCliente {
        testInsertarCliente();
        Cliente clienteLogin = contraladorCliente.loginCliente("Jhon2", "123453");
        assertNotEquals(null, clienteLogin);
    }

    @Test
    void testGetAllCliente() throws ClassNotFoundException, SQLException, ExceptionFoundCliente {

        List<Alergeno> alergenos = new ArrayList<>();
        alergenos.add(new Alergeno("Gluten"));

        Ingredientes tomate = new Ingredientes("Tomate", alergenos);
        Ingredientes queso = new Ingredientes("Queso", new ArrayList<>());

        List<Ingredientes> ingredientesPizza = new ArrayList<>();
        ingredientesPizza.add(tomate);
        ingredientesPizza.add(queso);

        Pizza pizza = new Pizza("Margarita", 12.5, SizeApp.MEDIANO, ingredientesPizza);

        LineaPedido lineaPedido = new LineaPedido(2, pizza);

        List<LineaPedido> listaLineaPedidos = new ArrayList<>();
        listaLineaPedidos.add(lineaPedido);

        Pedido pedido = new Pedido(new Date(), EstadoPedido.PENDIENTE, listaLineaPedidos);

        List<Pedido> pedidos = new ArrayList<Pedido>();
        pedidos.add(pedido);

        Cliente cliente2 = new Cliente(
                "12342121678A",
                "Juan Pérez30",
                "Calle Falsa 123",
                "987654321",
                "juan.perez@mail.com",
                "password43",
                pedidos);

        contraladorCliente.registrarCliente(cliente2);

        List<Cliente> clientes = contraladorCliente.todosClientes();
        assertEquals(1, clientes.size());
    }

    @Test
    void testGetDeleteCliente() throws ClassNotFoundException, SQLException, ExceptionFoundCliente {

        testInsertarCliente();
        Cliente clienteLogin = contraladorCliente.loginCliente("Jhon2", "123453");
        contraladorCliente.eliminarCliente(clienteLogin.getId());

    }

    @Test
    void testActualizarCliente() throws ClassNotFoundException, SQLException, ExceptionFoundCliente {

        Cliente cliente = new Cliente(
                "21412123424212",
                "Juan Pér23412",
                "Calle Falsa 123",
                "987654321",
                "juansolo@g356553.com",
                "password7875",
                false);

        contraladorCliente.registrarCliente(cliente);

        Cliente clienteLogin = contraladorCliente.loginCliente("Juan Pér23412", "password7875");

        clienteLogin.setNombre("paco");
        contraladorCliente.actualizarCliente(clienteLogin);
    }

}
