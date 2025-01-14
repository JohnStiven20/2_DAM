package pizzeria.Controller.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import pizzeria.Modelo.LineaPedido;

public interface InnerLineaPedidoDAO {

    public void save(Connection con, int idProducto, int idPedido, int cantidad)
            throws SQLException, ClassNotFoundException;

    public List<LineaPedido> getAllLineaPedidonbyID(int idPedido) throws SQLException, ClassNotFoundException;

}
