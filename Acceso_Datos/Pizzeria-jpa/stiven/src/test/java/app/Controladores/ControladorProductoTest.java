package app.Controladores;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import app.Enums.Size;
import app.Modelo.Ingrediente;
import app.Modelo.Pizza;
import app.Modelo.Producto;
import app.Modelo.utils.DatabaseConfPizzeria;

public class ControladorProductoTest {

    private ControladorProducto controladorProducto;

    @BeforeEach
    void setUp() {
        controladorProducto = new ControladorProducto();
        try {
            // Configurar la base de datos antes de cada test
            DatabaseConfPizzeria.dropTables();
            DatabaseConfPizzeria.createTable();
            DatabaseConfPizzeria.cargarDatosEnLaBaseDatos();
        } catch (SQLException e) {
            System.out.println("Error al configurar la base de datos: " + e.getMessage());
        }
    }

    @Test
    void testCatalogoProductos() throws SQLException {
        List<Producto> productos;
        
            productos = controladorProducto.catalogoProductos();
            assertNotNull(productos, "La lista de productos no debe ser nula");
            assertFalse(productos.isEmpty(), "La lista de productos no debe estar vacía");

            for (Producto producto : productos) {
                assertNotNull(producto.getId(), "El producto debe tener un ID");
                assertNotNull(producto.getNombre(), "El producto debe tener un nombre");
            }
        
    }

    @Test
    void testDelete() throws SQLException {
       
            Producto producto = controladorProducto.catalogoProductos().stream().findFirst().orElse(null);
            assertNotNull(producto, "Debe haber al menos un producto en la base de datos");

            // Eliminar el producto
            boolean eliminado = controladorProducto.delete(producto);
            assertTrue(eliminado, "El producto debe eliminarse correctamente");

            
        
    }

    @Test
    void testGetAlergenosByIngredient() throws SQLException {

        Ingrediente ingrediente = new Ingrediente(1, null, null);
        List<String> alergenos = null;

        
            alergenos = controladorProducto.getAlergenosByIngredient(ingrediente);
            assertNotNull(alergenos, "La lista de alérgenos no debe ser nula");
            assertFalse(alergenos.isEmpty(), "La lista de alérgenos no debe estar vacia");
            assertTrue(alergenos.contains("Gluten"), "La lista debe contener al menos un alérgeno valido");
        
    }

    @Test
    void testSave() throws SQLException {
      
            ArrayList<Ingrediente> ingredientes = new ArrayList<>();
            ingredientes.add(new Ingrediente("Cebolla", null));

            Pizza nuevoProducto = new Pizza("Pizza Margherita", 10.99, Size.MEDIANO, ingredientes);
            boolean guardado = controladorProducto.save(nuevoProducto);
            assertTrue(guardado, "El producto debe guardarse correctamente");

            // Buscamos el producto guardado usando un metodo adecuado
            Producto productoGuardado = controladorProducto.getAllProducts().stream()
                    .filter(p -> p.getNombre().equals("Pizza Margherita"))
                    .findFirst()
                    .orElse(null);

            assertNotNull(productoGuardado, "El producto guardado no debe ser nulo");
            assertEquals("Pizza Margherita", productoGuardado.getNombre(),"El nombre del producto guardado debe coincidir");
        
    }

    
}
