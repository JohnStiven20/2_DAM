package app.Controladores.dao.impl;

import java.sql.SQLException;
import java.util.List;

import app.Controladores.dao.PedidoDao;
import app.Modelo.Cliente;
import app.Modelo.LineaPedido;
import app.Modelo.Pedido;
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

            // Manejo de relaciones Cliente -> Pedido
            Cliente cliente = pedido.getCliente();
            if (cliente != null) {
                // Verifica si el cliente ya contiene el pedido, y lo agrega si no está
                if (!cliente.getListaPedidos().contains(pedido)) {
                    cliente.getListaPedidos().add(pedido);
                }
            }

            // Manejo de relaciones Pedido -> LineaPedido
            if (pedido.getLineaPedidos() != null) {
                for (LineaPedido linea : pedido.getLineaPedidos()) {
                    // Configura el pedido en cada LineaPedido si aún no está establecido
                    if (linea.getPedido() == null) {
                        linea.setPedido(pedido);
                    }
                }
            }

            // Guarda o actualiza el pedido y sus relaciones
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

            Pedido managedPedido = entityManager.contains(pedido) ? pedido
                    : entityManager.find(Pedido.class, pedido.getId());

            if (managedPedido != null) {
                Cliente cliente = managedPedido.getCliente();
                if (cliente != null) {
                    cliente.getListaPedidos().remove(managedPedido);
                    managedPedido.setCliente(null);
                }

                if (managedPedido.getLineaPedidos() != null) {
                    for (LineaPedido linea : managedPedido.getLineaPedidos()) {
                        linea.setPedido(null);
                    }
                    managedPedido.getLineaPedidos().clear();
                }

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
        if (cliente == null || cliente.getId() == 0) {
            throw new IllegalArgumentException("El cliente proporcionado no es válido.");
        }

        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();

            String consulta = "SELECT p FROM Pedido p WHERE p.cliente.id = :id";
            List<Pedido> pedidos = entityManager.createQuery(consulta, Pedido.class)
                    .setParameter("id", cliente.getId())
                    .getResultList();

            return pedidos;
        } catch (Exception e) {
            throw new SQLException("Error al obtener los pedidos del cliente: " + e.getMessage(), e);
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    @Override
    public List<LineaPedido> getLineasOrdersByOrder(Pedido pedido) throws SQLException {
        if (pedido == null || pedido.getId() == -1) {
            throw new IllegalArgumentException("El pedido proporcionado no es válido.");
        }

        String consulta = "SELECT l FROM LineaPedido l JOIN l.pedido p WHERE p.id = :id";
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            return entityManager.createQuery(consulta, LineaPedido.class)
                    .setParameter("id", pedido.getId())
                    .getResultList();
        } catch (Exception e) {
            throw new SQLException("Error al obtener las líneas del pedido: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Pedido> getOrdersByStatus(Pedido.EstadoPedido estadoPedido, Cliente cliente) throws SQLException {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            String consulta = "SELECT p FROM Pedido p JOIN p.cliente c WHERE p.estado = :estado AND c.id = :id";
            List<Pedido> pedidos = entityManager.createQuery(consulta, Pedido.class)
                    .setParameter("estado", estadoPedido)
                    .setParameter("id", cliente.getId())
                    .getResultList();

            entityManager.getTransaction().commit();
            return pedidos;
        } catch (Exception e) {
            if (entityManager != null && entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new SQLException("Error al obtener los pedidos por estado: " + e.getMessage(), e);
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
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
