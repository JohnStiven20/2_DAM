package app.Controladores.dao.impl;

import java.sql.SQLException;
import java.util.List;

import app.Controladores.dao.ClienteDao;
import app.Modelo.Cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;

public class JpaClienteDao implements ClienteDao {

    private final EntityManagerFactory entityManagerFactory;

    public JpaClienteDao() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("default");
    }

    @Override
    public void delete(Cliente cliente) throws SQLException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try (entityManager) {
            entityManager.getTransaction().begin();
            Cliente managedCliente = entityManager.contains(cliente) ? cliente : entityManager.merge(cliente);
            entityManager.remove(managedCliente);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager != null && entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new SQLException("Error al eliminar el cliente: " + e.getMessage() + ". Cliente: " + cliente, e);
        }
    }

    @Override
    public void update(Cliente cliente) throws SQLException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try (entityManager) {
            entityManager.getTransaction().begin();
            entityManager.merge(cliente);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager != null && entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new SQLException("Error al actualizar el cliente: " + e.getMessage() + ". Cliente: " + cliente, e);
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
            return cliente;
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new SQLException("Error al obtener el cliente por email: " + e.getMessage() + ". Email: " + email, e);
        }
    }

    @Override
    public List<Cliente> getAllCustomers() throws SQLException {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            List<Cliente> listaCliente = entityManager
                .createQuery("SELECT c FROM Cliente c", Cliente.class)
                .getResultList();
            return listaCliente;
        } catch (Exception e) {
            throw new SQLException("Error al obtener todos los clientes: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean save(Cliente cliente) throws SQLException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try (entityManager) {
            entityManager.getTransaction().begin();
            entityManager.merge(cliente);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (entityManager != null && entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new SQLException("Error al guardar el cliente: " + e.getMessage() + ". Cliente: " + cliente, e);
        }
    }
}
