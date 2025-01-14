package pizzeria.controller.dao.impl_jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import pizzeria.Controller.dao.impl_jpa.JpaIngrediente;
import pizzeria.Controller.dao.impl_jpa.JpaProducto;
import pizzeria.Modelo.Alergeno;
import pizzeria.Modelo.Ingredientes;
import pizzeria.Modelo.Pasta;

public class JpaIngredienteTest {

    JpaIngrediente jIngrediente = new JpaIngrediente();
    JpaProducto jpaProducto = new JpaProducto();

    @Test
    void testGetAllIngredientesByIdProducto() throws Exception {
        List<Ingredientes> ingredientes = new ArrayList<Ingredientes>();
        List<Alergeno> alergenos = new ArrayList<Alergeno>( );
        alergenos.add(new Alergeno("paco"));

        ingredientes.add(new Ingredientes("tomae", alergenos));
        Pasta pasta = new Pasta("Pasta", 12, ingredientes);
        jpaProducto.save(pasta);

        List<Ingredientes> ingredientesEncontrados = jIngrediente.getAllIngredientesByIdProducto(1);
        assertEquals(1, ingredientesEncontrados.size());
    }

    @Test
    void testSave() {
        Ingredientes ingrediente = new Ingredientes();
        List<Alergeno> alergenos = new ArrayList<>();
        alergenos.add(new Alergeno("arroz"));
        alergenos.add(new Alergeno("arroz2"));
        ingrediente.setAlergenos(alergenos);
        jIngrediente.save(ingrediente);
    }
}
