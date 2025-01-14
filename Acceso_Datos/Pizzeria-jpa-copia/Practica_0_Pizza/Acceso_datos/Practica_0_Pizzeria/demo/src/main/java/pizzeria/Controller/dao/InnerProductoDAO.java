package pizzeria.Controller.dao;

import java.sql.SQLException;
import java.util.List;

import pizzeria.Modelo.Producto;

public interface InnerProductoDAO {

    public void save(Producto producto) throws SQLException, ClassNotFoundException;

    public void delete(int idProducto) throws SQLException, ClassNotFoundException;

    public void update(Producto producto) throws SQLException, ClassNotFoundException;

    public Producto getProductoById(int idProducto) throws SQLException, ClassNotFoundException;;

    public List<Producto> getAllProductos() throws SQLException, ClassNotFoundException;

}
