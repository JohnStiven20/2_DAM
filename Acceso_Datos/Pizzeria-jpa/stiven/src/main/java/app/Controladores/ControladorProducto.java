package app.Controladores;

import java.sql.SQLException;
import java.util.List;

import app.Controladores.dao.ProductoDao;
import app.Controladores.dao.impl.JpaProducto;
import app.Modelo.Alergeno;
import app.Modelo.Ingrediente;
import app.Modelo.Producto;

public class ControladorProducto {

    private final ProductoDao productoDao2;

    public ControladorProducto() {
        this.productoDao2 = new JpaProducto();
    }

    public void   save(Producto producto) throws SQLException {
        productoDao2.save(producto);
    }

    public boolean  delete(Producto producto) throws SQLException {
        return productoDao2.delete(producto);
    }

    public void   update(Producto producto) throws SQLException {
        productoDao2.update(producto);
    }

    public List<Producto> getAllProducts() throws SQLException {
        return productoDao2.getAllProducts();
    }

    public List<Ingrediente> getIngredientsByProduct(Producto producto) throws SQLException{
        return productoDao2.findByProduct(producto);
    }

    public List<Alergeno> getAlergenosByIngredient(Ingrediente ingrediente) throws SQLException {
        return productoDao2.findbyIngrediente(ingrediente);
    }

    public List<Producto>  catalogoProductos() throws SQLException{
        return  productoDao2.getAllProducts();
    }

}
