package pizzeria.Controller.dao.impl_jpa;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import pizzeria.Controller.dao.InnerLineaPedidoDAO;
import pizzeria.Modelo.LineaPedido;
import pizzeria.Modelo.Pedido;

public class JpaLineaPedido {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");

    public void save(LineaPedido lineaPedido) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(lineaPedido);
        entityManager.getTransaction().commit();
    }

    public List<LineaPedido> getAllLineaPedidonbyID(int idPedido) throws SQLException, ClassNotFoundException {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<LineaPedido> pedidos = entityManager
                .createQuery("SELECT a FROM LineaPedido a WHERE a.pedido.id = :id", LineaPedido.class)
                .setParameter("id", idPedido).getResultList();

        return pedidos;
    }

}
