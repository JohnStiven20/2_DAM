package app;

import java.sql.SQLException;

import app.Controladores.dao.impl.JpaProducto;
import app.Modelo.Ingrediente;
import app.Modelo.Producto;

public class MainProductoJpa {
    public static void main(String[] args) {
        
        JpaProducto producto = new JpaProducto();

        Producto d = new Producto(1, null, 0);

        try {

            producto.getAllProducts().forEach(x -> System.out.println(x));

            System.out.println("VIVA ESPAÑA");
            producto.findByProduct(d).forEach(x -> System.out.println(x + " VIAVA ESPAÑA"));

            System.out.println("----------efewfwearrrrrrrrr");

            Ingrediente ingrediente = new Ingrediente(0, null, null);

            ingrediente.setId(1);

            producto.findbyIngrediente(ingrediente).forEach(x -> System.out.println(x + " HOLA BUENAS TARDES"));
            
              
        } catch (SQLException e) {
            System.out.println("-----------------------------------------------------------------------------");
            System.out.println(e.getMessage());
        }
    }
}
