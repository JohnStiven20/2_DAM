package pizzeria.Controller.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import pizzeria.Modelo.Ingredientes;

public interface InnerIngredienteDAO {

        public void save(Connection con, int id_producto, Ingredientes ingrediente)
                        throws SQLException, ClassNotFoundException;

        public List<Ingredientes> getAllIngredientesByIdProducto(Connection con, int id)   throws SQLException, ClassNotFoundException;

}