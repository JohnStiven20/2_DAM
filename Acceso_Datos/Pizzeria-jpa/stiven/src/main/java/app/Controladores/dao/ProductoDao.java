package app.Controladores.dao;

import java.sql.SQLException;
import java.util.List;

import app.Modelo.Alergeno;
import app.Modelo.Ingrediente;
import app.Modelo.Producto;
import jakarta.persistence.NoResultException;

public interface ProductoDao {

    boolean  delete(Producto producto) throws SQLException;

    void update(Producto producto) throws SQLException;

    void  save(Producto producto) throws SQLException , NoResultException;

    List<Producto> getAllProducts() throws SQLException;

    List<Ingrediente> findByProduct(Producto producto) throws SQLException;

    List<Alergeno> findbyIngrediente(Ingrediente producto) throws SQLException;

    
}
