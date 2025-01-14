package pizzeria.Controller.dao.impl_jpa;

import java.sql.SQLException;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import pizzeria.Controller.dao.InnerClienteDAO;
import pizzeria.Exceptions.ExceptionFoundCliente;
import pizzeria.Modelo.Cliente;


public class JpaCliente implements InnerClienteDAO {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");

    @Override
    public void save(Cliente cliente) throws SQLException, ClassNotFoundException {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(cliente);
        entityManager.getTransaction().commit();

    }

    @Override
    public Cliente finClienteByEmail(String email) throws SQLException, ClassNotFoundException  { 
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            Cliente cliente = entityManager.createQuery("SELECT c FROM Cliente c WHERE c.email = :email",
                    Cliente.class).setParameter("email", email).getSingleResult();
            return cliente;
    }

    @Override
    public Cliente login(String password, String nombre) throws SQLException, ClassNotFoundException {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Cliente cliente = entityManager
                .createQuery("SELECT a FROM Cliente a WHERE a.password = :password AND a.nombre = :nombre",
                        Cliente.class)
                .setParameter("password", password).setParameter("nombre", nombre).getSingleResult();

        return cliente;
    }

    @Override
    public void update(Cliente cliente) throws SQLException, ClassNotFoundException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(cliente);
        entityManager.getTransaction().commit();
    }

    @Override
    public void delete(int idCliente) throws SQLException, ClassNotFoundException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Cliente cliente = entityManager.find(Cliente.class, idCliente);
        entityManager.remove(cliente);
        entityManager.getTransaction().commit();

    }

    @Override
    public List<Cliente> getAllCliente() throws SQLException, ClassNotFoundException {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Cliente> clientes = entityManager.createQuery("SELECT  c FROM Cliente c", Cliente.class).getResultList();

        return clientes;
    }

}
