package pizzeria.controller.dao.impl;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pizzeria.Controller.ContraladorCliente;
import pizzeria.Controller.dao.impl.JdbcPedidoDAO;
import pizzeria.Exceptions.ExceptionFoundCliente;
import pizzeria.Modelo.Alergeno;
import pizzeria.Modelo.Cliente;
import pizzeria.Modelo.EstadoPedido;
import pizzeria.Modelo.Ingredientes;
import pizzeria.Modelo.LineaPedido;
import pizzeria.Modelo.Pagable;
import pizzeria.Modelo.PagarEfectivo;
import pizzeria.Modelo.Pedido;
import pizzeria.Modelo.Pizza;
import pizzeria.Modelo.Producto;
import pizzeria.Modelo.SizeApp;

public class JdbcPedidoDAOTest {

        private JdbcPedidoDAO jdbcPedidoDAO;
        private ContraladorCliente contraladorCliente;

        @BeforeEach
        void setUp() {
                jdbcPedidoDAO = new JdbcPedidoDAO();
                contraladorCliente = new ContraladorCliente();
        }

        @Test
        void testSave() throws ClassNotFoundException, SQLException, ExceptionFoundCliente {

                Cliente cliente = new Cliente(
                                "12345678A", // DNI
                                "Juan Pérez", // Nombre
                                "Calle Falsa 123", // Dirección
                                "987654321", // Teléfono
                                "juan.perez@mail.com", // Email
                                "password123", // Contraseña
                                false // Admin (false = no es administrador)
                );

                // contraladorCliente.registrarCliente(cliente);

                Cliente clienteExiste = contraladorCliente.loginCliente("Juan Pérez", "password123");

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
                Pizza pizza = new Pizza(1, "Margarita", 12.5, SizeApp.MEDIANO, ingredientesPizza);
                LineaPedido lineaPedido = new LineaPedido(2, pizza);

                // Crear lista de líneas de pedido
                List<LineaPedido> listaLineaPedidos = new ArrayList<>();
                listaLineaPedidos.add(lineaPedido);

                // Crear pedido
                Pedido pedido = new Pedido(new Date(), EstadoPedido.PENDIENTE, listaLineaPedidos,
                                new Cliente("12345678A", "Test Cliente", "Calle Falsa 123", "987654321",
                                                "test@example.com", "password",
                                                false));

                // Guardar pedido
                jdbcPedidoDAO.save(clienteExiste.getId(), pedido);

                // Verificar que se guardó correctamente
                // List<Pedido> pedidos =
                // jdbcPedidoDAO.getAllPedidoByIdCliente(clienteExiste.getId());
                // assertFalse(pedidos.isEmpty());
                // assertEquals(EstadoPedido.ENTREGADO, pedidos.get(0).getEstado());
        }

        @Test
        void testUpdatePedidoEstado() throws ClassNotFoundException, SQLException, ExceptionFoundCliente {

                Cliente cliente = new Cliente(
                                "13121231", // DNI
                                "Juan Pérez12", // Nombre
                                "Calle Falsa 123", // Dirección
                                "987654321", // Teléfono
                                "juansolo@gmail.com", // Email
                                "password", // Contraseña
                                false // Admin (false = no es administrador)
                );

                // contraladorCliente.registrarCliente(cliente);

                Cliente clienteExiste = contraladorCliente.loginCliente("Juan Pérez12", "password");

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
                Pizza pizza = new Pizza(1, "Margarita", 12.5, SizeApp.MEDIANO, ingredientesPizza);
                LineaPedido lineaPedido = new LineaPedido(2, pizza);

                // Crear lista de líneas de pedido
                List<LineaPedido> listaLineaPedidos = new ArrayList<>();
                listaLineaPedidos.add(lineaPedido);
                Pedido pedido = new Pedido(new Date(), EstadoPedido.PENDIENTE, listaLineaPedidos);

                jdbcPedidoDAO.save(clienteExiste.getId(), pedido);

                List<Pedido> pedidos = jdbcPedidoDAO.getAllPedidoByIdCliente(clienteExiste.getId());

                List<Pedido> listapedidos = jdbcPedidoDAO.getAllPedidoByEstado(EstadoPedido.PENDIENTE, clienteExiste);

                Pedido pedidoExiste = listapedidos.stream()
                                .filter((itemPedido) -> itemPedido.getEstado() == EstadoPedido.PENDIENTE).findFirst()
                                .orElse(null);
                // Actualizar estado del pedido

                jdbcPedidoDAO.update(pedidoExiste);

                // // Verificar el cambio de estado
                // List<Pedido> listapedidos = jdbcPedidoDAO.getAllPedidoByIdCliente(1);
                // assertEquals(EstadoPedido.ENTREGADO, pedidos.get(0).getEstado());
        }

        @Test
        void testGetAllPedidoByEstado() throws ClassNotFoundException, SQLException, ExceptionFoundCliente {

                Cliente cliente = new Cliente(
                                "13121231212", // DNI
                                "Juan Pérez15", // Nombre
                                "Calle Falsa 123", // Dirección
                                "987654321", // Teléfono
                                "juansolo@gmaidql.com", // Email
                                "password1", // Contraseña
                                false // Admin (false = no es administrador)
                );

                contraladorCliente.registrarCliente(cliente);

                Cliente clienteExiste = contraladorCliente.loginCliente("Juan Pérez15", "password1");

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
                Pizza pizza = new Pizza(1, "Margarita", 12.5, SizeApp.MEDIANO, ingredientesPizza);
                LineaPedido lineaPedido = new LineaPedido(2, pizza);

                // Crear lista de líneas de pedido
                List<LineaPedido> listaLineaPedidos = new ArrayList<>();
                listaLineaPedidos.add(lineaPedido);
                // Crear pedido
                Pedido pedido = new Pedido(new Date(), EstadoPedido.ENTREGADO, listaLineaPedidos);

                jdbcPedidoDAO.save(clienteExiste.getId(), pedido);

                // Obtener todos los pedidos por estado
                List<Pedido> pedidos = jdbcPedidoDAO.getAllPedidoByEstado(EstadoPedido.ENTREGADO, clienteExiste);

                // Verificar que el pedido se recuperó correctamente
                assertFalse(pedidos.isEmpty());
                assertEquals(EstadoPedido.ENTREGADO, pedidos.get(0).getEstado());
        }

        @Test
        void testDelete() throws ClassNotFoundException, SQLException, ExceptionFoundCliente {

                Cliente cliente = new Cliente(
                                "213121312", // DNI
                                "Juan Pérez11", // Nombre
                                "Calle Falsa 123", // Dirección
                                "987654321", // Teléfono
                                "juansolo@g212132l.com", // Email
                                "password4", // Contraseña
                                false // Admin (false = no es administrador)
                );

                // contraladorCliente.registrarCliente(cliente);

                Cliente clienteExiste = contraladorCliente.loginCliente("Juan Pérez11", "password4");

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
                Pizza pizza = new Pizza(1, "Margarita", 12.5, SizeApp.MEDIANO, ingredientesPizza);
                LineaPedido lineaPedido = new LineaPedido(2, pizza);

                // Crear lista de líneas de pedido
                List<LineaPedido> listaLineaPedidos = new ArrayList<>();
                listaLineaPedidos.add(lineaPedido);
                // Crear pedido
                Pedido pedido = new Pedido(new Date(), EstadoPedido.ENTREGADO, listaLineaPedidos);

                jdbcPedidoDAO.save(clienteExiste.getId(), pedido);

                // Obtener todos los pedidos por estado
                List<Pedido> pedidos = jdbcPedidoDAO.getAllPedidoByEstado(EstadoPedido.ENTREGADO, clienteExiste);
                // Eliminar pedido
                Pedido pedidoExiste = pedidos.stream()
                                .filter((itemPedido) -> itemPedido.getEstado() == EstadoPedido.ENTREGADO).findFirst()
                                .orElse(null);
                // Actualizar estado del pedido

                jdbcPedidoDAO.delete(pedidoExiste.getId());
                List<Pedido> pedidosExiste = jdbcPedidoDAO.getAllPedidoByEstado(EstadoPedido.PENDIENTE, clienteExiste);
                // Verificar que se eliminó
                assertTrue(pedidosExiste.isEmpty());
        }
}
