package app.Controladores.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.Controladores.dao.ProductoDao;
import app.Modelo.Alergeno;
import app.Modelo.Bebida;
import app.Modelo.Ingrediente;
import app.Modelo.Producto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;

public class JpaProducto implements ProductoDao {

    private final EntityManagerFactory entityManagerFactory;

    public JpaProducto() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("default");
    }

    @Override
    public boolean delete(Producto producto) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Producto productoBaseDatos = entityManager.contains(producto) ? producto : entityManager.merge(producto);
            entityManager.remove(productoBaseDatos);
            entityManager.getTransaction().commit();
            return true; 
        } catch (Exception e) {
            return false; 
        }
    }

    @Override
    public void update(Producto producto) throws SQLException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try (entityManager) {
            entityManager.getTransaction().begin();
            entityManager.merge(producto);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new SQLException("Error al actualizar el producto: " + e.getMessage(), e);
        }
    }

    @Override
    public void save(Producto producto) throws SQLException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            try {
                entityManager
                        .createQuery("SELECT c FROM Producto c WHERE c.nombre = :nombre", Producto.class)
                        .setParameter("nombre", producto.getNombre())
                        .getSingleResult();

            } catch (NoResultException e) {
                if (producto instanceof Bebida) {
                    entityManager.persist(producto);
                } else {
                    if (producto.getIngredientes() != null) {

                        ArrayList<Ingrediente> ingredientesBaseDatos = new ArrayList<>();

                        for (Ingrediente ingrediente : producto.getIngredientes()) {
                            Ingrediente ingredienteBaseDatos = saveIngrediente(entityManager, ingrediente);
                            ingredientesBaseDatos.add(ingredienteBaseDatos);
                        }

                        producto.setIngredientes(ingredientesBaseDatos);

                    }
                    entityManager.persist(producto);
                }
            }

            entityManager.getTransaction().commit();
        } catch (SQLException e) {
            throw new SQLException("Error al guardar el producto: " + e.getMessage(), e);
        } 
    }

    public Ingrediente saveIngrediente(EntityManager entityManager, Ingrediente ingrediente) throws SQLException {
        
        try {
            Ingrediente ingredienteBaseDatos = entityManager
                    .createQuery("SELECT c FROM Ingrediente c WHERE c.nombre = :nombre", Ingrediente.class)
                    .setParameter("nombre", ingrediente.getNombre())
                    .getSingleResult();

            return entityManager.merge(ingredienteBaseDatos);
        } catch (NoResultException e) {

            if (ingrediente.getAlergenos() != null) {
                ArrayList<Alergeno> alergenosBaseDatos = new ArrayList<>();

                for (Alergeno alergeno : ingrediente.getAlergenos()) {
                    alergenosBaseDatos.add(saveAlergeno(entityManager, alergeno));
                }

                ingrediente.setAlergenos(alergenosBaseDatos);

            }
            return entityManager.merge(ingrediente); 
        }
    }

    public Alergeno saveAlergeno(EntityManager entityManager, Alergeno alergeno) throws SQLException {
      
        try {
            Alergeno alergenoBaseDatos = entityManager
                    .createQuery("SELECT c FROM Alergeno c WHERE c.nombre = :nombre", Alergeno.class)
                    .setParameter("nombre", alergeno.getNombre())
                    .getSingleResult();
            return entityManager.merge(alergenoBaseDatos);
        } catch (NoResultException e) {
            return entityManager.merge(alergeno);
        }
    }

    @Override
    public List<Producto> getAllProducts() throws SQLException {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            List<Producto> listaProductos = entityManager
                    .createQuery("SELECT c FROM Producto c", Producto.class)
                    .getResultList();
            return listaProductos;
        } catch (Exception e) {
            throw new SQLException("Error al obtener todos los productos: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Ingrediente> findByProduct(Producto producto) throws SQLException {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            return entityManager.createQuery(
                    "SELECT i FROM Ingrediente i JOIN i.listaProductos p WHERE p.id = :id", Ingrediente.class)
                    .setParameter("id", producto.getId())
                    .getResultList();
        } catch (Exception e) {
            throw new SQLException("Error al encontrar ingredientes por producto: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Alergeno> findbyIngrediente(Ingrediente ingrediente) throws SQLException {

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            return entityManager.createQuery(
                    "SELECT a FROM Alergeno a JOIN a.ingredientes i WHERE i.id = :id", Alergeno.class)
                    .setParameter("id", ingrediente.getId())
                    .getResultList();
        } catch (Exception e) {
            throw new SQLException("Error al encontrar alérgenos por ingrediente: " + e.getMessage(), e);
        }
    }

}
