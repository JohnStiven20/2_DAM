package app;

import java.sql.SQLException;
import java.util.ArrayList;

import app.Controladores.ContraladorPedido;
import app.Enums.Size;
import app.Modelo.Bebida;
import app.Modelo.Cliente;
import app.Modelo.LineaPedido;
import app.Modelo.Pasta;
import app.Modelo.Pedido;
import app.Modelo.Pedido.EstadoPedido;
import app.Modelo.Pizza;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class MainEnganche {

    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            var entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityTransaction.commit();
        }

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
        ContraladorPedido contraladorPedido = new ContraladorPedido();

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

            cliente.setId(1);

            Pedido pedidoCliente = contraladorPedido.getOrdersByStatus(EstadoPedido.PEDIENTE, cliente).stream().findFirst().orElse(null);
            System.out.println(pedidoCliente.toString());

            contraladorPedido.delete(pedidoCliente);


            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
