package app.Controladores.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import app.Controladores.dao.PedidoDao;
import app.MetodosPagos.PagarEfectivo;
import app.MetodosPagos.PagarTarjeta;
import app.Modelo.Cliente;
import app.Modelo.LineaPedido;
import app.Modelo.Pedido;
import app.Modelo.Pedido.EstadoPedido;
import app.Modelo.Producto;
import static app.Modelo.utils.DatabaseConfPizzeria.DELETE_PEDIDO;
import static app.Modelo.utils.DatabaseConfPizzeria.INSERT_LINEA_PEDIDO;
import static app.Modelo.utils.DatabaseConfPizzeria.INSERT_PEDIDO;
import static app.Modelo.utils.DatabaseConfPizzeria.SELECT_PEDIDO_MEDIANTE_CLIENTE_ID;
import static app.Modelo.utils.DatabaseConfPizzeria.SELECT_PEDIDO_MEDIANTE_ESTADO_PEDIDO_NUEVO;
import static app.Modelo.utils.DatabaseConfPizzeria.UPDATE_PEDIDO_ENTREGADO;
import static app.Modelo.utils.DatabaseConfPizzeria.getConnection;

public class JbcPedidoDao implements PedidoDao {

    private final JbcProductoDao jbcProductoDao = new JbcProductoDao();

    @Override
    public void save(Pedido pedido) throws SQLException {

        Connection connection = getConnection();

        try (connection) {

            int id = 0;

            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PEDIDO,
Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setDate(1, new java.sql.Date(pedido.getFecha().getTime()));
            preparedStatement.setString(2, pedido.getEstado().name());
            preparedStatement.setInt(3, pedido.getCliente().getId());
            preparedStatement.execute();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    id = resultSet.getInt(1);
                }
            }

            for (int i = 0; i < pedido.getLineaPedidos().size(); i++) {
                saveOrderLine(pedido.getLineaPedidos().get(i), connection, id,
                        pedido.getLineaPedidos().get(i).getProducto().getId());
            }

        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
            }
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public boolean delete(Pedido pedido) throws SQLException {
        
        boolean eliminado = false;

        try (Connection conexion = getConnection()) {
           
            PreparedStatement declaracionPreparada = conexion.prepareStatement(DELETE_PEDIDO);
            declaracionPreparada.setInt(1, pedido.getId()); 

            int filasAfectadas = declaracionPreparada.executeUpdate();

            if (filasAfectadas > 0) {
                eliminado = true;
            }
        }

        return eliminado; 
    }

    @Override
    public void update(Pedido pedido, EstadoPedido estadoPedido) throws SQLException {

        try (Connection connection = getConnection()) {

            PreparedStatement preparableStatement = connection.prepareStatement(UPDATE_PEDIDO_ENTREGADO);
            preparableStatement.setString(1, estadoPedido.name());

            if (pedido.getPagable() == null) {
                preparableStatement.setString(2, null);
            } else if (pedido.getPagable() instanceof PagarEfectivo) {
                preparableStatement.setString(2, "EFECTIVO");
            } else if (pedido.getPagable() instanceof PagarTarjeta) {
                preparableStatement.setString(2, "TARJETA");
            }

            preparableStatement.setInt(3, pedido.getId());
            preparableStatement.executeUpdate();

        }
    }

    @Override
    public List<Pedido> getOrdersByCustumer(Cliente cliente) throws SQLException {

        Connection connection = getConnection();
        List<Pedido> pedidos = new ArrayList<>();

        try {

            PreparedStatement preparableStatement = connection.prepareStatement(SELECT_PEDIDO_MEDIANTE_CLIENTE_ID);
            preparableStatement.setInt(1, cliente.getId());

            try (ResultSet resultSet = preparableStatement.executeQuery()) {

                if (resultSet.next()) {
                    pedidos = getOrdersByCustomerId(cliente);
                }
            }

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }

        return pedidos;
    }

    /**
     * 
     * MODICAR YA CLIENTE ES NULL
     * 
     */

    public List<Pedido> getOrdersByCustomerId(Cliente cliente) throws SQLException {

        Connection connection = getConnection();
        List<Pedido> pedidos = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PEDIDO_MEDIANTE_CLIENTE_ID);
            preparedStatement.setInt(1, cliente.getId());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {

                    int id = resultSet.getInt("pedido.id");
                    EstadoPedido estatadoPedido = EstadoPedido.valueOf(resultSet.getString("pedido.estado_pedido"));
                    Date fecha = resultSet.getDate("pedido.fecha");

                    Pedido pedido = new Pedido(id, estatadoPedido, null, cliente, null);
                    pedido.setFecha(fecha);
                    List<LineaPedido> listaLineaPedidos = getLineasOrdersByOrder(pedido);
                    pedido.setLineaPedidos(listaLineaPedidos);
                    pedidos.add(pedido);

                }

            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }

        return pedidos;
    }

    /*
     * 
     * PREGUNTAL PROFE PARA ESTE METODO
     * 
     * MODIFICAR YA QUE SE PASA UN CLIENTE NULL
     * 
     * @see
     * 
     */

    @Override
    public List<Pedido> getOrdersByStatus(EstadoPedido estadoPedido, Cliente cliente) throws SQLException {

        Connection connection = getConnection();
        List<Pedido> pedidos = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(SELECT_PEDIDO_MEDIANTE_ESTADO_PEDIDO_NUEVO);
            preparedStatement.setString(1, estadoPedido.name());
            preparedStatement.setInt(2, cliente.getId());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {

                    int id = resultSet.getInt("pedido.id");
                    EstadoPedido estatadoPedido = EstadoPedido.valueOf(resultSet.getString("pedido.estado_pedido"));
                    Date fecha = resultSet.getDate("pedido.fecha");

                    Pedido pedido = new Pedido(id, estatadoPedido, null, cliente, null);
                    pedido.setFecha(fecha);
                    List<LineaPedido> listaLineaPedidos = getLineasOrdersByOrder(pedido);
                    pedido.setLineaPedidos(listaLineaPedidos);
                    pedidos.add(pedido);

                }

            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }

        return pedidos;
    }

    private void saveOrderLine(LineaPedido lineaPedido, Connection connection, int pedido_id, int producto_id)
            throws SQLException {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_LINEA_PEDIDO,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, lineaPedido.getCantidad());
            preparedStatement.setInt(2, pedido_id);
            preparedStatement.setInt(3, producto_id);
            preparedStatement.execute();

        } catch (SQLException e) {

            throw new SQLException(e.getMessage());

        }
    }

    /**
     * 
     * MEJORARLO PORQUE HAY COSAS REDUNDANTES
     * 
     * @see
     * 
     */

    @Override
    public List<LineaPedido> getLineasOrdersByOrder(Pedido pedido) throws SQLException {
        List<LineaPedido> listaPedidos;
        listaPedidos = jbcProductoDao.getProductsById(pedido.getId());
        return listaPedidos;
    }

    @Override
    public void addOrderLine(int cantidad, Producto producto, Pedido pedido) throws SQLException {
        LineaPedido lineaPedido = new LineaPedido(cantidad, producto);
        saveOrderLine(lineaPedido, getConnection(), pedido.getId(), lineaPedido.getProducto().getId());
    }

}
