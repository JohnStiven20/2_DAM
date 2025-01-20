package app.Controladores;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import app.Enums.Size;
import app.Modelo.Bebida;
import app.Modelo.Cliente;
import app.Modelo.LineaPedido;
import app.Modelo.Pasta;
import app.Modelo.Pedido;
import app.Modelo.Pedido.EstadoPedido;
import app.Modelo.Pizza;

public class ContraladorPedidoTest {

    private ContraladorPedido contraladorPedido = null;

    @BeforeEach
    void setUp() {
        contraladorPedido = new ContraladorPedido();
    }

    @Test
    void testAddOrderLine() {

        Cliente cliente = new Cliente(
                "67890123F", 
                "Ana", 
                "650987321", 
                "ana.nueva@example.com", 
                "Calle Nueva 456", 
                "securePassword", 
                new ArrayList<>(),
                false, 
                "Gómez"
        );

        ArrayList<LineaPedido> lineaPedidos = new ArrayList<>();

        Pizza pizza = new Pizza("Pizza", 75, Size.GRANDE, null);
        Pasta pasta = new Pasta("Pasta", 4, Size.MEDIANO, null);
        Bebida bebida = new Bebida("Bebida", 56, Size.GRANDE);

        lineaPedidos.add(new LineaPedido(4, pizza));
        lineaPedidos.add(new LineaPedido(50, pasta));
        lineaPedidos.add(new LineaPedido(78, bebida));

        Pedido pedido = new Pedido(EstadoPedido.ENTREGADO, lineaPedidos, null, null);

        try {
            contraladorPedido.save(pedido);

            contraladorPedido.addOrderLine(pizza, 5, cliente);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    void testCancelarPedido() {

    }

    @Test
    void testEntregarPedido() {

    }

    @Test
    void testFinalizarPedido() {

    }

    @Test
    void testGetLineasOrdersByOrder() {

        Cliente cliente = new Cliente(
                "67890123F", 
                "Ana", 
                "650987321", 
                "ana.nueva@example.com", 
                "Calle Nueva 456",
                "securePassword",
                new ArrayList<>(), 
                false,
                "Gómez" 
        );

        ArrayList<LineaPedido> lineaPedidos = new ArrayList<>();

        Pizza pizza = new Pizza("Pizza", 75, Size.GRANDE, null);
        Pasta pasta = new Pasta("Pasta", 4, Size.MEDIANO, null);
        Bebida bebida = new Bebida("Bebida", 56, Size.GRANDE);

        lineaPedidos.add(new LineaPedido(4, pizza));
        lineaPedidos.add(new LineaPedido(50, pasta));
        lineaPedidos.add(new LineaPedido(78, bebida));

        Pedido pedido = new Pedido(EstadoPedido.ENTREGADO, lineaPedidos, cliente, null);

        try {
            contraladorPedido.save(pedido);

            contraladorPedido.getLineasOrdersByOrder(pedido);

        } catch (SQLException e) { 
            System.out.println(e.getMessage());
        }
           
    }

    @Test
    void testGetOrdersByCustumer() {

        Cliente cliente = new Cliente(
                "67890123F", 
                "Ana", 
                "650987321",
                "ana.nueva@example.com", 
                "Calle Nueva 456", 
                "securePassword", 
                new ArrayList<>(), 
                false, 
                "Gómez" 
        );

        ArrayList<LineaPedido> lineaPedidos = new ArrayList<>();

        Pizza pizza = new Pizza("Pizza", 75, Size.GRANDE, null);
        Pasta pasta = new Pasta("Pasta", 4, Size.MEDIANO, null);
        Bebida bebida = new Bebida("Bebida", 56, Size.GRANDE);

        lineaPedidos.add(new LineaPedido(4, pizza));
        lineaPedidos.add(new LineaPedido(50, pasta));
        lineaPedidos.add(new LineaPedido(78, bebida));

        Pedido pedido = new Pedido(EstadoPedido.ENTREGADO, lineaPedidos, null, null);

        try {
            contraladorPedido.save(pedido);

            List<Pedido> pedidoCliente = contraladorPedido.getOrdersByCustumer(cliente);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    void testGetOrdersByStatus() {

        Cliente cliente = new Cliente(
                "67890123F", 
                "Ana", 
                "650987321",
                "ana.nueva@example.com", 
                "Calle Nueva 456", 
                "securePassword", 
                new ArrayList<>(), 
                false, 
                "Gómez" 
        );

        ArrayList<LineaPedido> lineaPedidos = new ArrayList<>();

        Pizza pizza = new Pizza("Pizza", 75, Size.GRANDE, null);
        Pasta pasta = new Pasta("Pasta", 4, Size.MEDIANO, null);
        Bebida bebida = new Bebida("Bebida", 56, Size.GRANDE);

        lineaPedidos.add(new LineaPedido(4, pizza));
        lineaPedidos.add(new LineaPedido(50, pasta));
        lineaPedidos.add(new LineaPedido(78, bebida));

        Pedido pedido = new Pedido(EstadoPedido.ENTREGADO, lineaPedidos, null, null);

        try {
            contraladorPedido.save(pedido);

            List<Pedido> pedidosCliente = contraladorPedido.getOrdersByStatus(EstadoPedido.PEDIENTE, cliente);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }
}
