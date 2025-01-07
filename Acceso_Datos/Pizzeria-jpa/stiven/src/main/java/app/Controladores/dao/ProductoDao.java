package app.Controladores.dao;

import java.sql.SQLException;
import java.util.List;

import app.Enums.Size;
import app.Modelo.Ingrediente;
import app.Modelo.Producto;

public interface ProductoDao {

    boolean delete(Producto producto) throws SQLException;

    void update(Producto producto, Size size) throws SQLException;

    boolean save(Producto producto) throws SQLException;

    List<Producto> getAllProducts() throws SQLException;

    List<Ingrediente> findByProduct(Producto producto) throws SQLException;

    List<String> findbyIngrediente(Ingrediente producto) throws SQLException;

    
}
