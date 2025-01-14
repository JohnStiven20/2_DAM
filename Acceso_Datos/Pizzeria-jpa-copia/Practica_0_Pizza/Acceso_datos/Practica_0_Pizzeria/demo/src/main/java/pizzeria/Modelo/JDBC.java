
package pizzeria.Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class JDBC {

    private String url = "jdbc:mysql://localhost:3306/";
    private String user = "root";
    private String password = "admin";

    public void insertarBaseDatos() {

        try (Connection con = DriverManager.getConnection(url, user, password);
                PreparedStatement preparedStatement = con.prepareStatement("CREATE TABLE CLIENTES( \r\n" + //
                        "    dni VARCHAR(255) PRIMARY KEY,   \r\n" + //
                        "    nombre VARCHAR(255) not null,     \r\n" + //
                        "    apellidos VARCHAR(255) not null,     \r\n" + //
                        "    telefono VARCHAR(255) NULL UNIQUE     \r\n" + //
                        ");")

        ) {
            preparedStatement.executeQuery();
        } catch (Exception e) {
            // TODO: handle exception
        }

        try (
                Connection con = DriverManager.getConnection(url, user, password);
                PreparedStatement preparedStatement = con.prepareStatement("CREATE TABLE COCHES (\r\n" + //
                        "    matricula VARCHAR(255) PRIMARY KEY,\r\n" + //
                        "    marca VARCHAR(255) NOT NULL,\r\n" + //
                        "    modelo VARCHAR(255) NOT NULL,\r\n" + //
                        "    fecha DATE NULL,\r\n" + //
                        "    propietario VARCHAR(255),\r\n" + //
                        "    FOREIGN KEY(propietario) REFERENCES CLIENTES(dni) ON DELETE NO ACTION ON UPDATE CASCADE\r\n"
                        + //
                        ");")

        ) {
            preparedStatement.executeQuery();

        } catch (Exception e) {
            // TODO: handle exception
        }

        try (
                Connection con = DriverManager.getConnection(url, user, password);
                PreparedStatement preparedStatement = con.prepareStatement("CREATE TABLE VENTAS (\r\n" + //
                        "    id INT AUTO_INCREMENT PRIMARY KEY,\r\n" + //
                        "    coche_vendido VARCHAR(255),\r\n" + //
                        "    cliente_vendido VARCHAR(255),\r\n" + //
                        "    fecha_venta DATE NOT NULL,\r\n" + //
                        "    precio double NOT NULL,\r\n" + //
                        "    FOREIGN KEY (coche_vendido) REFERENCES COCHES(Matricula) ON DELETE NO ACTION ON UPDATE CASCADE,\r\n"
                        + //
                        "    FOREIGN KEY (cliente_vendido) REFERENCES CLIENTES(DNI) ON DELETE NO ACTION ON UPDATE CASCADE\r\n"
                        + //
                        ");")

        ) {
            preparedStatement.executeQuery();

        } catch (Exception e) {
            // TODO: handle exception
        }

        try (
                Connection con = DriverManager.getConnection(url, user, password);
                PreparedStatement preparedStatement = con.prepareStatement("CREATE TABLE REPARACIONES (\r\n" + //
                        "    id INT PRIMARY KEY AUTO_INCREMENT,\r\n" + //
                        "    descripcion VARCHAR(255)  NULL,\r\n" + //
                        "    costo double NOT NULL\r\n" + //
                        ");")

        ) {
            preparedStatement.executeQuery();

        } catch (Exception e) {
           
        }

        try (
                Connection con = DriverManager.getConnection(url, user, password);
                PreparedStatement preparedStatement = con.prepareStatement("CREATE TABLE COCHES_REPARACIONES (\r\n" + //
                        "    id INT PRIMARY KEY AUTO_INCREMENT,\r\n" + //
                        "    coche_reparado VARCHAR(255) NOT NULL,\r\n" + //
                        "    reparacion INT NOT NULL,\r\n" + //
                        "    FOREIGN KEY (coche_reparado) REFERENCES coches(matricula) ON DELETE CASCADE ON UPDATE CASCADE,\r\n"
                        + //
                        "    FOREIGN KEY (reparacion) REFERENCES reparaciones(id) ON DELETE CASCADE ON UPDATE CASCADE\r\n"
                        + //
                        ");")

        ) {
            preparedStatement.executeQuery();

        } catch (Exception e) {
            
        }

    }


   

}