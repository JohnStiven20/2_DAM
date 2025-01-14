package app;

import java.util.ArrayList;

import app.Controladores.ControladorCliente;
import app.Modelo.Cliente;
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

        Cliente cliente = new Cliente("48642965N", "Stiven", "602 381 142", "stivnsolanomacas@gmail.com", "Travesia", "12345", null, true, "Solano");
        Cliente cliente1 = new Cliente("48642966J", "Stiven", "603 300 142", "solamacas@gmail.com", "Casa nova", "456123", null, false, "Macas");


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
            controladorCliente.delete(clienteEncontrado2);

            controladorCliente.getAllCusturmers().forEach(x -> System.out.println(x));


    
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }




    }
}
