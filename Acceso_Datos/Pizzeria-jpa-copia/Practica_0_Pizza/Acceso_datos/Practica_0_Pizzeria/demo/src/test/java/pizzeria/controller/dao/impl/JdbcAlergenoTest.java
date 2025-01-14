package pizzeria.controller.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import pizzeria.Controller.dao.impl.JdbcAlergeno;
import pizzeria.Modelo.Alergeno;
import pizzeria.Modelo.Conexion;
import pizzeria.Modelo.Ingredientes;

public class JdbcAlergenoTest {

    JdbcAlergeno jdbcAlergeno = new JdbcAlergeno();

    @Test
    void testFindById() {

    }

    @Test
    void testFindByName() {

    }

    @Test
    void testSave() {

    }

    @Test
    void getAllAlergenoByidIngrediente() throws ClassNotFoundException, SQLException {
        Connection con = new Conexion().getConexion();

        List<Alergeno> alergenosTomate = new ArrayList<>();
        alergenosTomate.add(new Alergeno("Pelizilina"));
        alergenosTomate.add(new Alergeno("Gluten"));
        alergenosTomate.add(new Alergeno("Lactosa"));
        alergenosTomate.add(new Alergeno("Frutos secos"));

        List<Alergeno> listaAlergenos = jdbcAlergeno.getAllAlergenoByidIngrediente(con, 55);
        assertEquals(alergenosTomate.size(), listaAlergenos.size());
      
    }

}
