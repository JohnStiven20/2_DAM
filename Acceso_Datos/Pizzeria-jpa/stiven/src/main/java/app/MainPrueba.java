package app;

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


    }
}
