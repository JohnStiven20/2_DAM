package app.Controladores;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.Controladores.dao.PedidoDao;
import app.Controladores.dao.ProductoDao;
import app.Controladores.dao.impl.JpaPedidoDao;
import app.Controladores.dao.impl.JpaProducto;
import app.Interfaces.Pagable;
import app.Modelo.Cliente;
import app.Modelo.LineaPedido;
import app.Modelo.Pedido;
import app.Modelo.Pedido.EstadoPedido;
import app.Modelo.Producto;

public class ContraladorPedido {

    private final PedidoDao pedidoDao2;
    private final ProductoDao productoDao;


    public ContraladorPedido() {
        pedidoDao2 = new JpaPedidoDao();
        productoDao = new JpaProducto();
    }

    public void delete(Pedido pedido) throws SQLException {
        pedidoDao2.delete(pedido);
    }

    public void save(Pedido pedido) throws SQLException {
        pedidoDao2.save(pedido);
    }

    public List<LineaPedido> getLineasOrdersByOrder(Pedido pedido) throws SQLException {
        return pedidoDao2.getLineasOrdersByOrder(pedido);
    }

    public List<Pedido> getOrdersByStatus(Pedido.EstadoPedido estadoPedido, Cliente cliente) throws SQLException {
        return pedidoDao2.getOrdersByStatus(estadoPedido, cliente);
    }

    public List<Pedido> getOrdersByCustumer(Cliente cliente) throws SQLException {
        return pedidoDao2.getOrdersByCustumer(cliente);
    }

    public void addOrderLine(Producto producto, int cantidad, Cliente cliente) throws SQLException {
        List<Pedido> listaPedidos = pedidoDao2.getOrdersByStatus(EstadoPedido.PEDIENTE, cliente);
        Pedido pedido = listaPedidos.stream().findFirst().orElse(null);
    
        try {
            Producto productoEncontrado = productoDao.getAllProducts()
                    .stream()
                    .filter(x -> x.getNombre().equals(producto.getNombre()))
                    .findFirst()
                    .orElse(null);
    
            // Si no se encuentra el producto, guárdalo
            if (productoEncontrado == null) {
                productoDao.save(producto);
                productoEncontrado = productoDao.getAllProducts()
                        .stream()
                        .filter(x -> x.getNombre().equals(producto.getNombre()))
                        .findFirst()
                        .orElse(null);
            }
    
            if (pedido != null) {
                // Agregar línea al pedido existente
                pedidoDao2.addOrderLine(cantidad, productoEncontrado, pedido);
            } else {
                // Crear un nuevo pedido
                Pedido pedidoNuevo = new Pedido();
                pedidoNuevo.setEstado(EstadoPedido.PEDIENTE);
                pedidoNuevo.setCliente(cliente);
    
                LineaPedido lineaPedido = new LineaPedido(cantidad, productoEncontrado);
                List<LineaPedido> listaLineaPedidos = new ArrayList<>();
                listaLineaPedidos.add(lineaPedido);
    
                pedidoNuevo.setLineaPedidos(listaLineaPedidos);
    
                // Guardar el nuevo pedido
                pedidoDao2.save(pedidoNuevo);
            }
        } catch (Exception e) {
            throw new SQLException("Error al agregar una línea al pedido: " + e.getMessage(), e);
        }
    }
    

    public void finalizarPedido(Pagable metodoPago, Cliente cliente) throws SQLException {
        Pedido pedido = pedidoDao2.getOrdersByStatus(EstadoPedido.PEDIENTE, cliente).stream().findFirst().get();
        pedido.setEstado(EstadoPedido.ENTREGADO);
        pedido.setPagable(metodoPago);
        pedidoDao2.update(pedido);
    }

    public void cancelarPedido(Cliente cliente) throws SQLException {
        Pedido pedido = pedidoDao2.getOrdersByStatus(EstadoPedido.PEDIENTE, cliente).stream().findFirst().get();
        pedido.setEstado(EstadoPedido.CANCELADO);
        pedidoDao2.update(pedido);
    }

    public void entregarPedido(Cliente cliente) throws SQLException {
        Pedido pedido = pedidoDao2.getOrdersByStatus(EstadoPedido.PEDIENTE, cliente).stream().findFirst().get();
        pedido.setEstado(EstadoPedido.ENTREGADO);
        pedidoDao2.update(pedido);
    }

}
