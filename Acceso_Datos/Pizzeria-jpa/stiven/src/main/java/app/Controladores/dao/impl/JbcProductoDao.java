package app.Controladores.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import app.Controladores.dao.ProductoDao;
import app.Enums.Size;
import app.Modelo.Bebida;
import app.Modelo.Ingrediente;
import app.Modelo.LineaPedido;
import app.Modelo.Pasta;
import app.Modelo.Pizza;
import app.Modelo.Producto;
import static app.Modelo.utils.DatabaseConfPizzeria.DELETE_PRODUCTO;
import static app.Modelo.utils.DatabaseConfPizzeria.INSERT_ALOGENO;
import static app.Modelo.utils.DatabaseConfPizzeria.INSERT_INGREDIENTE;
import static app.Modelo.utils.DatabaseConfPizzeria.INSERT_INGREDIENTE_ALOGENO;
import static app.Modelo.utils.DatabaseConfPizzeria.INSERT_PRODUCTO;
import static app.Modelo.utils.DatabaseConfPizzeria.INSERT_PRODUCTO_INGREDIENTE;
import static app.Modelo.utils.DatabaseConfPizzeria.SELECT_ALOGENO_MEDIANTE_NOMBRE;
import static app.Modelo.utils.DatabaseConfPizzeria.SELECT_INGREDIENTE_MEDIANTE_NOMBRE;
import static app.Modelo.utils.DatabaseConfPizzeria.SELECT_JOIN_INGREDIENTE_ALOGENO;
import static app.Modelo.utils.DatabaseConfPizzeria.SELECT_JOIN_INGREDIENTE_ALOGENO_POR_ID;
import static app.Modelo.utils.DatabaseConfPizzeria.SELECT_JOIN_LINEA_PEDIDO_POR_PEDIDO_ID;
import static app.Modelo.utils.DatabaseConfPizzeria.SELECT_JOIN_PRODUCTO_INGREDIENTE_POR_ID;
import static app.Modelo.utils.DatabaseConfPizzeria.SELECT_PRODUCTO_ALL;
import static app.Modelo.utils.DatabaseConfPizzeria.SELECT_PRODUCTO_MEDIANTE_NOMBRE_Y_SIZE;
import static app.Modelo.utils.DatabaseConfPizzeria.UPDATE_PRODUCTO;
import static app.Modelo.utils.DatabaseConfPizzeria.getConnection;

public class JbcProductoDao implements ProductoDao {

    public int findByIngredientePorNombre(String nombre, Connection connection) throws SQLException {

        try {

            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_INGREDIENTE_MEDIANTE_NOMBRE);
            preparedStatement.setString(1, nombre);

            int id = 0;

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    id = resultSet.getInt("ingrediente.id");
                }
            }

            if (id > 0) {
                return id;
            } else {
                return -1;
            }

        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public int findByAlergenoPorNombre(String nombre, Connection connection) throws SQLException {

        try {

            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALOGENO_MEDIANTE_NOMBRE);
            preparedStatement.setString(1, nombre);

            int id = 0;

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    id = resultSet.getInt("alogeno.id");
                }
            }

            if (id > 0) {
                return id;
            } else {
                return -1;
            }

        } catch (SQLException e) {
            throw new SQLException(e);
        }

    }

    public int findyByIngredienteAlogenoPorId(int ingrediente_id, int alergeno_id, Connection connection)
            throws SQLException {

        try (PreparedStatement preparedStatement = connection
                .prepareStatement(SELECT_JOIN_INGREDIENTE_ALOGENO_POR_ID)) {

            preparedStatement.setInt(1, ingrediente_id);
            preparedStatement.setInt(2, alergeno_id);

            int id = 0;

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    id = resultSet.getInt("id");
                }

            }

            if (id > 0) {
                return id;
            } else {
                return -1;
            }

        } catch (SQLException e) {
            System.out.println("vIVA ESPAÑA");
            throw new SQLException(e.getMessage());
        }
    }

    public boolean findByProductoMedianteNombreYSize(Producto producto, Connection connection) throws SQLException {

        int id = 0;

        try {

            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCTO_MEDIANTE_NOMBRE_Y_SIZE);
            preparedStatement.setString(1, producto.getNombre());

            if (producto instanceof Pizza) {
                Pizza pizza = ((Pizza) producto);
                preparedStatement.setString(2, pizza.getSize().toString());
            } else if (producto instanceof Pasta) {
                preparedStatement.setString(2, null);
            } else {
                Bebida bebida = ((Bebida) producto);
                preparedStatement.setString(2, bebida.getSize().toString());
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    id = resultSet.getInt("id");
                }

            }

            if (id > 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }

    }

    @Override
public boolean delete(Producto producto) throws SQLException {
    
    try (Connection connection = getConnection()) {
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCTO);
        preparedStatement.setInt(1, producto.getId());
        int filasAfectadas = preparedStatement.executeUpdate();
        
        return filasAfectadas > 0;
    } catch (SQLException e) {
        throw new SQLException(e.getMessage());
    }
}


    @Override
    public void update(Producto producto, Size size) throws SQLException {
        
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCTO);
            preparedStatement.setString(1, size.name());
            preparedStatement.execute();
            
        } catch (SQLException e) {
            throw new SQLDataException(e.getMessage());
        }
        
    }

    @Override
    public boolean save(Producto producto) throws SQLException {

    Connection connection = getConnection();

    boolean estado = findByProductoMedianteNombreYSize(producto, connection);

    // Si el producto ya existe, retornamos false.
    if (estado) {
        return false;
    }

    try (connection) {

        connection.setAutoCommit(false);

        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCTO, Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setString(1, producto.getNombre());
        preparedStatement.setDouble(2, producto.getPrecio());

        if (producto instanceof Pizza) {
            Pizza pizza = ((Pizza) producto);
            preparedStatement.setString(3, pizza.getSize().toString());
            preparedStatement.setString(4, "PIZZA");
        } else if (producto instanceof Pasta) {
            preparedStatement.setString(3, null);
            preparedStatement.setString(4, "PASTA");
        } else {
            Bebida bebida = ((Bebida) producto);
            preparedStatement.setString(3, bebida.getSize().toString());
            preparedStatement.setString(4, "BEBIDA");
        }

        preparedStatement.execute();

        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                producto.setId(generatedKeys.getInt(1));
            }
        }

        if (producto instanceof Pizza) {
            Pizza pizza = ((Pizza) producto);
            for (int i = 0; i < pizza.getIngredientes().size(); i++) {
                int ingrediente_id = saveIngrediente(pizza.getIngredientes().get(i), connection);
                saveWithProductoIngrediente(producto.getId(), ingrediente_id, connection);
            }
        } else if (producto instanceof Pasta) {
            Pasta pasta = ((Pasta) producto);
            for (int i = 0; i < pasta.getIngredientes().size(); i++) {
                int ingrediente_id = saveIngrediente(pasta.getIngredientes().get(i), connection);
                saveWithProductoIngrediente(producto.getId(), ingrediente_id, connection);
            }
        } else if (producto instanceof Bebida) {
            connection.commit();
        }

        connection.commit();
        return true;

    } catch (SQLException e) {
        if (connection != null) {
            connection.rollback();
        }
        throw new SQLException(e.getMessage());
    }
}


    @Override
    public List<Producto> getAllProducts() throws SQLException {

        ArrayList<Producto> listaProductos = new ArrayList<>();

        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCTO_ALL);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {

                    String tipo = resultSet.getNString("producto.tipo");

                    if (tipo.equalsIgnoreCase(Pizza.class.getSimpleName())) {

                        int id = resultSet.getInt("producto.id");
                        String nombre = resultSet.getNString("producto.nombre");
                        double precio = resultSet.getDouble("producto.precio");
                        Size size = Size.valueOf(resultSet.getString("producto.size"));

                        Pizza pizza = new Pizza(id, nombre, precio, size, null);

                        List<Ingrediente> listaIngredientes = findByProduct(pizza);

                        pizza.setIngredientes(listaIngredientes);
                        listaProductos.add(pizza);

                    } else if (tipo.equalsIgnoreCase(Pasta.class.getSimpleName())) {

                        int id = resultSet.getInt("producto.id");
                        String nombre = resultSet.getNString("producto.nombre");
                        double precio = resultSet.getDouble("producto.precio");

                        Pasta pasta = new Pasta(id, null, nombre, precio);
                        List<Ingrediente> listaIngredientes = findByProduct(pasta);
                        pasta.setIngredientes(listaIngredientes);
                        listaProductos.add(pasta);

                    } else if (tipo.equalsIgnoreCase(Bebida.class.getSimpleName())) {

                        int id = resultSet.getInt("producto.id");
                        String nombre = resultSet.getNString("producto.nombre");
                        double precio = resultSet.getDouble("producto.precio");
                        Size size = Size.valueOf(resultSet.getString("producto.size"));
                        Bebida bebida = new Bebida(id, nombre, precio, size);
                        listaProductos.add(bebida);

                    }

                }

            } catch (SQLException e) {
                throw new SQLException(e.getMessage());
            }

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());

        }

        return listaProductos;
    }

    public List<LineaPedido> getProductsById(int pedido_id) throws SQLException {

        ArrayList<LineaPedido> lista = new ArrayList<>();

        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_JOIN_LINEA_PEDIDO_POR_PEDIDO_ID);
            preparedStatement.setInt(1, pedido_id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {

                    String tipo = resultSet.getNString("producto.tipo");
                    int linea_pedido_id = resultSet.getInt("linea_pedido.id");
                    int cantidad = resultSet.getInt("linea_pedido.cantidad");

                    if (tipo.equalsIgnoreCase(Pizza.class.getSimpleName())) {

                        int id = resultSet.getInt("producto.id");
                        String nombre = resultSet.getNString("producto.nombre");
                        double precio = resultSet.getDouble("producto.precio");
                        Size size = Size.valueOf(resultSet.getString("producto.size"));

                        Pizza pizza = new Pizza(id, nombre, precio, size, null);

                        List<Ingrediente> listaIngredientes = findByProduct(pizza);
                        pizza.setIngredientes(listaIngredientes);
                        LineaPedido lineaPedido = new LineaPedido(linea_pedido_id, cantidad, pizza);
                        lista.add(lineaPedido);

                    } else if (tipo.equalsIgnoreCase(Pasta.class.getSimpleName())) {

                        int id = resultSet.getInt("producto.id");
                        String nombre = resultSet.getNString("producto.nombre");
                        double precio = resultSet.getDouble("producto.precio");

                        Pasta pasta = new Pasta(id, null, nombre, precio);
                        List<Ingrediente> listaIngredientes = findByProduct(pasta);
                        pasta.setIngredientes(listaIngredientes);
                        LineaPedido lineaPedido = new LineaPedido(linea_pedido_id, cantidad, pasta);
                        lista.add(lineaPedido);

                    } else {

                        int id = resultSet.getInt("producto.id");
                        String nombre = resultSet.getNString("producto.nombre");
                        double precio = resultSet.getDouble("producto.precio");
                        Size size = Size.valueOf(resultSet.getString("producto.size"));
                        Bebida bebida = new Bebida(id, nombre, precio, size);
                        LineaPedido lineaPedido = new LineaPedido(linea_pedido_id, cantidad, bebida);
                        lista.add(lineaPedido);

                    }
                }
            }

        } catch (SQLException e) {

            throw new SQLException(e.getMessage());
        }

        return lista;

    }


    @Override
    public List<Ingrediente> findByProduct(Producto producto) throws SQLException {

        List<Ingrediente> listaIngredientes = new ArrayList<>();

        try (Connection connection = getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_JOIN_PRODUCTO_INGREDIENTE_POR_ID);
            preparedStatement.setInt(1, producto.getId());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {

                    int id = resultSet.getInt("id");
                    String nombre = resultSet.getString("nombre");
                    Ingrediente ingrediente = new Ingrediente(id, nombre, null);
                    List<String> listaAlogenos = findbyIngrediente(ingrediente);
                    ingrediente.setAlergenos(listaAlogenos);
                    listaIngredientes.add(ingrediente);

                }
            }
        }

        return listaIngredientes;
    }

    @Override
    public List<String> findbyIngrediente(Ingrediente ingrediente) throws SQLException {
        List<String> lista = new ArrayList<>();

        try (Connection connection = getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_JOIN_INGREDIENTE_ALOGENO);
            preparedStatement.setInt(1, ingrediente.getId());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    lista.add(resultSet.getString("nombre"));
                }
            }
        }

        return lista;
    }

    public void saveWithProductoIngrediente(int producto_id, int ingrediente_id, Connection connection) throws SQLException {

        try {
            PreparedStatement preparableStatement = connection.prepareStatement(INSERT_PRODUCTO_INGREDIENTE,Statement.RETURN_GENERATED_KEYS);
            preparableStatement.setInt(1, producto_id);
            preparableStatement.setInt(2, ingrediente_id);
            preparableStatement.execute();
            connection.commit();

        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
                throw new SQLException("Error al guardar saveProductoIngrediente", e);
            }

        }
    }

    public void saveWithIngredienteAlogeno(int ingrediente_id, int alogeno_id, Connection connection)
            throws SQLException {

        int hola = findyByIngredienteAlogenoPorId(ingrediente_id, alogeno_id, connection);

        if (hola == -1) {

            try {

                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INGREDIENTE_ALOGENO, Statement.RETURN_GENERATED_KEYS);

                preparedStatement.setInt(1, ingrediente_id);
                preparedStatement.setInt(2, alogeno_id);
                preparedStatement.execute();

            } catch (SQLException e) {
                if (connection != null) {
                    connection.rollback();
                    throw new SQLException("Error al guardar saveWithIngredienteAlogenos ", e);
                }
            }

        }
    }

    public int saveIngrediente(Ingrediente ingrediente, Connection connection) throws SQLException {

        int id = 0;

        int ingrediente_id = findByIngredientePorNombre(ingrediente.getNombre(), connection);

        if (ingrediente_id != -1) {
            return ingrediente_id;
        }

        try {

            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INGREDIENTE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, ingrediente.getNombre());
            preparedStatement.execute();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                while (resultSet.next()) {
                    id = resultSet.getInt(1);
                }
            }

            if (ingrediente.getAlergenos() != null ) {
                for (int i = 0; i < ingrediente.getAlergenos().size(); i++) {
                    int alogeno_id = saveAlogeno(ingrediente.getAlergenos().get(i), connection);
                    saveWithIngredienteAlogeno(id, alogeno_id, connection);
                }
            }

            return id;

        } catch (SQLException e) {
            connection.rollback();
            throw new SQLException("Error al guardar saveProductoIngrediente", e);

        }
    }

    public int saveAlogeno(String alogeno, Connection getConnection) throws SQLException {

        int id = 0;
        int alogeno_id = findByAlergenoPorNombre(alogeno, getConnection);

        if (alogeno_id != -1) {
            return alogeno_id;
        }

        try {

            PreparedStatement preparedStatement = getConnection.prepareStatement(INSERT_ALOGENO, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, alogeno);
            preparedStatement.execute();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    id = resultSet.getInt(1);
                }
            }

        } catch (SQLException e) {
            getConnection.rollback();
            throw new SQLException(e.getMessage());
        }

        return id;
    }  

}              