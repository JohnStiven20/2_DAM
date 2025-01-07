package app.Modelo.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConf {

    public static final String URL = "jdbc:mysql://localhost:3306/concesionario";
    public static final String USER = "root";
    public static final String PASS = "admin";

    public static final String CREATE_TABLE_CLIENTE = "CREATE TABLE IF NOT EXISTS clientes (" +
            " id INT AUTO_INCREMENT PRIMARY KEY," +
            "dni VARCHAR(255) NOT NULL, " +
            "nombre VARCHAR(255) NOT NULL, " +
            "apellidos VARCHAR(255) NOT NULL," +
            "telefono VARCHAR(255) NULL UNIQUE" +
            ");";

    public static final String CREATE_TABLE_COCHE = "CREATE TABLE IF NOT EXISTS COCHES (" +
            "matricula VARCHAR(255) PRIMARY KEY," +
            "marca VARCHAR(255) NOT NULL," +
            "modelo VARCHAR(255) NOT NULL," +
            "fecha DATE NULL," +
            "propietario INT NOT NULL," +
            "FOREIGN KEY(propietario) REFERENCES CLIENTES(id) ON DELETE CASCADE ON UPDATE CASCADE);";

    public static final String INSERT_CLIENTE = "INSERT INTO CLIENTES (dni, nombre, apellidos, telefono) VALUES (?,?,?,?)";
    public static final String INSERT_COCHE = "INSERT INTO COCHES (matricula, marca, modelo, fecha, propietario) VALUES (?, ?, ? ,?, ?)";

    public static final String DELETE_CLIENTE = "DELETE FROM CLIENTES WHERE dni=?";
    public static final String DELETE_COCHE = "DELETE FROM COCHES WHERE matricula= ?";

    public static final String UPDATE_CLIENTE = "UPDATE CLIENTES SET nombre = ?, apellidos = ? , telefono = ? WHERE dni = ? ";
    public static final String UPDATE_COCHE = "UPDATE COCHES SET marca= ?, modelo = ? , fecha= ?, propietario = ? WHERE matricula = ? ";

    private static final String DROP_TABLE_CLIENTE = "DROP TABLE IF EXISTS clientes";
    private static final String DROP_TABLE_COCHE = "DROP TABLE IF EXISTS coches";


    public static final String SELECT_CON_JOIN = 
            "SELECT coches.fecha, coches.Marca, coches.Matricula, coches.Modelo, coches.propietario, clientes.Apellidos, clientes.DNI, clientes.Nombre, clientes.Telefono, clientes.id " +
            "FROM coches " +
            "JOIN clientes ON coches.propietario = clientes.id " +
            "WHERE coches.propietario = ?";
    public static final String SELECT_CLIENTES = "SELECT clientes.dni, clientes.nombre" + 
    "FROM clientes";

    public static void createTables() throws SQLException {

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
            Statement stmt = conn.createStatement()) {


            stmt.execute(CREATE_TABLE_CLIENTE);
            System.out.println("Se ha creado la tabla clientes");
            stmt.execute(CREATE_TABLE_COCHE);
            System.out.println("Se ha creado la tabla coches");
        }
    }

    public static void dropAndCreateTables() throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
            Statement stmt = conn.createStatement()) {
            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
            stmt.execute(DROP_TABLE_CLIENTE);
            System.out.println("Se ha borrado la tabla clientes");
            stmt.execute(DROP_TABLE_COCHE);
            System.out.println("Se ha borrado la tabla coches");
            createTables();
        }
    }

}
