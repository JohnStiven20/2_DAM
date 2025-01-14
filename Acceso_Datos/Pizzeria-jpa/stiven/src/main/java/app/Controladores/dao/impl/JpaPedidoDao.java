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

    private final EntityManagerFactory entityManagerFactory;

    public JpaPedidoDao() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("default");
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
        try (entityManager) {
            entityManager.getTransaction().begin();
            Pedido managedPedido = entityManager.contains(pedido) ? pedido : entityManager.merge(pedido);
            entityManager.remove(managedPedido);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager != null && entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new SQLException("Error al eliminar el pedido: " + e.getMessage(), e);
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
        String consulta = "SELECT pedido.* FROM pedido WHERE pedido.cliente_id = :id";
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            @SuppressWarnings("unchecked")
            List<Pedido> listaPedidos = entityManager.createNativeQuery(consulta, Pedido.class)
                .setParameter("id", cliente.getId())
                .getResultList();
            return listaPedidos;
        } catch (Exception e) {
            throw new SQLException("Error al obtener los pedidos del cliente: " + e.getMessage(), e);
        }
    }

    @Override
    public List<LineaPedido> getLineasOrdersByOrder(Pedido pedido) throws SQLException {
        String consulta = "SELECT lineapedido.* FROM lineapedido JOIN pedido ON pedido.id = lineapedido.pedido_id WHERE pedido.id = :id";
        
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            @SuppressWarnings("unchecked")
            List<LineaPedido> listaLineas = entityManager.createNativeQuery(consulta, LineaPedido.class)
                .setParameter("id", pedido.getId())
                .getResultList();
            return listaLineas;
        } catch (Exception e) {
            throw new SQLException("Error al obtener las líneas del pedido: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Pedido> getOrdersByStatus(EstadoPedido estadoPedido, Cliente cliente) throws SQLException {
        String consulta = "SELECT pedido.* FROM pedido WHERE pedido.estado = :estado";
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            @SuppressWarnings("unchecked")
            List<Pedido> listaPedidos = entityManager.createNativeQuery(consulta, Pedido.class)
                .setParameter("estado", estadoPedido.name())
                .getResultList();
            return listaPedidos;
        } catch (Exception e) {
            throw new SQLException("Error al obtener los pedidos por estado: " + e.getMessage(), e);
        }
    }

    @Override
    public void addOrderLine(int cantidad, Producto producto, Pedido pedido) throws SQLException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try (entityManager) {
            entityManager.getTransaction().begin();
            LineaPedido lineaPedido = new LineaPedido(cantidad, producto, pedido);
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
