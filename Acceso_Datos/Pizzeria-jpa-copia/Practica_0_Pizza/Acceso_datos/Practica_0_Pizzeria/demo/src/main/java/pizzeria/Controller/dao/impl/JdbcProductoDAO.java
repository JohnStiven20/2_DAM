package pizzeria.Controller.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pizzeria.Controller.dao.InnerProductoDAO;
import pizzeria.Modelo.Bebida;
import pizzeria.Modelo.Conexion;
import pizzeria.Modelo.Ingredientes;
import pizzeria.Modelo.Pasta;
import pizzeria.Modelo.Pizza;
import pizzeria.Modelo.Producto;
import pizzeria.Modelo.SizeApp;

import static pizzeria.utils.DatabaseConfig.*;
import pizzeria.Modelo.Tipo;

public class JdbcProductoDAO implements InnerProductoDAO {

    JdbcIngredienteDAO jdbcIngredienteDAO = new JdbcIngredienteDAO();

    @Override
    public void save(Producto producto) throws SQLException, ClassNotFoundException {
        Pizza pizza = null;
        Pasta pasta = null;

        try (Connection con = new Conexion().getConexion()) {
            con.setAutoCommit(false);
            try {
                int idProducto = guardarProductoYRetornarId(con, producto);
                if (producto instanceof Pizza) {
                    pizza = (Pizza) producto;
                    if (pizza.getIngredientes() != null && !pizza.getIngredientes().isEmpty()) {
                        manejarIngredientes(con, idProducto, pizza.getIngredientes());
                    }

                } else if (producto instanceof Pasta) {
                    pasta = (Pasta) producto;
                    if (pasta.getIngredientes() != null && pasta.getIngredientes().size() >= 0) {
                        manejarIngredientes(con, idProducto, pasta.getIngredientes());
                    }
                }
                con.commit();
            } catch (SQLException | ClassNotFoundException e) {
                con.rollback();
                throw e;
            }
        }
    }

    private void manejarIngredientes(Connection con, int idProducto, List<Ingredientes> listaIngredientes)
            throws ClassNotFoundException, SQLException {
        for (Ingredientes ingrediente : listaIngredientes) {
            Ingredientes ingredienteEncontrado = jdbcIngredienteDAO.findByName(ingrediente.getNombre());
            if (ingredienteEncontrado == null) {
                insertarIngredienteYRelacion(con, idProducto, ingrediente);
            } else {
                jdbcIngredienteDAO.relacionProductoIngrediente(con, idProducto, ingredienteEncontrado);
            }
        }
    }

    private int guardarProductoYRetornarId(Connection con, Producto producto)
            throws SQLException, ClassNotFoundException {
        try (PreparedStatement preparedStatement = con.prepareStatement(INSERT_PRODUCTO,
                Statement.RETURN_GENERATED_KEYS)) {
            if (producto instanceof Pizza) {
                savePizza(preparedStatement, (Pizza) producto);
            } else if (producto instanceof Bebida) {
                saveBebida(preparedStatement, (Bebida) producto);
            } else if (producto instanceof Pasta) {
                savePasta(preparedStatement, (Pasta) producto);
            }

            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("No se pudo obtener el ID generado para el producto.");
                }
            }
        }
    }

    private void insertarIngredienteYRelacion(Connection con, int idProducto, Ingredientes ingrediente)
            throws SQLException, ClassNotFoundException {
        jdbcIngredienteDAO.save(con, idProducto, ingrediente);
    }

    private void savePizza(PreparedStatement preparedStatement, Pizza pizza)
            throws SQLException, ClassNotFoundException {
        preparedStatement.setString(1, pizza.getNombre());
        preparedStatement.setDouble(2, pizza.getPrecio());
        preparedStatement.setString(3, Tipo.PIZZA.getValue());
        preparedStatement.setString(4, pizza.getSize().getValue());
    }

    private void saveBebida(PreparedStatement preparedStatement, Bebida bebida) throws SQLException {
        preparedStatement.setString(1, bebida.getNombre());
        preparedStatement.setDouble(2, bebida.getPrecio());
        preparedStatement.setString(3, Tipo.BEBIDA.getValue());
        preparedStatement.setString(4, bebida.getSize().getValue());

    }

    private void savePasta(PreparedStatement preparedStatement, Pasta pasta)
            throws SQLException, ClassNotFoundException {

        preparedStatement.setString(1, pasta.getNombre());
        preparedStatement.setDouble(2, pasta.getPrecio());
        preparedStatement.setString(3, Tipo.PASTA.getValue());
        preparedStatement.setNull(4, java.sql.Types.NULL);

    }

    @Override
    public List<Producto> getAllProductos() throws SQLException, ClassNotFoundException {
        List<Producto> listaProductos = new ArrayList<>();
        try (Connection con = new Conexion().getConexion();
                PreparedStatement preparedStatement = con.prepareStatement(SELECT_ALL_PRODUCTO);
                ResultSet resultado = preparedStatement.executeQuery()) {

            while (resultado.next()) {
                Producto producto = construirProductoDesdeResultSet(resultado, con);
                if (producto != null) {
                    listaProductos.add(producto);
                }
            }
        }
        return listaProductos;
    }

    @Override
    public Producto getProductoById(int idProducto) throws SQLException, ClassNotFoundException {
        Producto producto = null;
        try (Connection con = new Conexion().getConexion();
                PreparedStatement preparedStatement = con.prepareStatement(SELECT_ALL_PRODUCTO_ID)) {
            preparedStatement.setInt(1, idProducto);
            try (ResultSet resultado = preparedStatement.executeQuery()) {
                if (resultado.next()) {
                    producto = construirProductoDesdeResultSet(resultado, con);
                }
            }
        }
        return producto;
    }

    private Producto construirProductoDesdeResultSet(ResultSet resultado, Connection con)
            throws SQLException, ClassNotFoundException {
        int idProducto = resultado.getInt("id");
        String nombre = resultado.getString("nombre");
        Double precio = resultado.getDouble("precio");
        Tipo tipo = Tipo.valueOf(resultado.getString("tipo").toUpperCase());
        List<Ingredientes> listaIngredientes;

        switch (tipo.getValue()) {
            case "pasta":
                listaIngredientes = jdbcIngredienteDAO.getAllIngredientesByIdProducto(con, idProducto);
                return new Pasta(idProducto, nombre, precio, listaIngredientes);
            case "pizza":
                SizeApp size = SizeApp.valueOf(resultado.getString("size").toUpperCase());
                listaIngredientes = jdbcIngredienteDAO.getAllIngredientesByIdProducto(con, idProducto);
                return new Pizza(idProducto, nombre, precio, size, listaIngredientes);
            case "bebida":
                size = SizeApp.valueOf(resultado.getString("size").toUpperCase());
                return new Bebida(idProducto, nombre, precio, size);
            default:
                return null;
        }
    }

    @Override
    public void delete(int idProducto) throws SQLException, ClassNotFoundException {
        try (Connection con = new Conexion().getConexion();
                PreparedStatement preparedStatement = con.prepareStatement(DELETE_PRODUCTO)) {
            preparedStatement.setInt(1, idProducto);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void update(Producto producto) throws SQLException, ClassNotFoundException {
        try (Connection con = new Conexion().getConexion();
                PreparedStatement preparedStatement = con.prepareStatement(UPDATE_PRODUCTO)) {
            Pizza pizza = null;
            Pasta pasta = null;
            Bebida bebida = null;

            if (producto instanceof Pizza) {
                pizza = (Pizza) producto;
                preparedStatement.setString(1, pizza.getNombre());
                preparedStatement.setDouble(2, pizza.getPrecio());
                preparedStatement.setString(3, Tipo.PIZZA.getValue());
                preparedStatement.setString(4, pizza.getSize().getValue());
                preparedStatement.setInt(5, pizza.getId());

            } else if (producto instanceof Pasta) {
                pasta = (Pasta) producto;
                preparedStatement.setString(1, pasta.getNombre());
                preparedStatement.setDouble(2, pasta.getPrecio());
                preparedStatement.setString(3, Tipo.PASTA.getValue());
                preparedStatement.setNull(4, java.sql.Types.NULL);
                preparedStatement.setInt(5, pasta.getId());

            } else if (producto instanceof Bebida) {
                bebida = (Bebida) producto;
                preparedStatement.setString(1, bebida.getNombre());
                preparedStatement.setDouble(2, bebida.getPrecio());
                preparedStatement.setString(3, Tipo.BEBIDA.getValue());
                preparedStatement.setString(4, bebida.getSize().getValue());
                preparedStatement.setInt(5, bebida.getId());
            }
            preparedStatement.executeUpdate();
        }

    }

}
