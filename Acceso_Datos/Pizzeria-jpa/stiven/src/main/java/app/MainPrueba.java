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
import app.Modelo.Pasta;
import app.Modelo.Pedido;
import app.Modelo.Pedido.EstadoPedido;
import app.Modelo.Pizza;
import app.Modelo.Producto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class MainPrueba {

    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = 
        Persistence.createEntityManagerFactory("default"); 
        EntityManager entityManager = entityManagerFactory.createEntityManager(); 
        var entityTransaction = entityManager.getTransaction(); 
        entityTransaction.begin();
        entityTransaction.commit(); 
        entityManager.close();

        ControladorCliente controladorCliente = new ControladorCliente();

        System.out.println();
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------------------");


        List<Pedido> listaPedidos = new ArrayList<>();

        LineaPedido lineaPedido = new LineaPedido(1, new Pasta("Marta", 2.0, Size.ENANO, List.of(new Ingrediente("Pina", List.of(new Alergeno(null, "Macarron")))) ));

        List<LineaPedido> lista = new ArrayList<>();

        lista.add(lineaPedido);


        Cliente cliente = new Cliente("48642965N", "Stiven", "602 381 142", "stivnsolanomacas@gmail.com", "Travesia", "12345", listaPedidos, true, "Solano");

        Cliente cliente1 = new Cliente("48642966J", "Stiven", "603 300 142", "solamacas@gmail.com", "Casa nova", "456123", null, false, "Macas");


        Pedido pedido = new Pedido(EstadoPedido.ENTREGADO, lista, cliente, null);
        
        listaPedidos.add(pedido);


        Cliente cliente2 = new Cliente("12345678A", "Juan", "600123456", "juan@example.com", "Calle Falsa 123", "password", null, false, "Pérez");

        Pedido pedido1 = new Pedido();
        pedido.setCliente(cliente);

        Producto pizza = new Pizza("Margarita", 8.99, Size.GRANDE, null);


        LineaPedido linea = new LineaPedido(20,pizza);
        linea.setCantidad(2);
        linea.setPedido(pedido1);


        pedido.getLineaPedidos().add(linea);

 


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
            //controladorCliente.delete(clienteEncontrado2);

            controladorCliente.getAllCusturmers().forEach(x -> System.out.println(x));
    
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
