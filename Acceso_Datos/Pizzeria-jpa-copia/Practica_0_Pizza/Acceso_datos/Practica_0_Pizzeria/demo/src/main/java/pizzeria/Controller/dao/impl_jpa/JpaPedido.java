package pizzeria.Controller.dao.impl_jpa;

import java.sql.SQLException;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import pizzeria.Controller.dao.InnerPedido;
import pizzeria.Modelo.Cliente;
import pizzeria.Modelo.EstadoPedido;
import pizzeria.Modelo.LineaPedido;
import pizzeria.Modelo.Pagable;
import pizzeria.Modelo.Pedido;
import pizzeria.Modelo.Producto;
import pizzeria.Modelo.Producto;

public class JpaPedido implements InnerPedido {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
    JpaProducto jpaProducto = new JpaProducto();

    @Override
    public void save(int idCliente, Pedido pedido) throws ClassNotFoundException, SQLException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            Cliente cliente = entityManager.find(Cliente.class, idCliente);
            if (cliente != null) {
                pedido.setCliente(cliente);
            }

            for (LineaPedido linea : pedido.getListaLineaPedidos()) {
                // Verificar si el producto ya existe en la base de datos
                Producto producto = jpaProducto.getOrCreateProducto(entityManager, linea.getProducto());
                linea.setProducto(producto); // Asociar producto existente o creado
                // Asociar LineaPedido con el pedido
                linea.setPedido(pedido);
            }

            // Persistir el pedido (CascadeType.ALL se encargará de las LineaPedidos)
            entityManager.persist(pedido);

            entityManager.getTransaction().commit();
            System.out.println("Pedido persistido exitosamente.");
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Error al persistir el pedido.", e);
        }

    }

    @Override
    public void updatePedidoEstado(Pedido pedido) throws SQLException, ClassNotFoundException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updatePedidoEstado'");
    }

    @Override
    public void updatePedidoEstadoAndPagable(Pedido pedido, Pagable pagable)
            throws ClassNotFoundException, SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updatePedidoEstadoAndPagable'");
    }

    @Override
    public List<Pedido> getAllPedidoByEstado(EstadoPedido estado, Cliente cliente) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Pedido> pedidos = entityManager
                .createQuery("SELECT a FROM Pedido a JOIN a.cliente p WHERE p.id = :id AND a.estado = :estado",
                        Pedido.class)
                .setParameter("id",
                        cliente.getId())
                .setParameter("estado", estado)
                .getResultList();

        return pedidos;

    }

    @Override
    public void addCarrito(Pedido pedido, Producto producto, int cantidad) throws ClassNotFoundException, SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addCarrito'");
    }

    @Override
    public List<Pedido> getAllPedidoByIdCliente(int idCliente) throws ClassNotFoundException, SQLException {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Pedido> pedidos = entityManager
                .createQuery("SELECT a FROM Pedido a JOIN a.cliente p WHERE p.id = :id", Pedido.class)
                .setParameter("id",
                        idCliente)
                .getResultList();

        return pedidos;
    }

    @Override
    public void delete(int idPedido) throws ClassNotFoundException, SQLException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Pedido pedido = entityManager.find(Pedido.class, idPedido);
        entityManager.remove(pedido);
        entityManager.getTransaction().commit();
    }

    @Override
    public void update(Pedido pedido) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(pedido);
        entityManager.getTransaction().commit();

    }

    public Pedido getById(int idPedido) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Pedido pedido = entityManager.find(Pedido.class, idPedido);

        return pedido;
    }

}
