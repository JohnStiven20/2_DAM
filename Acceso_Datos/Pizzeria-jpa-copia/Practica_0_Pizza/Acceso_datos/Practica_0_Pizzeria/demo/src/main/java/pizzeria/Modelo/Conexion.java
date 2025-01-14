package pizzeria.Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
 
     private Connection conexion;
    private String url = "jdbc:mysql://localhost:3306/pizzeria_prueba";
    private String usuario = "root";
    private String contraseña = "admin";

    public Conexion() throws SQLException, ClassNotFoundException {
        this.conexion = DriverManager.getConnection(url, usuario, contraseña);
    }

    public Connection getConexion(){
        return conexion;
    }




}
