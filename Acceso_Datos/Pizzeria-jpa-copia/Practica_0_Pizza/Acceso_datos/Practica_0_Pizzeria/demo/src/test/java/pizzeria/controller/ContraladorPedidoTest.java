package pizzeria.controller;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pizzeria.utils.DatabaseConfig.CREATE_ALL_TABLES;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pizzeria.Controller.ContraladorCliente;
import pizzeria.Controller.ContraladorPedido;
import pizzeria.Controller.ControladorProducto;

import pizzeria.Exceptions.ExceptionFoundCliente;
import pizzeria.Modelo.Alergeno;
import pizzeria.Modelo.Cliente;
import pizzeria.Modelo.EstadoPedido;
import pizzeria.Modelo.Ingredientes;
import pizzeria.Modelo.LineaPedido;
import pizzeria.Modelo.PagarTarjeta;
import pizzeria.Modelo.Pedido;
import pizzeria.Modelo.Pizza;
import pizzeria.Modelo.Producto;
import pizzeria.Modelo.SizeApp;

public class ContraladorPedidoTest {

    private ContraladorPedido contraladorPedido;
    private ContraladorCliente contraladorCliente;
    private ControladorProducto controladorProducto;

    @BeforeEach
    void setUp() throws ClassNotFoundException, SQLException {
        contraladorPedido = new ContraladorPedido();
        contraladorCliente = new ContraladorCliente();
        controladorProducto = new ControladorProducto();
    }

    @Test
    void testAddCarrito() throws ClassNotFoundException, SQLException, ExceptionFoundCliente {

        Cliente cliente = new Cliente(
                "14341211678A", 
                "Juan Pérez1895",
                "Calle Falsa 123", 
                "987654321", 
                "juan.perez@24121ail.com", 
                "passwor164",
                false 
        );

        contraladorCliente.registrarCliente(cliente);

        Cliente clienteExiste = contraladorCliente.loginCliente("Juan Pérez1895", "passwor164");
        // Crear ingredientes y alérgenos
        List<Alergeno> alergenos = new ArrayList<>();
        alergenos.add(new Alergeno("Gluten"));

        Ingredientes tomate = new Ingredientes("Tomate", alergenos);
        Ingredientes queso = new Ingredientes("Queso", new ArrayList<>());

        // Crear lista de ingredientes
        List<Ingredientes> ingredientesPizza = new ArrayList<>();
        ingredientesPizza.add(tomate);
        ingredientesPizza.add(queso);

        // Crear pizza y línea de pedido
        Pizza pizza = new Pizza("Margarita", 12.5, SizeApp.MEDIANO, ingredientesPizza);

        controladorProducto.saveProduct(pizza);
        List<Producto> listaPedido = controladorProducto.getAllProducts();
        Producto productoAsignado = listaPedido.stream().filter(t -> t.getNombre().equals("Margarita")).findFirst()
                .orElse(null);

        contraladorPedido.addCarrito(productoAsignado, 13, clienteExiste);
    }

    @Test
    void testCancelarPedido() throws ClassNotFoundException, SQLException, ExceptionFoundCliente {

        Cliente cliente = new Cliente(
                "14312121678A", // DNI
                "Juan Pérez444", // Nombre
                "Calle Falsa 123", // Dirección
                "987654321", // Teléfono
                "juan.perez@243121ail.com", // Email
                "passwor131", // Contraseña
                false // Admin (false = no es administrador)
        );

        contraladorCliente.registrarCliente(cliente);

        Cliente clienteExiste = contraladorCliente.loginCliente("Juan Pérez444", "passwor131");

        // Crear ingredientes y alérgenos
        List<Alergeno> alergenos = new ArrayList<>();
        alergenos.add(new Alergeno("Gluten"));

        Ingredientes tomate = new Ingredientes("Tomate", alergenos);
        Ingredientes queso = new Ingredientes("Queso", new ArrayList<>());

        // Crear lista de ingredientes
        List<Ingredientes> ingredientesPizza = new ArrayList<>();
        ingredientesPizza.add(tomate);
        ingredientesPizza.add(queso);

        // Crear pizza y línea de pedido
        Pizza pizza = new Pizza("Margarita", 12.5, SizeApp.MEDIANO, ingredientesPizza);
        controladorProducto.saveProduct(pizza);
        List<Producto> listaPedido = controladorProducto.getAllProducts();
        Producto productoAsignado = listaPedido.stream().filter(t -> t.getNombre().equals("Margarita")).findFirst()
                .orElse(null);

        LineaPedido lineaPedido = new LineaPedido(2, productoAsignado);

        // Crear lista de líneas de pedido
        List<LineaPedido> listaLineaPedidos = new ArrayList<>();
        listaLineaPedidos.add(lineaPedido);

        // Crear pedido
        Pedido pedido = new Pedido(new Date(), EstadoPedido.PENDIENTE, listaLineaPedidos, clienteExiste);

        // Guardar pedido
        contraladorPedido.savePedido(pedido);

        contraladorPedido.cancelarPedido(clienteExiste);

    }

    @Test
    void testEliminarPedido() throws ClassNotFoundException, SQLException, ExceptionFoundCliente {

        Cliente cliente = new Cliente(
                "21125467112", // DNI
                "Juan Pérez3412", // Nombre
                "Calle Falsa 123", // Dirección
                "987654321", // Teléfono
                "juansolo@g3123232322l.com", // Email
                "password12", // Contraseña
                false // Admin (false = no es administrador)
        );

        contraladorCliente.registrarCliente(cliente);

        Cliente clienteExiste = contraladorCliente.loginCliente("Juan Pérez3412", "password12");

        // Crear pedido
        // Crear ingredientes y alérgenos
        List<Alergeno> alergenos = new ArrayList<>();
        alergenos.add(new Alergeno("Gluten"));

        Ingredientes tomate = new Ingredientes("Tomate", alergenos);
        Ingredientes queso = new Ingredientes("Queso", new ArrayList<>());

        // Crear lista de ingredientes
        List<Ingredientes> ingredientesPizza = new ArrayList<>();
        ingredientesPizza.add(tomate);
        ingredientesPizza.add(queso);

        // Crear pizza y línea de pedido
        Pizza pizza = new Pizza( "Margarita", 12.5, SizeApp.MEDIANO, ingredientesPizza);

        controladorProducto.saveProduct(pizza);
        List<Producto> listaPedido = controladorProducto.getAllProducts();
        Producto productoAsignado = listaPedido.stream().filter(t -> t.getNombre().equals("Margarita")).findFirst()
                .orElse(null);
        LineaPedido lineaPedido = new LineaPedido(2, productoAsignado);

        // Crear lista de líneas de pedido
        List<LineaPedido> listaLineaPedidos = new ArrayList<>();
        listaLineaPedidos.add(lineaPedido);
        // Crear pedido
        Pedido pedido = new Pedido(new Date(), EstadoPedido.PENDIENTE, listaLineaPedidos,
                clienteExiste);

        contraladorPedido.savePedido(pedido);

        // Obtener todos los pedidos por estado
        List<Pedido> pedidos = contraladorPedido.getAllPedidoByEstado(EstadoPedido.PENDIENTE, clienteExiste);
        // Eliminar pedido
        Pedido pedidoExiste = pedidos.stream()
                .filter((itemPedido) -> itemPedido.getEstado() == EstadoPedido.PENDIENTE).findFirst()
                .orElse(null);
        // elimiar el pedido

        contraladorPedido.eliminarPedido(pedidoExiste.getId());
        List<Pedido> pedidosExiste = contraladorPedido.getAllPedidoByEstado(EstadoPedido.PENDIENTE, clienteExiste);
        // Verificar que se eliminó
        assertTrue(pedidosExiste.isEmpty());
    }

    @Test
    void testEntregarPedido() throws ClassNotFoundException, SQLException, ExceptionFoundCliente {

        Cliente cliente = new Cliente(
                "1424323121678A", // DNI
                "Juan Pérez122", // Nombre
                "Calle Falsa 123", // Dirección
                "987654321", // Teléfono
                "juan.perez@2121ail.com", // Email
                "password43", // Contraseña
                false // Admin (false = no es administrador)
        );

        contraladorCliente.registrarCliente(cliente);

        Cliente clienteExiste = contraladorCliente.loginCliente("Juan Pérez122", "password43");

        // Crear ingredientes y alérgenos
        List<Alergeno> alergenos = new ArrayList<>();
        alergenos.add(new Alergeno("Gluten"));

        Ingredientes tomate = new Ingredientes("Tomate", alergenos);
        Ingredientes queso = new Ingredientes("Queso", new ArrayList<>());

        // Crear lista de ingredientes
        List<Ingredientes> ingredientesPizza = new ArrayList<>();
        ingredientesPizza.add(tomate);
        ingredientesPizza.add(queso);

        // Crear pizza y línea de pedido
        Pizza pizza = new Pizza( "Margarita", 12.5, SizeApp.MEDIANO, ingredientesPizza);
        controladorProducto.saveProduct(pizza);
        List<Producto> listaPedido = controladorProducto.getAllProducts();
        Producto productoAsignado = listaPedido.stream().filter(t -> t.getNombre().equals("Margarita")).findFirst()
                .orElse(null);
        LineaPedido lineaPedido = new LineaPedido(2, productoAsignado);

        // Crear lista de líneas de pedido
        List<LineaPedido> listaLineaPedidos = new ArrayList<>();
        listaLineaPedidos.add(lineaPedido);

        // Crear pedido
        Pedido pedido = new Pedido(new Date(), EstadoPedido.PENDIENTE, listaLineaPedidos, clienteExiste);

        // Guardar pedido
        contraladorPedido.savePedido(pedido);

        contraladorPedido.entregarPedido(clienteExiste);
    }

    @Test
    void testFinalizarPedido() throws ClassNotFoundException, SQLException, ExceptionFoundCliente {

        Cliente cliente = new Cliente(
                "142112121678A", // DNI
                "Juan Pérez6432", // Nombre
                "Calle Falsa 123", // Dirección
                "987654321", // Teléfono
                "juan.perez@5451221l.com", // Email
                "password5364", // Contraseña
                false // Admin (false = no es administrador)
        );

        contraladorCliente.registrarCliente(cliente);

        Cliente clienteExiste = contraladorCliente.loginCliente("Juan Pérez6432", "password5364");

        // Crear ingredientes y alérgenos
        List<Alergeno> alergenos = new ArrayList<>();
        alergenos.add(new Alergeno("Gluten"));

        Ingredientes tomate = new Ingredientes("Tomate", alergenos);
        Ingredientes queso = new Ingredientes("Queso", new ArrayList<>());

        // Crear lista de ingredientes
        List<Ingredientes> ingredientesPizza = new ArrayList<>();
        ingredientesPizza.add(tomate);
        ingredientesPizza.add(queso);

        // Crear pizza y línea de pedido
        Pizza pizza = new Pizza( "Margarita", 12.5, SizeApp.MEDIANO, ingredientesPizza);
        controladorProducto.saveProduct(pizza);
        List<Producto> listaPedido = controladorProducto.getAllProducts();
        Producto productoAsignado = listaPedido.stream().filter(t -> t.getNombre().equals("Margarita")).findFirst()
                .orElse(null);
        LineaPedido lineaPedido = new LineaPedido(2, productoAsignado);

        // Crear lista de líneas de pedido
        List<LineaPedido> listaLineaPedidos = new ArrayList<>();
        listaLineaPedidos.add(lineaPedido);

        // Crear pedido
        Pedido pedido = new Pedido(new Date(), EstadoPedido.PENDIENTE, listaLineaPedidos, clienteExiste);

        // Guardar pedido
        contraladorPedido.savePedido(pedido);

        contraladorPedido.finalizarPedido(clienteExiste, new PagarTarjeta());

    }

    @Test
    void testSavePedido() throws ClassNotFoundException, SQLException, ExceptionFoundCliente {
        Cliente cliente = new Cliente(
                "12342121678A",
                "Juan Pérez30", 
                "Calle Falsa 123", 
                "987654321",
                "juan.perez@mail.com", 
                "password43", 
                false 
        );

        contraladorCliente.registrarCliente(cliente);

        Cliente clienteExiste = contraladorCliente.loginCliente("Juan Pérez30", "password43");

        List<Alergeno> alergenos = new ArrayList<>();
        alergenos.add(new Alergeno("Gluten"));

        Ingredientes tomate = new Ingredientes("Tomate", alergenos);
        Ingredientes queso = new Ingredientes("Queso", new ArrayList<>());

        List<Ingredientes> ingredientesPizza = new ArrayList<>();
        ingredientesPizza.add(tomate);
        ingredientesPizza.add(queso);


        Pizza pizza = new Pizza( "Margarita", 12.5, SizeApp.MEDIANO, ingredientesPizza);
        controladorProducto.saveProduct(pizza);
        List<Producto> listaPedido = controladorProducto.getAllProducts();
        Producto productoAsignado = listaPedido.stream().filter(t -> t.getNombre().equals("Margarita")).findFirst()
                .orElse(null);
        LineaPedido lineaPedido = new LineaPedido(2, productoAsignado);


        List<LineaPedido> listaLineaPedidos = new ArrayList<>();
        listaLineaPedidos.add(lineaPedido);

        Pedido pedido = new Pedido(new Date(), EstadoPedido.PENDIENTE, listaLineaPedidos, clienteExiste);

        contraladorPedido.savePedido(pedido);

    }
}
