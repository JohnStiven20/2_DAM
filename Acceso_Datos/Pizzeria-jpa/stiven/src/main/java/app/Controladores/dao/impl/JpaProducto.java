package app.Controladores.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.Controladores.dao.ProductoDao;
import app.Modelo.Alergeno;
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
    public void delete(Producto producto) throws SQLException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try (entityManager) {
            entityManager.getTransaction().begin();
            Producto managedProducto = entityManager.contains(producto) ? producto : entityManager.merge(producto);
            entityManager.remove(managedProducto);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager != null && entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new SQLException("Error al eliminar el producto: " + e.getMessage(), e);
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
            if (entityManager != null && entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new SQLException("Error al actualizar el producto: " + e.getMessage(), e);
        }
    }

    @Override
public void save(Producto producto) throws SQLException {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    try {
        entityManager.getTransaction().begin();

        // Verificar si el producto ya existe
        Producto productoBaseDatos = null;
        try {
            productoBaseDatos = entityManager.createQuery("SELECT c FROM Producto c WHERE c.nombre = :nombre", Producto.class)
                                             .setParameter("nombre", producto.getNombre())
                                             .getSingleResult();
        } catch (NoResultException e) {
            // Producto no existe, continuamos con la creación
        }

        if (productoBaseDatos != null) {
            // Actualizamos el producto existente
            productoBaseDatos.setIngredientes(producto.getIngredientes());
            entityManager.merge(productoBaseDatos);
        } else {
            // Persistimos un nuevo producto
            if (producto.getIngredientes() != null) {
                List<Ingrediente> ingredientesConContexto = new ArrayList<>();
                for (Ingrediente ingrediente : producto.getIngredientes()) {
                    Ingrediente ingredienteConContexto = saveIngrediente(entityManager, ingrediente);
                    ingredientesConContexto.add(ingredienteConContexto);
                }
                producto.setIngredientes(ingredientesConContexto);
            }
            entityManager.persist(producto);
        }

        entityManager.getTransaction().commit();
    } catch (Exception e) {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
        throw new SQLException("Error al guardar el producto: " + e.getMessage(), e);
    } finally {
        if (entityManager.isOpen()) {
            entityManager.close();
        }
    }
}

    


public Ingrediente saveIngrediente(EntityManager entityManager, Ingrediente ingrediente) throws SQLException {
    if (ingrediente == null) {
        throw new IllegalArgumentException("El ingrediente no puede ser null");
    }

    Ingrediente ingredienteBaseDatos = null;
    try {
        ingredienteBaseDatos = entityManager.createQuery("SELECT c FROM Ingrediente c WHERE c.nombre = :nombre", Ingrediente.class)
                                            .setParameter("nombre", ingrediente.getNombre())
                                            .getSingleResult();
    } catch (NoResultException e) {
        // Ingrediente no existe
    }

    if (ingredienteBaseDatos != null) {
        return ingredienteBaseDatos; // Retornar ingrediente existente
    } else {
        // Persistir nuevo ingrediente con sus alérgenos
        if (ingrediente.getAlergenos() != null) {
            List<Alergeno> alergenosConContexto = new ArrayList<>();
            for (Alergeno alergeno : ingrediente.getAlergenos()) {
                Alergeno alergenoConContexto = saveAlergeno(entityManager, alergeno);
                alergenosConContexto.add(alergenoConContexto);
            }
            ingrediente.setAlergenos(alergenosConContexto);
        }
        entityManager.persist(ingrediente);
        return ingrediente;
    }
}





public Alergeno saveAlergeno(EntityManager entityManager, Alergeno alergeno) throws SQLException {
    if (alergeno == null) {
        throw new IllegalArgumentException("El alérgeno no puede ser null");
    }

    Alergeno alergenoBaseDatos = null;
    try {
        alergenoBaseDatos = entityManager.createQuery("SELECT c FROM Alergeno c WHERE c.nombre = :nombre", Alergeno.class)
                                         .setParameter("nombre", alergeno.getNombre())
                                         .getSingleResult();
    } catch (NoResultException e) {
        // Alérgeno no existe
    }

    if (alergenoBaseDatos != null) {
        return alergenoBaseDatos; // Retornar alérgeno existente
    } else {
        entityManager.persist(alergeno);
        return alergeno;
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
        String CONSULTA = "SELECT i.* "
                + "FROM Ingrediente i "
                + "JOIN producto_ingrediente pi ON i.id = pi.ingredientes_id "
                + "JOIN producto p ON p.id = pi.listaProductos_id "
                + "WHERE p.id = :id";

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {

            @SuppressWarnings("unchecked")
            List<Ingrediente> ingredientes = entityManager
                    .createNativeQuery(CONSULTA, Ingrediente.class)
                    .setParameter("id", producto.getId())
                    .getResultList();
            return ingredientes;
        } catch (Exception e) {
            throw new SQLException("Error al encontrar ingredientes por producto: " + e.getMessage(), e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Alergeno> findbyIngrediente(Ingrediente ingrediente) throws SQLException {
        String CONSULTA = "SELECT alergeno.* "
                + "FROM alergeno "
                + "JOIN ingrediente_alergeno ON ingrediente_alergeno.alergenos_id = alergeno.id "
                + "JOIN ingrediente ON ingrediente_alergeno.ingredientes_id = ingrediente.id "
                + "WHERE ingrediente.id = :id";

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            List<Alergeno> listaAlergenos = entityManager
                    .createNativeQuery(CONSULTA, Alergeno.class)
                    .setParameter("id", ingrediente.getId())
                    .getResultList();
            return listaAlergenos;
        } catch (Exception e) {
            throw new SQLException("Error al encontrar alérgenos por ingrediente: " + e.getMessage(), e);
        }
    }

    

   
}
