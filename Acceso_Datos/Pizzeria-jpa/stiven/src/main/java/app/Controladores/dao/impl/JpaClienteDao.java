package app.Controladores.dao.impl;

import java.sql.SQLException;
import java.util.List;

import app.Controladores.dao.ClienteDao;
import app.Modelo.Cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaClienteDao implements ClienteDao {

    private final EntityManagerFactory entityManagerFactory;

    public JpaClienteDao() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("default");
    }

    @Override
    public void delete(Cliente cliente) throws SQLException {
        try(EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            Cliente managedCliente = entityManager.contains(cliente) ? cliente : entityManager.merge(cliente);
            entityManager.remove(managedCliente);
            entityManager.getTransaction().commit();
            entityManager.close();
        } 
    }

    @Override
    public void update(Cliente cliente) throws SQLException {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()){
            entityManager.getTransaction().begin();
            entityManager.merge(cliente);
            entityManager.getTransaction().commit();
            entityManager.close();
        } 
    }

    @Override
    public Cliente getClienteByEmail(String email) throws SQLException {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            Cliente cliente = entityManager
            .createQuery("SELECT c FROM Cliente c WHERE c.email = :email", Cliente.class)
            .setParameter("email", email)
            .getSingleResult();
            entityManager.close();
            return  cliente;
        } 
    }

    @Override
    public List<Cliente> getAllCustomers() throws SQLException {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            List<Cliente> listaCliente = entityManager
            .createQuery("SELECT c FROM Cliente c", Cliente.class)
            .getResultList();
            entityManager.close();
            return listaCliente;
        }
    }


    @Override
    public boolean save(Cliente cliente) throws SQLException {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.persist(cliente);
            entityManager.getTransaction().commit();
            entityManager.close();
        }

        return  true;
    }

}
