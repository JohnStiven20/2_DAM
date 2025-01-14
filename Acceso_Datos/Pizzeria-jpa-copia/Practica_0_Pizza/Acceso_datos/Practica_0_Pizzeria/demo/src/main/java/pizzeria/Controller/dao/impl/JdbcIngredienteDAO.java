package pizzeria.Controller.dao.impl;

import java.lang.classfile.ClassFile.Option;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pizzeria.Controller.dao.InnerIngredienteDAO;
import pizzeria.Modelo.Alergeno;
import pizzeria.Modelo.Conexion;
import pizzeria.Modelo.Ingredientes;
import static pizzeria.utils.DatabaseConfig.*;

public class JdbcIngredienteDAO implements InnerIngredienteDAO {

    private final JdbcAlergeno jdbcAlergeno = new JdbcAlergeno();

    @Override
    public void save(Connection con, int idProducto, Ingredientes ingrediente)
            throws SQLException, ClassNotFoundException {
        con.setAutoCommit(false);
        int idIngrediente = insertarIngrediente(con, ingrediente);
        insertarProductoIngrediente(con, idProducto, idIngrediente);
        procesarAlergenos(con, idIngrediente, ingrediente);
        con.commit();
    }

    private void procesarAlergenos(Connection con, int idIngrediente, Ingredientes ingrediente)
            throws SQLException, ClassNotFoundException {
                Alergeno alergenoExiste = null ; 

        if (ingrediente.getAlergenos() == null || ingrediente.getAlergenos().isEmpty()) {
            return;
        }

        for (Alergeno alergeno : ingrediente.getAlergenos()) {
             alergenoExiste = jdbcAlergeno.findByName(con, alergeno.getNombre());
            if (alergenoExiste == null) {
                jdbcAlergeno.save(con, idIngrediente, alergeno);
            } else {
                jdbcAlergeno.relacionIngredienteAlergeno(con, idIngrediente, alergenoExiste.getId());
            }
        }
    }

    public void relacionProductoIngrediente(Connection con, int idProducto, Ingredientes ingrediente)
            throws SQLException, ClassNotFoundException {
        con.setAutoCommit(false);
        insertarProductoIngrediente(con, idProducto, ingrediente.getId());
        con.commit();
    }

    private int insertarIngrediente(Connection con, Ingredientes ingrediente) throws SQLException {
        int idIngrediente = 0;
        try (PreparedStatement preparedStatement = con.prepareStatement(INSERT_INGREDIENTE,
                PreparedStatement.RETURN_GENERATED_KEYS)) {

            con.setAutoCommit(false);
            preparedStatement.setString(1, ingrediente.getNombre());
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    idIngrediente = generatedKeys.getInt(1);
                }
            }

        }
        return idIngrediente;
    }

    public void insertarProductoIngrediente(Connection con, int idProducto, int idIngrediente)
            throws SQLException, ClassNotFoundException {

        if (!relationProductoIngredienteExists(con, idProducto, idIngrediente)) {
            try (PreparedStatement preparedStatement = con.prepareStatement(INSERT_PRODUCTO_iNGREDIENTE)) {
                preparedStatement.setInt(1, idProducto);
                preparedStatement.setInt(2, idIngrediente);
                preparedStatement.executeUpdate();
            }
        }
    }

    private boolean relationProductoIngredienteExists(Connection con, int idProducto, int idIngrediente)
            throws SQLException, ClassNotFoundException {
        int id = 0;
        try (
                PreparedStatement preparedStatement = con.prepareStatement(SELECT_PRODUCTO_INGREDIENTE_EXIST);) {
            preparedStatement.setInt(1, idProducto);
            preparedStatement.setInt(2, idIngrediente);

            try (ResultSet resultado = preparedStatement.executeQuery()) {
                if (resultado.next()) {
                    id = resultado.getInt("id");
                }
            }
            return id != 0;
        }
    }

    public Ingredientes findByName(String name) throws SQLException, ClassNotFoundException {
        Ingredientes ingrediente = null;
        try (Connection con = new Conexion().getConexion();
                PreparedStatement preparedStatement = con.prepareStatement(SELECT_INGREDIENTE_NOMBRE)) {

            con.setAutoCommit(false);
            preparedStatement.setString(1, name);

            try (ResultSet resultado = preparedStatement.executeQuery()) {

                if (resultado.next()) {
                    ingrediente = buildIngredienteFromResultSet(con, resultado);
                }

            } catch (Exception e) {
                con.rollback();
                throw e;
            }
            con.commit();
            return ingrediente;
        }

    }

    @Override
    public List<Ingredientes> getAllIngredientesByIdProducto(Connection con, int id)
            throws SQLException, ClassNotFoundException {

        List<Ingredientes> listaIngrediente = new ArrayList<Ingredientes>();
        try (PreparedStatement preparedStatement = con.prepareStatement(SELECT_PRODUCTO_INGREDIENTE)) {

            preparedStatement.setInt(1, id);
            try (ResultSet resultado = preparedStatement.executeQuery()) {
                while (resultado.next()) {
                    buildIngredienteFromResultSet(con, resultado);
                    listaIngrediente.add(buildIngredienteFromResultSet(con, resultado));
                }
            }

        }
        return listaIngrediente;
    }

    private Ingredientes buildIngredienteFromResultSet(Connection con, ResultSet resultado)
            throws SQLException, ClassNotFoundException {
        int idIngrediente = resultado.getInt("id");
        String nombreIngrediente = resultado.getString("nombre");
        List<Alergeno> alergenos = jdbcAlergeno.getAllAlergenoByidIngrediente(con, idIngrediente);
        return new Ingredientes(idIngrediente, nombreIngrediente, alergenos);
    }

}
