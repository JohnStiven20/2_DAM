package app.Controladores;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import app.Enums.Size;
import app.Modelo.Alergeno;
import app.Modelo.Ingrediente;
import app.Modelo.Pasta;
import app.Modelo.Pizza;
import app.Modelo.Producto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class ControladorProductoTest {

    private ControladorProducto controladorProducto;
    EntityManagerFactory entityManagerFactory;

    @BeforeEach
    void setUp() {
        controladorProducto = new ControladorProducto();
        insetarProductos();
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
        List<Alergeno> alergenos = null;

        alergenos = controladorProducto.getAlergenosByIngredient(ingrediente);
        assertNotNull(alergenos, "La lista de alérgenos no debe ser nula");
        assertFalse(alergenos.isEmpty(), "La lista de alérgenos no debe estar vacia");
        assertTrue(alergenos.contains(new Alergeno(1, "Lactosa")), "La lista debe contener al menos un alérgeno valido");

    }

    @Test
    void testUpdate() throws SQLException {

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {

            Producto producto = entityManager
            .createQuery("SELECT c FROM Producto c WHERE c.id = :id", Producto.class)
            .setParameter("id", 1)
            .getSingleResult();

            Pizza pizza = ((Pizza) producto);

            pizza.setNombre("Quatro Quesos 5");
            pizza.setPrecio(5.0);


            controladorProducto.update(pizza);

            Producto productoActualizado = entityManager.createQuery(
                    "SELECT c FROM Producto c WHERE c.id = :id", Producto.class)
                    .setParameter("id", 1)
                    .getSingleResult();

            Pizza pizzaActualizada = ((Pizza) productoActualizado);

            assertEquals("Quatro Quesos 5", pizzaActualizada.getNombre(), "El nombre de la pizza no coincide");
            assertEquals(5.0, pizzaActualizada.getPrecio(), 0.001, "El precio de la pizza no coincide");

        } catch (SQLException e) {
            throw new SQLException("Algo va mal");
        } finally {
            if (entityManager.isOpen()) {
                entityManager.close();
            }
        }

    }

    @Test
    void testGetIngrediente() throws SQLException {

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Producto producto = entityManager
            .createQuery("SELECT c FROM Producto c WHERE c.id = :id", Producto.class)
            .setParameter("id", 1)
            .getSingleResult();

        List<Ingrediente> ingredientes = controladorProducto.getIngredientsByProduct(producto);

        assertNotNull(ingredientes, "La lista de ingredientes no debe ser nula");
        assertFalse(ingredientes.isEmpty(), "La lista de ingredientes no debe estar vacia");
        assertTrue(ingredientes.contains(new Ingrediente(1,"Tomate", null)), "La lista debe contener al menos un alérgeno valido");

    }

    public void insetarProductos() {

        List<Ingrediente> ingredientes = new ArrayList<>();

        Alergeno lactosa = new Alergeno("Lactosa");
        Alergeno pepitilla = new Alergeno("Pepitilla");

        List<Alergeno> alergenos = new ArrayList<>();

        alergenos.add(lactosa);
        alergenos.add(pepitilla);

        Ingrediente tomate = new Ingrediente("Tomate", alergenos);
        Ingrediente cebolla = new Ingrediente("Cebolla", alergenos);

        ingredientes.add(tomate);
        ingredientes.add(cebolla);

        Pasta pasta = new Pasta("Pasta", 15, Size.GRANDE, ingredientes);
        Pizza pizza = new Pizza("Pizza", 0, Size.MEDIANO, ingredientes);

        try {
            controladorProducto.save(pizza);
            controladorProducto.save(pasta);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
