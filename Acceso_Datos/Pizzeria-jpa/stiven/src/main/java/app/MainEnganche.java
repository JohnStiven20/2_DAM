package app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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


        try {

            jpaProducto.save(pasta);
            jpaProducto.save(pizza);
            jpaProducto.save(pasta);

        } catch (NoResultException | SQLException e) {
            System.out.println(e.getMessage());
        }

    
    }
}
