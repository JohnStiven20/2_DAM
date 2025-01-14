package app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.Controladores.ControladorCliente;
import app.Enums.Size;
import app.Modelo.Alergeno;
import app.Modelo.Cliente;
import app.Modelo.Ingrediente;
import app.Modelo.LineaPedido;
import app.Modelo.Pedido;
import app.Modelo.Pedido.EstadoPedido;
import app.Modelo.Pizza;
import app.Modelo.Producto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class MainPrueba {

    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory
                = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        var entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityTransaction.commit();
        entityManager.close();

        ControladorCliente controladorCliente = new ControladorCliente();

        System.out.println();
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------------------");

        List<Pedido> pedidos = new ArrayList<>();
        List<Pedido> pedidos2 = new ArrayList<>();
        Cliente cliente = new Cliente("48642965N", "Stiven", "602 381 142", "stivnsolanomacas@gmail.com", "Travesia", "12345", pedidos, true, "Solano");
        Cliente cliente1 = new Cliente("48642966J", "Marcos", "603 300 142", "solamacas@gmail.com", "Casa nova", "456123", pedidos2, false, "Macas");

        List<LineaPedido> lineasPedidos = new ArrayList<>();
        List<Ingrediente> ingredientes = new ArrayList<>();
        List<Alergeno> alergenos = new ArrayList<>();
        Alergeno alergeno = new Alergeno(ingredientes, "Coloterel");
        Alergeno alergeno1 = new Alergeno(ingredientes, "Papitas");
        alergenos.add(alergeno1);
        alergenos.add(alergeno);
        Ingrediente ingrediente = new Ingrediente("Cebolla", alergenos);
        ingredientes.add(ingrediente);
        Producto producto = new Pizza("Pasta", 41, Size.GRANDE, ingredientes);
        Pedido pedido = new Pedido(EstadoPedido.ENTREGADO, lineasPedidos, cliente, null);
        LineaPedido lineaPedido = new LineaPedido(10, producto, pedido);
        lineasPedidos.add(lineaPedido);
        pedidos.add(pedido);

        Pedido pedidoClonado = new Pedido();
        pedidoClonado.setEstado(pedido.getEstado());

        List<LineaPedido> lineasPedidosClonadas = new ArrayList<>();

        for (LineaPedido linea : pedido.getLineaPedidos()) {
            LineaPedido lineaClonada = new LineaPedido();
            lineaClonada.setCantidad(linea.getCantidad());
            lineaClonada.setProducto(linea.getProducto());
            lineaClonada.setPedido(pedidoClonado); // Asociar al pedido clonado
            lineasPedidosClonadas.add(lineaClonada);
        }
        pedidoClonado.setLineaPedidos(lineasPedidosClonadas);
        pedidoClonado.setCliente(cliente1);
        pedidos2.add(pedidoClonado);

        try {
            controladorCliente.save(cliente);
            controladorCliente.save(cliente1);

            Cliente clienteEncontrado = controladorCliente.findByEmail("stivnsolanomacas@gmail.com");
            clienteEncontrado.setApellidos("Pintor asutriaco");
            clienteEncontrado.setDireccion("Pisos picados");
            System.out.println(clienteEncontrado.toString());
            controladorCliente.update(clienteEncontrado);
            ArrayList<Cliente> listaClientes = (ArrayList<Cliente>) controladorCliente.getAllCusturmers();
            listaClientes.forEach(x -> System.out.println(x + "VIVA ESPAÑA"));
            Cliente clienteEncontrado2 = controladorCliente.findByEmail("stivnsolanomacas@gmail.com");
            controladorCliente.getAllCusturmers().forEach(x -> System.out.println(x));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
