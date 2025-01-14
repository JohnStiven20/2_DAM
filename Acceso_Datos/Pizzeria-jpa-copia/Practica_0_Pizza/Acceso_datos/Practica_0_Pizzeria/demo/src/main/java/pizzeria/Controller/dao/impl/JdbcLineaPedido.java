package pizzeria.Controller.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static pizzeria.utils.DatabaseConfig.*;
import pizzeria.Controller.dao.InnerLineaPedidoDAO;

import pizzeria.Modelo.Conexion;
import pizzeria.Modelo.LineaPedido;
import pizzeria.Modelo.Producto;

public class JdbcLineaPedido implements InnerLineaPedidoDAO {

    JdbcProductoDAO jdbcProductoDAO = new JdbcProductoDAO();

    @Override
    public void save(Connection con, int idProducto, int idPedido, int cantidad)
            throws SQLException, ClassNotFoundException {

        try (
                PreparedStatement preparedStatement = con.prepareStatement(INSERT_LINEAPEDIDO);) {
            preparedStatement.setInt(1, cantidad);
            preparedStatement.setInt(2, idProducto);
            preparedStatement.setInt(3, idPedido);

            preparedStatement.executeUpdate();
            con.commit();
        }

    }

    @Override
    public List<LineaPedido> getAllLineaPedidonbyID(int idPedido) throws SQLException, ClassNotFoundException {

        List<LineaPedido> listaLineaPedidos = new ArrayList<LineaPedido>();

        try (Connection con = new Conexion().getConexion();
                PreparedStatement preparedStatement = con.prepareStatement(SELECT_LINEAPEDIDO_BY_ID);) {

            preparedStatement.setInt(1, idPedido);

            try (ResultSet resultado = preparedStatement.executeQuery()) {

                while (resultado.next()) {
                    int id = resultado.getInt("id");
                    int cantidad = resultado.getInt("cantidad");
                    int idProducto = resultado.getInt("id_producto");
                    Producto producto = jdbcProductoDAO.getProductoById(idProducto);
                    listaLineaPedidos.add(new LineaPedido(id, cantidad, producto));

                }
            }

        }

        return listaLineaPedidos;
        //
    }

}
