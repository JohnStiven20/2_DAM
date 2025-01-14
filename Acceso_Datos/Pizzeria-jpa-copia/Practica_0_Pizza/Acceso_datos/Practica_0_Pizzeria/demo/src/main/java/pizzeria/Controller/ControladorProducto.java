package pizzeria.Controller;

import java.sql.SQLException;
import java.util.List;

import pizzeria.Controller.dao.InnerProductoDAO;
import pizzeria.Controller.dao.impl.JdbcProductoDAO;
import pizzeria.Controller.dao.impl_jpa.JpaProducto;
import pizzeria.Modelo.Producto;

public class ControladorProducto {

    InnerProductoDAO jdbcProductoDAO = new JpaProducto();

    public void registrarProducto(Producto producto) throws ClassNotFoundException, SQLException {
        jdbcProductoDAO.save(producto);
    }

    public List<Producto> getAllProducts() throws ClassNotFoundException, SQLException {
        return jdbcProductoDAO.getAllProductos();
    }

    public void saveProduct(Producto producto) throws ClassNotFoundException, SQLException {
        jdbcProductoDAO.save(producto);
    }

    public Producto getProductoById(int id) throws ClassNotFoundException, SQLException {
        return jdbcProductoDAO.getProductoById(id);
    }

    public void eliminarProducto(int idProducto) throws ClassNotFoundException, SQLException {
        jdbcProductoDAO.delete(idProducto);
    }

    public void actualizarProducto(Producto producto) throws ClassNotFoundException, SQLException {
        jdbcProductoDAO.update(producto);
    }
}
