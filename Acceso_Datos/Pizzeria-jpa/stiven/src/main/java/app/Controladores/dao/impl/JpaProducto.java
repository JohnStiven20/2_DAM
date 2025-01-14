package app.Controladores.dao.impl;

import java.sql.SQLException;
import java.util.List;

import app.Controladores.dao.ProductoDao;
import app.Modelo.Alergeno;
import app.Modelo.Ingrediente;
import app.Modelo.Producto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaProducto implements ProductoDao {

    private final EntityManagerFactory entityManagerFactory;

    public JpaProducto() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("default");
    }

    @Override
    public void delete(Producto producto) throws SQLException {

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            Producto managedCliente = entityManager.contains(producto) ? producto : entityManager.merge(producto);
            entityManager.remove(managedCliente);
            entityManager.getTransaction().commit();
            entityManager.close();
        }

    }

    @Override
    public void update(Producto producto) throws SQLException {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.merge(producto);
            entityManager.getTransaction().commit();
            entityManager.close();
        }
    }

    @Override
    public void  save(Producto producto) throws SQLException {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.persist(producto);
            entityManager.getTransaction().commit();
            entityManager.close();
        }

    }

    @Override
    public List<Producto> getAllProducts() throws SQLException {

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            List<Producto> listaCliente = entityManager
                    .createQuery("SELECT c FROM Producto c", Producto.class)
                    .getResultList();
            entityManager.close();
            return listaCliente;
        }
    }

    @Override
    public List<Ingrediente> findByProduct(Producto producto) throws SQLException {
        String CONSULTA = "SELECT i.* " +
                "FROM Ingrediente i " +
                "JOIN producto_ingrediente pi ON i.id = pi.ingredientes_id " +
                "JOIN producto p ON p.id = pi.listaProductos_id " +
                "WHERE p.id = :id";

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();

            @SuppressWarnings("unchecked")
            List<Ingrediente> ingredientes = entityManager
                    .createNativeQuery(CONSULTA, Ingrediente.class) 
                    .setParameter("id", producto.getId()) 
                    .getResultList();

            entityManager.getTransaction().commit();
            entityManager.close();
            return ingredientes;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Alergeno> findbyIngrediente(Ingrediente ingrediente) throws SQLException {

        String CONSULTA = "SELECT alergeno.*\n" + //
                        "FROM alergeno\n" + //
                        "JOIN ingrediente_alergeno ON ingrediente_alergeno.alergenos_id = alergeno.id\n" + //
                        "JOIN ingrediente ON ingrediente_alergeno.ingredientes_id = ingrediente.id\n" + //
                        "WHERE ingrediente.id = :id";


        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.getTransaction().commit();
            List<Alergeno> listaAlergenos = entityManager.createNativeQuery(CONSULTA, Alergeno.class).setParameter("id", ingrediente.getId())
            .getResultList();
            entityManager.close();
            return listaAlergenos;
            
        }

    }

}
