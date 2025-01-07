package app.Controladores.dao;

import java.sql.SQLException;
import java.util.List;

import app.Interfaces.Pagable;
import app.Modelo.Cliente;
import app.Modelo.LineaPedido;
import app.Modelo.Pedido;
import app.Modelo.Pedido.EstadoPedido;
import app.Modelo.Producto;

public interface PedidoDao {

    void save(Pedido pedido) throws SQLException ;

    boolean delete(Pedido pedido) throws SQLException;

    void update(Pedido pedido, EstadoPedido estadoPedido) throws SQLException;

    List<Pedido> getOrdersByCustumer(Cliente cliente) throws SQLException;

    List<LineaPedido> getLineasOrdersByOrder(Pedido pedido) throws SQLException;

    List<Pedido> getOrdersByStatus(EstadoPedido estadoPedido, Cliente cliente) throws SQLException;

    void addOrderLine(int cantidad, Producto producto, Pedido pedido) throws SQLException ;



    
}


