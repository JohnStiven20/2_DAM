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
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.persist(pedido);
            entityManager.getTransaction().commit();
            entityManager.close();
        }
    }

    @Override
    public void  delete(Pedido pedido) throws SQLException {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            Pedido managedCliente = entityManager.contains(pedido) ? pedido : entityManager.merge(pedido);
            entityManager.remove(managedCliente);
            entityManager.getTransaction().commit();
            entityManager.close();
        }
    }

    @Override
    public void update(Pedido pedido) throws SQLException {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.merge(pedido);
            entityManager.getTransaction().commit();
            entityManager.close();
        }
    }

    @Override
    public List<Pedido> getOrdersByCustumer(Cliente cliente) throws SQLException {
        
        String consulta = "SELECT pedido.*\n" + //
                        "FROM pedido\n" + //
                        "WHERE pedido.cliente_id = :id\n" + //
                        "";

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            @SuppressWarnings("unchecked")
            List<Pedido> listaPedidos = entityManager.createNativeQuery(consulta, Pedido.class).setParameter("id", cliente.getId()).getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return listaPedidos;
        } 


    }

    @Override
    public List<LineaPedido> getLineasOrdersByOrder(Pedido pedido) throws SQLException {

        String consulta = "SELECT lineapedido.*\n" + //
                        "FROM lineapedido\n" + //
                        "JOIN pedido ON pedido.id = lineapedido.pedido_id\n" + //
                        "WHERE pedido.id = :id";

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            @SuppressWarnings("unchecked")
            List<LineaPedido> listaPedidos = entityManager.createNativeQuery(consulta, LineaPedido.class).setParameter("id", pedido.getId()).getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return listaPedidos;
        }
        
    }

    @Override
    public List<Pedido> getOrdersByStatus(EstadoPedido estadoPedido, Cliente cliente) throws SQLException {

        String consulta = "SELECT pedido.*\n" + //
                        "FROM pedido\n" + //
                        "WHERE pedido.estado = :estado ";

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            @SuppressWarnings("unchecked")
            List<Pedido> listaPedidos = entityManager.createNativeQuery(consulta, LineaPedido.class).setParameter("estado", String.valueOf(estadoPedido)).getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return listaPedidos;
        } 
        
    }

    @Override
    public void addOrderLine(int cantidad, Producto producto, Pedido pedido) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addOrderLine'");
    }
    

}
