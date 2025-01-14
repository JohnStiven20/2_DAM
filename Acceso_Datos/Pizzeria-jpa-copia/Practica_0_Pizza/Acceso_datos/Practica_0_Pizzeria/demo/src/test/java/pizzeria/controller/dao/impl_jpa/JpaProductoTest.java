package pizzeria.controller.dao.impl_jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import pizzeria.Controller.dao.impl_jpa.JpaProducto;
import pizzeria.Modelo.Alergeno;
import pizzeria.Modelo.Bebida;
import pizzeria.Modelo.Ingredientes;
import pizzeria.Modelo.Pasta;
import pizzeria.Modelo.Pizza;
import pizzeria.Modelo.Producto;
import pizzeria.Modelo.SizeApp;

public class JpaProductoTest {

    JpaProducto jpaProducto = new JpaProducto();

    @Test
    void testDelete() {
            
    }

    @Test
    void testGetAllProductos() throws Exception {
        testGetProductoById();
        List<Producto> productos = jpaProducto.getAllProductos();  
        assertEquals(productos.size(), 4);
    }

    @Test
    void testGetProductoById() throws Exception {
        Bebida bebida = new Bebida("cocola", 12, SizeApp.GRANDE);
        jpaProducto.save(bebida);

        List<Ingredientes> ingredientes = new ArrayList<Ingredientes>();
        List<Alergeno> alergenos = new ArrayList<Alergeno>();
        alergenos.add(new Alergeno("paco"));

        ingredientes.add(new Ingredientes("tomae", alergenos));
        Pasta pasta = new Pasta("Pasta", 12, ingredientes);
        jpaProducto.save(pasta);

        List<Ingredientes> ingredientes1 = new ArrayList<Ingredientes>();
        List<Alergeno> alergenos1 = new ArrayList<Alergeno>();
        alergenos1.add(new Alergeno("pedro"));

        ingredientes1.add(new Ingredientes("tomaede", alergenos));
        Pizza pizza = new Pizza("pizza", 12, SizeApp.GRANDE, ingredientes1);
        jpaProducto.save(pizza);

        List<Ingredientes> ingredientes2 = new ArrayList<Ingredientes>();
        List<Alergeno> alergenos2 = new ArrayList<Alergeno>();
        alergenos2.add(new Alergeno("pedro"));
        ingredientes2.add(new Ingredientes("tomaededdw", alergenos));

        Pizza pizza2 = new Pizza("pizza2", 12, SizeApp.GRANDE, ingredientes2);
        jpaProducto.save(pizza2);
    }

    void testSave() throws Exception {

    }

    @Test
    void testUpdate() {

    }
}
