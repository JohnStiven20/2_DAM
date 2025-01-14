package pizzeria.Controller.dao;

import java.sql.SQLException;
import java.util.List;

import pizzeria.Modelo.Cliente;
import pizzeria.Modelo.EstadoPedido;
import pizzeria.Modelo.LineaPedido;
import pizzeria.Modelo.Pagable;
import pizzeria.Modelo.Pedido;
import pizzeria.Modelo.Producto;

public interface InnerPedido {

    public void save(int idCliente, Pedido pedido) throws ClassNotFoundException, SQLException;

    public void updatePedidoEstado(Pedido pedido) throws SQLException, ClassNotFoundException;

    public void updatePedidoEstadoAndPagable(Pedido pedido, Pagable pagable)
            throws ClassNotFoundException, SQLException;

    public List<Pedido> getAllPedidoByEstado(EstadoPedido estado ,  Cliente cliente) throws ClassNotFoundException, SQLException;

    public void addCarrito(Pedido pedido, Producto producto, int cantidad) throws ClassNotFoundException, SQLException;

    public List<Pedido> getAllPedidoByIdCliente(int idCliente) throws ClassNotFoundException, SQLException;

    public void delete(int idPedido) throws ClassNotFoundException, SQLException;
 
    public void update(Pedido pedido); 


}
