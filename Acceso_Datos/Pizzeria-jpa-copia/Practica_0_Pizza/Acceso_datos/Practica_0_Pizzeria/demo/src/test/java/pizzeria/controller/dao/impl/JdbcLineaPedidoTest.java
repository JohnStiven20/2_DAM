package pizzeria.controller.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;

import pizzeria.Controller.dao.impl.JdbcLineaPedido;
import pizzeria.Modelo.Conexion;
import pizzeria.Modelo.LineaPedido;

public class JdbcLineaPedidoTest {

    JdbcLineaPedido jdbcLineaPedido = new JdbcLineaPedido();

    @Test
    void testSave() throws ClassNotFoundException, SQLException {
        Connection con = new Conexion().getConexion();
        jdbcLineaPedido.save(con, 1, 1, 9);
    }

    @Test
    void testgetAllLineaPedidonbyID() throws ClassNotFoundException, SQLException {
        List<LineaPedido> listaLineaPedido = jdbcLineaPedido.getAllLineaPedidonbyID(1);
        assertEquals(2, listaLineaPedido.size());
    }

}
