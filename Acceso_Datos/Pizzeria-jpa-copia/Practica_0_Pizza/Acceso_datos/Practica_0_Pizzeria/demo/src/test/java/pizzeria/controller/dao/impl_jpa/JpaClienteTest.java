package pizzeria.controller.dao.impl_jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import pizzeria.Controller.dao.impl_jpa.JpaCliente;
import pizzeria.Modelo.Alergeno;
import pizzeria.Modelo.Cliente;
import pizzeria.Modelo.EstadoPedido;
import pizzeria.Modelo.Ingredientes;
import pizzeria.Modelo.LineaPedido;
import pizzeria.Modelo.Pedido;
import pizzeria.Modelo.Pizza;
import pizzeria.Modelo.Producto;
import pizzeria.Modelo.SizeApp;

public class JpaClienteTest {

    JpaCliente jpaCliente = new JpaCliente();

    @Test
    void testDelete() {

    }

    @Test
    void testFinClienteByEmail() throws ClassNotFoundException, SQLException {

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

        LineaPedido lineaPedido = new LineaPedido(2, pizza);

        // Crear lista de líneas de pedido
        List<LineaPedido> listaLineaPedidos = new ArrayList<>();
        listaLineaPedidos.add(lineaPedido);

        // Crear pedido
        Pedido pedido = new Pedido(new Date(), EstadoPedido.PENDIENTE, listaLineaPedidos);

        List<Pedido> pedidos = new ArrayList<Pedido>();
        pedidos.add(pedido);

        Cliente cliente2 = new Cliente(
                "12342121678A", // DNI
                "Juan Pérez30", // Nombre
                "Calle Falsa 123", // Dirección
                "987654321", // Teléfono
                "juan.perez@mail.com", // Email
                "password43", // Contraseña
                pedidos

        );

        jpaCliente.save(cliente2);

        Cliente cliente3 = jpaCliente.finClienteByEmail("juat.perez@mail.com");

        assertEquals(cliente3.getNombre(), "Juan Pérez30");

    }

    @Test
    void testGetAllCliente() throws ClassNotFoundException, SQLException {


        // assertEquals(clientes.size(), 1);

    }

    @Test
    void testLogin() throws ClassNotFoundException, SQLException {

        testSave();
        Cliente cliente = jpaCliente.login("password43", "Juan Pérez30");

        assertEquals("password43", cliente.getPassword());

    }

    @Test
    void testSave() throws ClassNotFoundException, SQLException {

        Cliente cliente2 = new Cliente(
                "12342121678A", // DNI
                "Juan Pérez30", // Nombre
                "Calle Falsa 123", // Dirección
                "987654321", // Teléfono
                "juan.perez@mail.com", // Email
                "password43", // Contraseña
                true);

        jpaCliente.save(cliente2);

    }

    @Test
    void testUpdate() {

    }
}
