package app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.Controladores.ControladorProducto;
import app.Controladores.dao.impl.JpaProducto;
import app.Enums.Size;
import app.Modelo.Alergeno;
import app.Modelo.Ingrediente;
import app.Modelo.Pasta;
import app.Modelo.Pizza;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;

public class MainEnganche {

    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory= Persistence.createEntityManagerFactory("default");
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            var entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityTransaction.commit();
        }

        ControladorProducto controladorProducto = new ControladorProducto();

        JpaProducto jpaProducto = new JpaProducto();

        List<Ingrediente> ingredientes = new ArrayList<>();


        Alergeno lactosa = new Alergeno("Lactosa");
        Alergeno pepitilla = new Alergeno("Pepitilla");

        List<Alergeno> alergenos = new ArrayList<>();

        alergenos.add(lactosa);
        alergenos.add(pepitilla);
        

        Ingrediente tomate = new Ingrediente("Tomate", alergenos);
        Ingrediente cebolla = new Ingrediente("Cebolla", alergenos);
        
        ingredientes.add(tomate);
        ingredientes.add(cebolla);


        Pasta pasta = new Pasta("Pasta", 15, Size.GRANDE, ingredientes);
        Pizza pizza = new Pizza("Pizza", 0, Size.MEDIANO, ingredientes);

   

        // ingredientes.add(new Ingrediente("Maria", null));
        // ingredientes.add(new Ingrediente("Perez", null));

        //Pizza pizza1 = new Pizza("Pizza", 4, Size.GRANDE, ingredientesNuevos);


        try {

            jpaProducto.save(pasta);
            jpaProducto.save(pizza);
            //jpaProducto.save(pizza1);

        } catch (NoResultException | SQLException e) {
            System.out.println(e.getMessage());
        }

        Ingrediente ingrediente = new Ingrediente(1, null, null);
        List<Alergeno> alergenosNuevos = null;

        try {
            alergenos = controladorProducto.getAlergenosByIngredient(ingrediente);
             System.out.println("BUENAS TARDES ");
            alergenos.forEach(x -> System.out.println(x + "viva españa"));
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    
    }
}
