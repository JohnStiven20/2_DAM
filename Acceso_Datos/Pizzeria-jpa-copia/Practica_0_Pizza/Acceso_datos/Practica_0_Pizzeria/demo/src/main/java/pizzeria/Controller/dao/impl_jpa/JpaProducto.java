package pizzeria.Controller.dao.impl_jpa;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import pizzeria.Controller.dao.InnerProductoDAO;
import pizzeria.Controller.dao.ProductoJpaDao;
import pizzeria.Modelo.Alergeno;
import pizzeria.Modelo.Bebida;
import pizzeria.Modelo.Cliente;
import pizzeria.Modelo.Ingredientes;
import pizzeria.Modelo.Pasta;
import pizzeria.Modelo.Pizza;
import pizzeria.Modelo.Producto;

public class JpaProducto implements InnerProductoDAO {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");

    @Override
    public void delete(int idProducto) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Producto cliente = entityManager.find(Producto.class, idProducto);
        entityManager.remove(cliente);
        entityManager.getTransaction().commit();
    }

    @Override
    public void update(Producto producto) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(producto);
        entityManager.getTransaction().commit();
    }

    @Override
    public Producto getProductoById(int idProducto) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Producto producto = entityManager.createQuery(
                "SELECT p FROM Producto p WHERE p.id = :id", Producto.class)
                .setParameter("id", idProducto)
                .getSingleResult();
        return producto;
    }

    @Override
    public List<Producto> getAllProductos() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Producto> productos = entityManager.createQuery("SELECT a FROM Producto a", Producto.class).getResultList();
        return productos;
    }

    public Producto getOrCreateProducto(EntityManager em, Producto producto) {
        try {

            Producto productoExistente = em.createQuery(
                    "SELECT p FROM Producto p WHERE p.nombre = :nombre", Producto.class)
                    .setParameter("nombre", producto.getNombre())
                    .getSingleResult();
            return productoExistente;

        } catch (NoResultException e) {

            if (producto instanceof Pizza) {

                Pizza pizza = (Pizza) producto;
                List<Ingredientes> ingredientes = new ArrayList<>();

                for (Ingredientes ingrediente : pizza.getIngredientes()) {
                    ingredientes.add(getOrCreateIngredient(em, ingrediente));
                }

                pizza.setIngredientes(ingredientes);
                em.persist(pizza);
                return pizza;

            } else if (producto instanceof Pasta) {

                Pasta pasta = (Pasta) producto;
                List<Ingredientes> ingredientes = new ArrayList<>();
                for (Ingredientes ingrediente : pasta.getIngredientes()) {
                    ingredientes.add(getOrCreateIngredient(em, ingrediente));
                }
                pasta.setIngredientes(ingredientes);
                em.persist(pasta);
                return pasta;

            } else   {

                Bebida bebida = (Bebida) producto;
                em.persist(bebida);
                return bebida;

            }
        }
    }

    private Ingredientes getOrCreateIngredient(EntityManager em, Ingredientes ingrediente) {
        try {
            Ingredientes ingredienteExistente = em.createQuery(
                    "SELECT i FROM Ingredientes i WHERE i.nombre = :nombre", Ingredientes.class)
                    .setParameter("nombre", ingrediente.getNombre())
                    .getSingleResult();
            return ingredienteExistente;

        } catch (NoResultException e) {
            List<Alergeno> alergenos = new ArrayList<>();
            for (Alergeno alergeno : ingrediente.getAlergenos()) {
                alergenos.add(getOrCreateAlergeno(em, alergeno));
            }
            ingrediente.setAlergenos(alergenos);
            em.persist(ingrediente);
            return ingrediente;
        }
    }

    private Alergeno getOrCreateAlergeno(EntityManager em, Alergeno alergeno) {
        try {
            Alergeno alergenoExistente = em.createQuery(
                    "SELECT a FROM Alergeno a WHERE a.nombre = :nombre", Alergeno.class)
                    .setParameter("nombre", alergeno.getNombre())
                    .getSingleResult();
            return alergenoExistente;

        } catch (NoResultException e) {
            em.persist(alergeno); 
            return alergeno;
        }
    }

    @Override
    public void save(Producto producto) throws SQLException, ClassNotFoundException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            Producto productoGestionado = getOrCreateProducto(entityManager, producto);

            if (!entityManager.contains(productoGestionado)) {
                entityManager.persist(productoGestionado);
            }

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        }
    }

}
