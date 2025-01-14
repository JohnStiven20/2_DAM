package pizzeria.controller.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import pizzeria.Controller.dao.impl.JdbcIngredienteDAO;
import pizzeria.Modelo.Alergeno;
import pizzeria.Modelo.Conexion;
import pizzeria.Modelo.Ingredientes;

public class JdbcIngredienteDAOTest {

    JdbcIngredienteDAO jdbcIngrediente = new JdbcIngredienteDAO();

    @Test
    void testFindByName() {

    }

    @Test
    void testGetAllIngredienteByidProducto() {

    }

    @Test
    void testInsertarProductoIngrediente() {

    }

    @Test
    void testSave() {

    }

    @Test
    void testSaveWithIngrediente() {

    }

    @Test
    void getAllIngredienteByidProducto() throws ClassNotFoundException, SQLException {
        Connection con = new Conexion().getConexion();

        List<Alergeno> alergenosTomate = new ArrayList<>();
        alergenosTomate.add(new Alergeno("Pelizilina"));
        alergenosTomate.add(new Alergeno("Gluten"));
        alergenosTomate.add(new Alergeno("Lactosa"));
        alergenosTomate.add(new Alergeno("Frutos secos"));

        List<Alergeno> alergenosQueso = new ArrayList<>();
        alergenosQueso.add(new Alergeno("leche"));
        alergenosQueso.add(new Alergeno("leche1"));
        alergenosQueso.add(new Alergeno("leche2"));

        List<Ingredientes> ingredientesPizza = new ArrayList<>();
        ingredientesPizza.add(new Ingredientes("Tomate10", alergenosTomate));
        ingredientesPizza.add(new Ingredientes("Queso Mozzarella", alergenosQueso));
        ingredientesPizza.add(new Ingredientes("Peperonni", alergenosQueso));

        List<Ingredientes> listaIngredientes = jdbcIngrediente.getAllIngredientesByIdProducto(con, 66);

        assertEquals(ingredientesPizza.size() , listaIngredientes.size());
    }

}
