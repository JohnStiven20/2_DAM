package app;

import app.Controladores.ControladorCliente;
import app.Modelo.Cliente;

public class MainPrueba {

    public static void main(String[] args) {

        // EntityManagerFactory entityManagerFactory = 
        // Persistence.createEntityManagerFactory("default"); 
        // EntityManager entityManager = entityManagerFactory.createEntityManager(); 
        // var entityTransaction = entityManager.getTransaction(); 
        // entityTransaction.begin();
        // entityTransaction.commit(); 
        // entityManager.close();

        ControladorCliente controladorCliente = new ControladorCliente();

        Cliente cliente = new Cliente("48642965N", "Stiven", "602 381 142", "stivnsolanomacas@gmail.com", "Travesia", "12345", null, true, "Solano");

        try {
            //controladorCliente.save(cliente);

            controladorCliente.update(cliente, "Pisos picados", "602 381 142", "Pinto Austriaco");


        } catch (Exception e) {
            // TODO: handle exception
        }




    }
}
