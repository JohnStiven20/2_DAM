package pizzeria.controller.dao.impl_jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import pizzeria.Controller.dao.impl_jpa.JpaAlergeno;
import pizzeria.Controller.dao.impl_jpa.JpaIngrediente;
import pizzeria.Modelo.Alergeno;
import pizzeria.Modelo.Ingredientes;

public class JpaAlergenoTest {

    JpaAlergeno jpaAlergeno = new JpaAlergeno();
    JpaIngrediente jpaIngrediente = new JpaIngrediente();

    @Test
    void testFindById() {

    }

    @Test
    void testFindByName() {

    }

    @Test
    void testGetAllAlergenoByidIngrediente() {

        // List<Ingredientes> ingredientes = new ArrayList<Ingredientes>();

        List<Alergeno> alergenos = new ArrayList<Alergeno>();
        alergenos.add(new Alergeno("paco"));
        alergenos.add(new Alergeno("pac2"));

        Ingredientes ingrediente = new Ingredientes("casa", alergenos);
        jpaIngrediente.save(ingrediente);

        List<Alergeno> alergenosEncontrados = jpaAlergeno.getAllAlergenoByidIngrediente(1);

        assertEquals(2, alergenosEncontrados.size());

    }

    @Test
    void testRelacionIngredienteAlergeno() {

    }

    @Test
    void testSave() {

    }
}
