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

    }

    @Override
    public void save(Pedido pedido) throws SQLException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try (entityManager) {
            entityManager.getTransaction().begin();
            Cliente cliente = pedido.getCliente();
            if (cliente != null) {
                if (!cliente.getListaPedidos().contains(pedido)) {
                    cliente.getListaPedidos().add(pedido);
                }
            }

            if (pedido.getLineaPedidos() != null) {
                for (LineaPedido linea : pedido.getLineaPedidos()) {
                    if (linea.getPedido() == null) {
                        linea.setPedido(pedido);
                    }
                }
            }

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
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();

            Pedido pedidoBaseDatos = entityManager.contains(pedido) ? pedido
                : entityManager.find(Pedido.class, pedido.getId());

            if (pedidoBaseDatos != null) {
                Cliente cliente = pedidoBaseDatos.getCliente();
                if (cliente != null) {
                    cliente.getListaPedidos().remove(pedidoBaseDatos);
                    pedidoBaseDatos.setCliente(null);
                }

                if (pedidoBaseDatos.getLineaPedidos() != null) {
                    for (LineaPedido linea : pedidoBaseDatos.getLineaPedidos()) {
                        linea.setPedido(null);
                    }
                    pedidoBaseDatos.getLineaPedidos().clear();
                }

                entityManager.remove(pedidoBaseDatos);
            }

            entityManager.getTransaction().commit();
        } catch (Exception e) {
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
            throw new SQLException("Error al actualizar el pedido: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Pedido> getOrdersByCustumer(Cliente cliente) throws SQLException {

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {

            String consulta = "SELECT p FROM Pedido p WHERE p.cliente.id = :id";
            List<Pedido> pedidos = entityManager.createQuery(consulta, Pedido.class)
                    .setParameter("id", cliente.getId())
                    .getResultList();

            return pedidos;

        } catch (Exception e) {
            throw new SQLException("Error al obtener los pedidos del cliente: " + e.getMessage(), e);
        }
    }

    @Override
    public List<LineaPedido> getLineasOrdersByOrder(Pedido pedido) throws SQLException {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            return entityManager
                    .createQuery("SELECT l FROM LineaPedido l JOIN l.pedido p WHERE p.id = :id", LineaPedido.class)
                    .setParameter("id", pedido.getId())
                    .getResultList();
        } catch (Exception e) {
            throw new SQLException("Error al obtener las líneas del pedido: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Pedido> getOrdersByStatus(Pedido.EstadoPedido estadoPedido, Cliente cliente) throws SQLException {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();

            String consulta = "SELECT p FROM Pedido p JOIN p.cliente c WHERE p.estado = :estado AND c.id = :id";
            List<Pedido> pedidos = entityManager.createQuery(consulta, Pedido.class)
                    .setParameter("estado", estadoPedido)
                    .setParameter("id", cliente.getId())
                    .getResultList();

            entityManager.getTransaction().commit();
            return pedidos;
        } catch (Exception e) {
            throw new SQLException("Error al obtener los pedidos por estado: " + e.getMessage(), e);
        }
    }

    @Override
    public void addOrderLine(int cantidad, Producto producto, Pedido pedido) throws SQLException {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            LineaPedido lineaPedido = new LineaPedido(cantidad, producto);
            pedido.getLineaPedidos().add(lineaPedido);
            entityManager.merge(pedido);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new SQLException("Error al agregar una línea al pedido: " + e.getMessage(), e);
        }
    }

}
