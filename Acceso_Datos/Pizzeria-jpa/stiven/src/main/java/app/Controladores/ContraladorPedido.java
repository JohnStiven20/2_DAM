package app.Controladores;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.Controladores.dao.PedidoDao;
import app.Controladores.dao.impl.JbcPedidoDao;
import app.Interfaces.Pagable;
import app.Modelo.Cliente;
import app.Modelo.LineaPedido;
import app.Modelo.Pedido;
import app.Modelo.Pedido.EstadoPedido;
import app.Modelo.Producto;

public class ContraladorPedido {

    private final PedidoDao pedidoDao;

    public ContraladorPedido() {
        pedidoDao = new JbcPedidoDao();
    }

    public boolean delete(Pedido pedido) throws SQLException {
        return pedidoDao.delete(pedido);
    }

    public void save(Pedido pedido) throws SQLException {
        pedidoDao.save(pedido);
    }

    public void getLineasOrdersByOrder(Pedido pedido) throws SQLException {
        pedidoDao.getLineasOrdersByOrder(pedido).forEach(x -> System.out.println(x));
    }

    public List<Pedido> getOrdersByStatus(Pedido.EstadoPedido estadoPedido, Cliente cliente) throws SQLException {
        return pedidoDao.getOrdersByStatus(estadoPedido, cliente);
    }

    public List<Pedido> getOrdersByCustumer(Cliente cliente) throws SQLException {
        return pedidoDao.getOrdersByCustumer(cliente);
    }

    public void addOrderLine(int cantidad ,Producto producto, Pedido pedido) throws SQLException {
        pedidoDao.addOrderLine(cantidad, producto, pedido);
    }

    public void addCarrito(Producto producto,int cantidad , Cliente cliente) throws SQLException {

        ArrayList<LineaPedido> lisatLineaPedidos = new ArrayList<>();
        List<Pedido> listaPedidos = pedidoDao.getOrdersByStatus(EstadoPedido.PEDIENTE, cliente);
        Pedido pedidoNuevo = listaPedidos.stream().findFirst().orElse(null);

        if (pedidoNuevo != null) {
            addOrderLine(cantidad, producto, pedidoNuevo);           
        } else {
            LineaPedido lineaPedido = new LineaPedido(cantidad, producto);
            lisatLineaPedidos.add(lineaPedido);
            pedidoNuevo = new Pedido(EstadoPedido.PEDIENTE, lisatLineaPedidos, cliente, null);
            pedidoDao.save(pedidoNuevo);
        }
    }

    public void finalizarPedido(Pagable metodoPago, Cliente cliente) throws SQLException {
        Pedido pedido = pedidoDao.getOrdersByStatus(EstadoPedido.PEDIENTE, cliente).stream().findFirst().get();
        pedido.setEstado(EstadoPedido.ENTREGADO);
        pedido.setPagable(metodoPago);
        pedidoDao.update(pedido, EstadoPedido.ENTREGADO);
    }

    public void cancelarPedido(Cliente cliente) throws SQLException {
        Pedido pedido = pedidoDao.getOrdersByStatus(EstadoPedido.PEDIENTE, cliente).stream().findFirst().get();
        pedidoDao.update(pedido, EstadoPedido.CANCELADO);
    }

    public void entregarPedido(Cliente cliente) throws SQLException {
        Pedido pedido = pedidoDao.getOrdersByStatus(EstadoPedido.PEDIENTE, cliente).stream().findFirst().get();
        pedidoDao.update(pedido, EstadoPedido.ENTREGADO);
    }
}
