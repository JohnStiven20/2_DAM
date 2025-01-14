package pizzeria.Controller.dao.impl;

import static pizzeria.utils.DatabaseConfig.INSERT_ALERGENO;
import static pizzeria.utils.DatabaseConfig.INSERT_INGREDIENTE_ALERGENO;
import static pizzeria.utils.DatabaseConfig.SELECT_ALERGENO_ID;
import static pizzeria.utils.DatabaseConfig.SELECT_ALERGENO_NOMBRE;
import static pizzeria.utils.DatabaseConfig.SELECT_INGREDIENTE_ALERGENO;
import static pizzeria.utils.DatabaseConfig.SELECT_INGREDIENTE_ALERGENO_EXIST;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pizzeria.Controller.dao.InnerAlergeno;
import pizzeria.Modelo.Alergeno;
import pizzeria.Modelo.Conexion;

public class JdbcAlergeno implements InnerAlergeno {

    @Override
    public void save(Connection con, int idIngrediente, Alergeno alergeno)
            throws SQLException, ClassNotFoundException {
        try (Statement statement = con.createStatement();
                PreparedStatement preparedStatement = con.prepareStatement(INSERT_ALERGENO,
                        statement.RETURN_GENERATED_KEYS)) {
            int idAlergeno = 0;
            con.setAutoCommit(false);
            preparedStatement.setString(1, alergeno.getNombre());
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    idAlergeno = generatedKeys.getInt(1);
                }
            }

            relacionIngredienteAlergeno(con, idIngrediente, idAlergeno);
        }
    }

    @Override
    public void relacionIngredienteAlergeno(Connection con, int id_ingrediente, int id_alergeno)
            throws SQLException, ClassNotFoundException {
        con.setAutoCommit(false);
        if (!relacionIngredienteAlergenoExite(id_ingrediente, id_alergeno)) {
            try (PreparedStatement preparedStatement2 = con.prepareStatement(INSERT_INGREDIENTE_ALERGENO)) {
                preparedStatement2.setInt(1, id_ingrediente);
                preparedStatement2.setInt(2, id_alergeno);
                preparedStatement2.executeUpdate();
                con.commit();
            }
        }

    }

    @Override
    public Alergeno findById(int id) throws SQLException, ClassNotFoundException {
        Alergeno alergeno = null;
        try (Connection con = new Conexion().getConexion();
                PreparedStatement preparedStatement = con.prepareStatement(SELECT_ALERGENO_ID)) {
            con.setAutoCommit(false);
            preparedStatement.setInt(1, id);
            try (ResultSet resultado = preparedStatement.executeQuery()) {
                if (resultado.next()) {
                    int idAlergeno = resultado.getInt("id");
                    String nombreAlergeno = resultado.getString("nombre");
                    alergeno = new Alergeno(idAlergeno, nombreAlergeno);
                }
            } catch (Exception e) {
                con.rollback();
                throw e;
            }
            con.commit();
            return alergeno;
        }
    }

    public List<Alergeno> getAllAlergenoByidIngrediente(Connection con, int id_ingrediente)
            throws SQLException, ClassNotFoundException {
        List<Alergeno> alergenos = new ArrayList<Alergeno>();
        try (PreparedStatement preparedStatement = con.prepareStatement(SELECT_INGREDIENTE_ALERGENO);) {

            preparedStatement.setInt(1, id_ingrediente);
            try (ResultSet resultado = preparedStatement.executeQuery()) {

                while (resultado.next()) {
                    int idAlergeno = resultado.getInt("id");
                    String nombreAlergeno = resultado.getString("nombre");
                    alergenos.add(new Alergeno(idAlergeno, nombreAlergeno));
                }
            }
            return alergenos;
        }
    }

    public boolean relacionIngredienteAlergenoExite(int id_ingrediente, int id_alergeno)
            throws SQLException, ClassNotFoundException {
        int id = 0;
        try (Connection con = new Conexion().getConexion();
                PreparedStatement preparedStatement = con.prepareStatement(SELECT_INGREDIENTE_ALERGENO_EXIST);) {
            preparedStatement.setInt(1, id_ingrediente);
            preparedStatement.setInt(2, id_alergeno);
            try (ResultSet resultado = preparedStatement.executeQuery()) {
                if (resultado.next()) {
                    id = resultado.getInt("id");
                }
            }
            return id != 0;
        }
    }

    @Override
    public Alergeno findByName(Connection con, String name) throws SQLException, ClassNotFoundException {
        Alergeno alergeno = null;
        int idAlergeno = 0;
        String nombreAlergeno = null;
        try (PreparedStatement preparedStatement = con.prepareStatement(SELECT_ALERGENO_NOMBRE)) {
            con.setAutoCommit(false);
            preparedStatement.setString(1, name);
            try (ResultSet resultado = preparedStatement.executeQuery()) {

                if (resultado.next()) {
                    idAlergeno = resultado.getInt("id");
                    nombreAlergeno = resultado.getString("nombre");
                    alergeno = new Alergeno(idAlergeno, nombreAlergeno);
                }

            } catch (Exception e) {
                con.rollback();
                throw e;
            }

            con.commit();
            return alergeno;
        }

    }

}
