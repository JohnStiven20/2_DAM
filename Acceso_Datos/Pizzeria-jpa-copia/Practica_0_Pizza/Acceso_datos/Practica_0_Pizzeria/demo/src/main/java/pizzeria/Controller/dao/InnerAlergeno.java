package pizzeria.Controller.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import pizzeria.Modelo.Alergeno;

public interface InnerAlergeno {

    public void save(Connection con, int id, Alergeno alergeno) throws SQLException, ClassNotFoundException;

    public Alergeno findById(int id) throws SQLException, ClassNotFoundException;

    public Alergeno findByName(Connection con, String name) throws SQLException, ClassNotFoundException;

    public void relacionIngredienteAlergeno(Connection con, int id_ingrediente, int id_alergeno)
            throws SQLException, ClassNotFoundException;

    public List<Alergeno> getAllAlergenoByidIngrediente(Connection con, int id_ingrediente)
            throws SQLException, ClassNotFoundException;

}