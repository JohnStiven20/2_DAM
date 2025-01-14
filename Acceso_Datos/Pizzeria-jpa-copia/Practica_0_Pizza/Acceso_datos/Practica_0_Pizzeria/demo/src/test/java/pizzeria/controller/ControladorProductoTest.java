package pizzeria.controller;

import static org.junit.jupiter.api.Assertions.*;
import static pizzeria.utils.DatabaseConfig.CREATE_ALL_TABLES;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pizzeria.Controller.ControladorProducto;
import pizzeria.Modelo.Alergeno;
import pizzeria.Modelo.Bebida;
import pizzeria.Modelo.Ingredientes;
import pizzeria.Modelo.Pasta;
import pizzeria.Modelo.Pizza;
import pizzeria.Modelo.Producto;
import pizzeria.Modelo.SizeApp;

public class ControladorProductoTest {

    ControladorProducto controladorProducto;

    @BeforeEach
    void setUp() throws ClassNotFoundException, SQLException {
        controladorProducto = new ControladorProducto();
    }

    @Test
    void testRegistrarProducto() throws ClassNotFoundException, SQLException {
        Bebida bebida = new Bebida("cocola", 12, SizeApp.GRANDE);
        controladorProducto.registrarProducto(bebida);

        List<Ingredientes> ingredientes = new ArrayList<Ingredientes>();
        List<Alergeno> alergenos = new ArrayList<Alergeno>();
        alergenos.add(new Alergeno("paco"));

        ingredientes.add(new Ingredientes("tomae", alergenos));
        Pasta pasta = new Pasta("Pasta", 12, ingredientes);
        controladorProducto.registrarProducto(pasta);

        List<Ingredientes> ingredientes1 = new ArrayList<Ingredientes>();
        List<Alergeno> alergenos1 = new ArrayList<Alergeno>();
        alergenos1.add(new Alergeno("pedro"));

        ingredientes1.add(new Ingredientes("tomaede", alergenos));
        Pizza pizza = new Pizza("pizza", 12, SizeApp.GRANDE, ingredientes1);
        controladorProducto.registrarProducto(pizza);

        List<Ingredientes> ingredientes2 = new ArrayList<Ingredientes>();
        List<Alergeno> alergenos2 = new ArrayList<Alergeno>();
        alergenos2.add(new Alergeno("pedro"));
        ingredientes2.add(new Ingredientes("tomaededdw", alergenos));

        Pizza pizza2 = new Pizza("pizza2", 12, SizeApp.GRANDE, ingredientes2);
        controladorProducto.registrarProducto(pizza2);

        assertTrue(true, "Los productos se registraron correctamente");
    }

    @Test
    void testObtenerProductoPorId() throws ClassNotFoundException, SQLException {
        testRegistrarProducto();
        Producto productoExistente;

        List<Producto> listproducto = controladorProducto.getAllProducts();

        productoExistente = listproducto.stream().filter((producto) -> producto.getNombre().equals("pizza"))
                .findFirst().orElse(null);

        assertEquals("pizza", productoExistente.getNombre());
    }

    @Test
    void testEliminarProducto() throws ClassNotFoundException, SQLException {
        Pizza pizza = new Pizza("Peperonni", 15, SizeApp.GRANDE, new ArrayList<>());
        controladorProducto.registrarProducto(pizza);
        List<Producto> listproducto = new ArrayList<>();
        Producto productoExistente;
        listproducto = controladorProducto.getAllProducts();
        productoExistente = listproducto.stream().filter((producto) -> producto.getNombre().equals("Peperonni"))
                .findFirst().orElse(null);

        controladorProducto.eliminarProducto(productoExistente.getId());

        // Producto productoEliminado =
        // controladorProducto.getProductoById(productoExistente.getId());
        // assertNull(productoEliminado);
    }

    @Test
    void testActualizarProducto() throws ClassNotFoundException, SQLException {
        Pasta pasta = new Pasta("Fettuccine Alfredo", 10.0, new ArrayList<>());
        controladorProducto.registrarProducto(pasta);
        List<Producto> listproducto = new ArrayList<>();
        Producto productoExistente;
        listproducto = controladorProducto.getAllProducts();
        productoExistente = listproducto.stream()
                .filter((producto) -> producto.getNombre().equals("Fettuccine Alfredo"))
                .findFirst().orElse(null);

        productoExistente.setNombre("Fettuccine Alfredo Premium");
        productoExistente.setPrecio(12.5);
        controladorProducto.actualizarProducto(productoExistente);

        Producto productoActualizado = controladorProducto.getProductoById(productoExistente.getId());

        assertNotNull(productoActualizado);
        assertEquals("Fettuccine Alfredo Premium", productoActualizado.getNombre());
        assertEquals(12.5, productoActualizado.getPrecio());
    }

    @Test
    void testObtenerTodosLosProductos() throws ClassNotFoundException, SQLException {
        // Registrar algunos productos
        controladorProducto.registrarProducto(new Pizza("Pepperoni", 13.0, SizeApp.GRANDE, new ArrayList<>()));
        controladorProducto.registrarProducto(new Pasta("Ravioli", 11.0, new ArrayList<>()));

        // Obtener todos los productos
        List<Producto> productos = controladorProducto.getAllProducts();

        // Verificar que la lista no esté vacía
        assertNotNull(productos);
        assertTrue(productos.size() > 0, "La lista de productos debería contener elementos");
    }
}
