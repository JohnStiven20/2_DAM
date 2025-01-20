package app.Controladores;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import app.Enums.Size;
import app.MetodosPagos.PagarTarjeta;
import app.Modelo.Bebida;
import app.Modelo.Cliente;
import app.Modelo.LineaPedido;
import app.Modelo.Pasta;
import app.Modelo.Pedido;
import app.Modelo.Pedido.EstadoPedido;
import app.Modelo.Pizza;
import app.Modelo.Producto;

public class ContraladorPedidoTest {

    private ContraladorPedido contraladorPedido = null;
    private ControladorCliente controladorCliente = null;

    @BeforeEach
    void setUp() {
        contraladorPedido = new ContraladorPedido();
        controladorCliente = new ControladorCliente();
        insetarDatos();
    }

    @Test
    void testAddOrderLine() {

        Cliente cliente = new Cliente(
                "98765432Z",
                "Carlos",
                "690123456",
                "carlos.mendez@example.com",
                "Avenida Central 123",
                "newSecurePassword",
                new ArrayList<>(),
                true,
                "Méndez");

        ArrayList<LineaPedido> lineaPedidos = new ArrayList<>();

        Pizza pizza = new Pizza("Pizza Pepperoni", 12.5, Size.MEDIANO, null);
        Pasta pasta = new Pasta("Pasta Alfredo", 9.99, Size.ENANO, null);
        Bebida bebida = new Bebida("Refresco Cola", 2.5, Size.GRANDE);

        lineaPedidos.add(new LineaPedido(2, pizza));
        lineaPedidos.add(new LineaPedido(3, pasta));
        lineaPedidos.add(new LineaPedido(1, bebida));

        Pedido pedido = new Pedido(
                EstadoPedido.PEDIENTE,
                lineaPedidos,
                cliente, null);

        try {
            contraladorPedido.save(pedido);

            Cliente clienteBaseDatos = controladorCliente.getAllCusturmers().get(1);

            contraladorPedido.addOrderLine(pizza, 5, clienteBaseDatos);

            Producto producto = clienteBaseDatos.getListaPedidos().stream()
                    .flatMap(pedidoStream -> pedidoStream.getLineaPedidos().stream())
                    .map(LineaPedido::getProducto)
                    .findFirst()
                    .orElse(null);

            assertEquals(producto.getNombre(), "Pizza Pepperoni");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void testCancelarPedido() {

        Cliente clienteBaseDatos;
        try {
            clienteBaseDatos = controladorCliente.getAllCusturmers().stream().findFirst().orElse(null);

            contraladorPedido.cancelarPedido(clienteBaseDatos);

            Pedido pedidoCliente = contraladorPedido.getOrdersByCustumer(clienteBaseDatos).stream().findFirst().get();

            assertEquals(pedidoCliente.getEstado(), EstadoPedido.CANCELADO);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void testEntregarPedido() {

        Cliente clienteBaseDatos;
        try {
            clienteBaseDatos = controladorCliente.getAllCusturmers().stream()
                    .filter(usuario -> usuario.getEmail().equals("ana.nueva@example.com")).findFirst().orElse(null);

            contraladorPedido.entregarPedido(clienteBaseDatos);

            Pedido pedidoCliente = contraladorPedido.getOrdersByCustumer(clienteBaseDatos).stream().findFirst().get();

            assertEquals(pedidoCliente.getEstado(), EstadoPedido.ENTREGADO);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    void testFinalizarPedido() {
        Cliente clienteBaseDatos;
        try {
            clienteBaseDatos = controladorCliente.getAllCusturmers().stream()
                    .findFirst().orElse(null);

            contraladorPedido.finalizarPedido(new PagarTarjeta(), clienteBaseDatos);

            Pedido pedidoCliente = contraladorPedido.getOrdersByCustumer(clienteBaseDatos).stream().findFirst().get();

            assertEquals(pedidoCliente.getPagable().getClass().getSimpleName(), "PagarTarjeta");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    void testGetLineasOrdersByOrder() {

        try {

            Cliente clienteBaseDatos = controladorCliente.getAllCusturmers().stream()
                    .findFirst()
                    .orElse(null);

            Pedido pedidoCliente = clienteBaseDatos.getListaPedidos().stream().findFirst().get();

            contraladorPedido.getLineasOrdersByOrder(pedidoCliente).forEach(x -> System.out.println(x));

            int distancia = contraladorPedido.getLineasOrdersByOrder(pedidoCliente).size();

            assertEquals(distancia, 3);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    void testGetOrdersByCustumer() {

        try {

            Cliente clienteBaseDatos = controladorCliente.getAllCusturmers().stream()
                    .findFirst()
                    .orElse(null);

            List<Pedido> pedidoCliente = contraladorPedido.getOrdersByCustumer(clienteBaseDatos);

            assertEquals(pedidoCliente.size(), 1);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    void testGetOrdersByStatus() {

        try {
            Cliente clienteBaseDatos = controladorCliente.getAllCusturmers().stream()
                    .findFirst()
                    .orElse(null);

            Pedido pedidoCliente = contraladorPedido.getOrdersByStatus(EstadoPedido.PEDIENTE, clienteBaseDatos).stream()
                    .findFirst().orElse(null);

            assertEquals(pedidoCliente.getEstado(), EstadoPedido.PEDIENTE);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void insetarDatos() {

        Cliente cliente = new Cliente(
                "67890123F",
                "Ana",
                "650987321",
                "ana.nueva@example.com",
                "Calle Nueva 456",
                "securePassword",
                new ArrayList<>(),
                false,
                "Gómez");

        ArrayList<LineaPedido> lineaPedidos = new ArrayList<>();

        Pizza pizza = new Pizza("Pizza", 75, Size.GRANDE, null);
        Pasta pasta = new Pasta("Pasta", 4, Size.MEDIANO, null);
        Bebida bebida = new Bebida("Bebida", 56, Size.GRANDE);

        lineaPedidos.add(new LineaPedido(4, pizza));
        lineaPedidos.add(new LineaPedido(50, pasta));
        lineaPedidos.add(new LineaPedido(78, bebida));

        Pedido pedido = new Pedido(EstadoPedido.PEDIENTE, lineaPedidos, cliente, null);

        try {
            contraladorPedido.save(pedido);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
