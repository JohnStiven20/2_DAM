package pizzeria.Controller.dao;

import java.sql.SQLException;
import java.util.List;

import pizzeria.Modelo.Producto;

public interface ProductoJpaDao {

    public void save(Producto producto)  throws Exception;

    public void delete(int idProducto) ;

    public void update(Producto producto) ;

    public Producto getProductoById(int idProducto) ;;

    public List<Producto> getAllProductos() ;
}
