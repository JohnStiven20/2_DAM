package app.Controladores.dao.impl;

import java.sql.SQLException;
import java.util.List;

import app.Controladores.dao.PedidoDao;
import app.Modelo.Cliente;
import app.Modelo.LineaPedido;
import app.Modelo.Pedido;
import app.Modelo.Pedido.EstadoPedido;
import app.Modelo.Producto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaPedidoDao implements PedidoDao {

    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");

    public JpaPedidoDao() {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            var entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityTransaction.commit();
        }
    }

    @Override
    public void save(Pedido pedido) throws SQLException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try (entityManager) {

            entityManager.getTransaction().begin();
            entityManager.merge(pedido);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager != null && entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new SQLException("Error al guardar el pedido: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Pedido pedido) throws SQLException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            // Verificar si el pedido está gestionado por el EntityManager
            Pedido managedPedido = entityManager.contains(pedido) ? pedido
                    : entityManager.find(Pedido.class, pedido.getId());

            if (managedPedido != null) {
                // Desvincular el pedido de su cliente
                Cliente cliente = managedPedido.getCliente();
                if (cliente != null) {
                    cliente.getListaPedidos().remove(managedPedido);
                    managedPedido.setCliente(null);
                }

                // Desvincular líneas de pedido
                if (managedPedido.getLineaPedidos() != null) {
                    for (LineaPedido linea : managedPedido.getLineaPedidos()) {
                        linea.setPedido(null);
                    }
                    managedPedido.getLineaPedidos().clear();
                }

                // Eliminar el pedido
                entityManager.remove(managedPedido);
            }

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new SQLException("Error al eliminar el pedido: " + e.getMessage(), e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void update(Pedido pedido) throws SQLException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try (entityManager) {
            entityManager.getTransaction().begin();
            entityManager.merge(pedido);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager != null && entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new SQLException("Error al actualizar el pedido: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Pedido> getOrdersByCustumer(Cliente cliente) throws SQLException {
        String consulta = "SELECT p FROM Pedido p WHERE p.cliente.id = :id";
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            return entityManager.createQuery(consulta, Pedido.class)
                    .setParameter("id", cliente.getId())
                    .getResultList();
        } catch (Exception e) {
            throw new SQLException("Error al obtener los pedidos del cliente: " + e.getMessage(), e);
        }
    }

    @Override
    public List<LineaPedido> getLineasOrdersByOrder(Pedido pedido) throws SQLException {
        String consulta = "SELECT c FROM Pedido c WHERE c.id = :id";
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            return entityManager.createQuery(consulta, LineaPedido.class)
                    .setParameter("id", pedido.getId())
                    .getResultList();
        } catch (Exception e) {
            throw new SQLException("Error al obtener las líneas del pedido: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Pedido> getOrdersByStatus(EstadoPedido estadoPedido, Cliente cliente) throws SQLException {
        String consulta = "SELECT p FROM Pedido p WHERE p.estado = :estado AND p.cliente.id = :id";
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            return entityManager.createQuery(consulta, Pedido.class)
                    .setParameter("estado", estadoPedido)
                    .setParameter("id", cliente.getId())
                    .getResultList();
        } catch (Exception e) {
            throw new SQLException("Error al obtener los pedidos por estado: " + e.getMessage(), e);
        }
    }

    @Override
    public void addOrderLine(int cantidad, Producto producto, Pedido pedido) throws SQLException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try (entityManager) {
            entityManager.getTransaction().begin();
            LineaPedido lineaPedido = new LineaPedido(cantidad, producto);
            pedido.getLineaPedidos().add(lineaPedido);
            entityManager.merge(pedido);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager != null && entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new SQLException("Error al agregar una línea al pedido: " + e.getMessage(), e);
        }
    }
}
