package pizzeria.controller.dao.impl_jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import pizzeria.Controller.dao.impl_jpa.JpaCliente;
import pizzeria.Controller.dao.impl_jpa.JpaLineaPedido;
import pizzeria.Controller.dao.impl_jpa.JpaPedido;
import pizzeria.Modelo.Alergeno;
import pizzeria.Modelo.Cliente;
import pizzeria.Modelo.EstadoPedido;
import pizzeria.Modelo.Ingredientes;
import pizzeria.Modelo.LineaPedido;
import pizzeria.Modelo.Pasta;
import pizzeria.Modelo.Pedido;
import pizzeria.Modelo.Pizza;
import pizzeria.Modelo.SizeApp;

public class JpaPedidoTest {

    JpaCliente jpaCliente = new JpaCliente();
    JpaPedido jpaPedido = new JpaPedido();
    JpaLineaPedido jpaLineaPedido = new JpaLineaPedido();

    @Test
    void testAddCarrito() {

        // jpaLineaPedido.save();

    }

    @Test
    void testDelete() throws ClassNotFoundException, SQLException {
        testSave();
        jpaPedido.delete(1);
    }

    @Test
    void testGetAllPedidoByEstado() throws ClassNotFoundException, SQLException {
        testSave();

        Cliente cliente = jpaCliente.login("password43", "Juan Pérez30");
        List<Pedido> pedidos = jpaPedido.getAllPedidoByEstado(EstadoPedido.ENTREGADO, cliente);
        assertEquals(1, pedidos.size());
    }

    @Test
    void testGetAllPedidoByIdCliente() throws ClassNotFoundException, SQLException {
        testSave();

        List<Pedido> pedidos = jpaPedido.getAllPedidoByIdCliente(1);

        assertEquals(1, pedidos.size());

    }

    @Test
    void testGetListLineaPedido() throws ClassNotFoundException, SQLException {

        testSave();

        List<LineaPedido> lineaPedidos = jpaLineaPedido.getAllLineaPedidonbyID(1);

        assertEquals(2, lineaPedidos.size());

    }

    @Test
    void testSave() throws ClassNotFoundException, SQLException {

        Cliente cliente2 = new Cliente(
                "12342121678A",
                "Juan Pérez30",
                "Calle Falsa 123",
                "987654321",
                "juan.perez@mail.com",
                "password43",
                true);

        jpaCliente.save(cliente2);

        Cliente clienteExiste = jpaCliente.finClienteByEmail(cliente2.getEmail());

        Alergeno soja = new Alergeno("Soja");
        Alergeno frutosSecos = new Alergeno("Frutos Secos");

        Ingredientes tofu = new Ingredientes("Tofu", Arrays.asList(soja));
        Ingredientes nueces = new Ingredientes("Nueces", Arrays.asList(frutosSecos));
        Ingredientes espinaca = new Ingredientes("Espinaca", new ArrayList<>());

        Pizza vegetariana = new Pizza("Pizza Vegetariana", 14.0, SizeApp.GRANDE, Arrays.asList(tofu, nueces, espinaca));
        Pasta alfredo = new Pasta("Pasta Alfredo", 11.0, Arrays.asList(espinaca));

        LineaPedido linea5 = new LineaPedido(2, vegetariana);
        LineaPedido linea6 = new LineaPedido(1, alfredo);

        Pedido pedido = new Pedido(new Date(), EstadoPedido.ENTREGADO, Arrays.asList(linea5, linea6));

        jpaPedido.save(clienteExiste.getId(), pedido);

    }

    @Test
    void testUpdate() throws ClassNotFoundException, SQLException {

        testSave();

        List<Pedido> pedidos = jpaPedido.getAllPedidoByIdCliente(1);

        Pedido pedido = pedidos.get(0);

        pedido.setEstado(EstadoPedido.CANCELADO);

        jpaPedido.update(pedido);

        Pedido pedido2 = jpaPedido.getById(pedido.getId());

        assertEquals(EstadoPedido.CANCELADO, pedido2.getEstado());

    }

}
