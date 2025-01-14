package pizzeria.Controller.dao.impl_jpa;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import pizzeria.Controller.dao.IngredienteJpaDao;
import pizzeria.Modelo.Ingredientes;

public class JpaIngrediente implements IngredienteJpaDao {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");

    @Override
    public void save(Ingredientes ingrediente) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(ingrediente);
        entityManager.getTransaction().commit();
    }

    @Override
    public List<Ingredientes> getAllIngredientesByIdProducto(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
 
        List<Ingredientes>  ingredientes =     entityManager.createQuery("SELECT i FROM Ingredientes i " +
                "JOIN i.productos p " +
                "WHERE p.id = :productoId", Ingredientes.class)
                .setParameter("productoId", id).getResultList();
         
        return ingredientes;
    }

}
