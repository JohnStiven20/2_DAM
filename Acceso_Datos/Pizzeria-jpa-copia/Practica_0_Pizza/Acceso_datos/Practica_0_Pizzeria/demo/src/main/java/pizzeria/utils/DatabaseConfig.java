package pizzeria.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import pizzeria.Modelo.Conexion;

public class DatabaseConfig {

      public static final String INNER_CLIENTE = "INSERT INTO CLIENTE(dni , nombre , direccion , email , password , admin , telefono) VALUES (?,?,?,?,?,?,?)";
      public static final String INSERT_PRODUCTO = "INSERT INTO PRODUCTO(nombre, precio,tipo , size) VALUES (?,?,?,?)";
      public static final String INSERT_PRODUCTO_iNGREDIENTE = "INSERT INTO  producto_ingrediente (id_producto , id_ingrediente) VALUES (?,?)";
      public static final String INSERT_INGREDIENTE = "INSERT INTO INGREDIENTE (nombre) VALUES (?)";
      public static final String INSERT_INGREDIENTE_ALERGENO = "INSERT INTO  ingrediente_alergeno (id_ingrediente , id_alergeno) VALUES (?,?)";
      public static final String INSERT_ALERGENO = "INSERT INTO  ALERGENO (nombre) VALUES (?)";
      public static final String INSERT_LINEAPEDIDO = "INSERT INTO lineapedido(cantidad, id_producto, id_pedido) VALUES (?, ?, ?)";

      public static final String INSERT_PEDIDO = "INSERT INTO pedido(fecha, cliente_id, estadopedido, metodopago) VALUES (?, ?, ?, ?)";

      public static final String SELECT_INGREDIENTE_ALERGENO = "select  alergeno.id , alergeno.nombre from  ingrediente inner join  \n"
                  + //
                  "ingrediente_alergeno on  ingrediente.id = \n" + //
                  "ingrediente_alergeno.id_ingrediente \n" + //
                  "inner  join  alergeno on alergeno.id = ingrediente_alergeno.id_alergeno\n" + //
                  "where  ingrediente_alergeno.id_ingrediente = ?; ";

      public static final String SELECT_PRODUCTO_INGREDIENTE = " SELECT ingrediente.id, ingrediente.nombre\n" + //
                  "FROM ingrediente\n" + //
                  "JOIN producto_ingrediente ON ingrediente.id = producto_ingrediente.id_ingrediente\n" + //
                  "JOIN producto ON producto_ingrediente.id_producto = producto.id\n" + //
                  "WHERE producto_ingrediente.id_producto = ?;";

      public static final String SELECT_CLIENTE = "SELECT * FROM cliente WHERE email = ?";
      public static final String SELECT_ALERGENO_ID = "SELECT id , nombre FROM  alergeno where id = ?";
      public static final String SELECT_ALERGENO_NOMBRE = "SELECT id , nombre FROM  alergeno where nombre = ?";
      public static final String SELECT_INGREDIENTE_NOMBRE = "SELECT id , nombre FROM  ingrediente where nombre = ?";
      public static final String SELECT_ALL_PRODUCTO = "SELECT * from producto";
      public static final String SELECT_ALL_PRODUCTO_ID = "SELECT * from producto where id=?";
      public static final String SELECT_LOGIN_CLIENTE = "SELECT * FROM cliente WHERE nombre = ? AND password = ?";
      public static final String SELECT_ALL_CLIENTE = "SELECT id , dni , nombre , direccion , email , password , admin , telefono FROM cliente ";
      public static final String SELECT_ALL_PRODUCT = "SELECT id  , nombre , precio , tipo FROM producto";
      public static final String SELECT_LINEAPEDIDO_BY_ID = "SELECT lineapedido.* FROM PEDIDO  INNER JOIN  lineapedido ON pedido.id =  lineapedido.id_pedido  where lineapedido.id_pedido = ?";
      public static final String SELECT_PEDIDO_ESTADO = "select pedido.* , lineapedido.id_producto from pedido  join lineapedido  on pedido.id = lineapedido.id_pedido where pedido.estadopedido = ? and pedido.cliente_id = ?  ";
      public static final String SELECT_PEDIDO_ID = "SELECT DISTINCT pedido.*, lineapedido.*\n" + //
                  "FROM pedido\n" + //
                  "JOIN lineapedido ON pedido.id = lineapedido.id_pedido\n" + //
                  "WHERE pedido.cliente_id = ?";
      public static final String SELECT_INGREDIENTE_ALERGENO_EXIST = "select  * from  ingrediente_alergeno where ingrediente_alergeno.id_ingrediente = ? and  ingrediente_alergeno.id_alergeno = ? ;";
      public static final String SELECT_PRODUCTO_INGREDIENTE_EXIST = "select * from producto_ingrediente where id_producto = ? and id_ingrediente = ? ";
      public static final String SELECT_PRODUCTO_NAME_SIZE = "SELECT * FROM producto\n" + //
                  "WHERE producto.nombre = 'cococolo'\n" + //
                  "AND (\n" + //
                  "    producto.size = COALESCE(?, producto.size)\n" + //
                  "    OR (? IS NULL AND producto.size IS NULL)\n" + //
                  ");";

      public static final String UPDATE_CLIENTE = "UPDATE cliente SET nombre = ? , direccion = ?  , password = ? , admin = ? , telefono = ?  where id= ?";
      public static final String UPDATE_PEDIDO_ESTADO_AND_PAGO = "UPDATE pedido SET estadopedido = ? , metodopago = ?  where id = ? ";
      public static final String UPDATE_PEDIDO_ESTADO = "UPDATE pedido SET estadopedido = ?  where id = ? ";
      public static final String UPDATE_PRODUCTO = "UPDATE producto SET nombre = ? , precio = ? , tipo = ? , size = ?  where id = ?";
      public static final String UPDATE_PEDIDO = "UPDATE pedido SET fecha = ?, estadopedido = ?, cliente_id = ?, metodopago = ? WHERE id = ?";

      static final String DROP_TABLE_CLIENTE = "DROP TABLE IF EXISTS cliente";
      static final String DROP_INGREDIENTE_ALERGENO = "DROP TABLE IF EXISTS ingrediente_alergeno";
      static final String DROP_PRODUCTO = "DROP TABLE IF EXISTS producto";
      static final String DROP_INGREDIENTE = "DROP TABLE IF EXISTS ingrediente";
      static final String DROP_PRODUCTO_INGREDIENTE = "DROP TABLE IF EXISTS producto_ingrediente";
      static final String DROP_ALERGENO = "DROP TABLE IF EXISTS alergeno";
      static final String DROP_PEDIDO = "DROP TABLE IF EXISTS pedido";
      static final String DROP_LINEAPEDIDO = "DROP TABLE IF EXISTS LineaPedido";

      public static final String DELETE_PRODUCTO = "DELETE FROM producto WHERE id = ?";
      public static final String DELETE_CLIENTE = "DELETE FROM cliente WHERE id = ?";
      public static final String DELETE_PEDIDO = "DELETE FROM pedido WHERE id = ?";

      public static final String CREATE_TABLE_CLIENTE = " CREATE TABLE cliente (\r\n" + //
                  "          id INT NOT NULL AUTO_INCREMENT,\r\n" + //
                  "          dni VARCHAR(255) DEFAULT NULL,\r\n" + //
                  "          nombre VARCHAR(255) DEFAULT NULL,\r\n" + //
                  "          direccion VARCHAR(255) DEFAULT NULL,\r\n" + //
                  "          email VARCHAR(255) DEFAULT NULL,\r\n" + //
                  "          password VARCHAR(255) DEFAULT NULL,\r\n" + //
                  "          admin BIT(1) DEFAULT NULL,\r\n" + //
                  "          telefono VARCHAR(255) DEFAULT NULL,\r\n" + //
                  "          PRIMARY KEY (id),\r\n" + //
                  "          UNIQUE KEY dni (dni),\r\n" + //
                  "          UNIQUE KEY email (email)\r\n" + //
                  "      ) ";

      public static final String CREATE_TABLE_PRODUCTO = " CREATE TABLE producto (\n" + //
                  "  id INT NOT NULL AUTO_INCREMENT,\n" + //
                  "  nombre VARCHAR(255) DEFAULT NULL,\n" + //
                  "  precio DOUBLE DEFAULT NULL,\n" + //
                  "  tipo ENUM('pizza', 'pasta', 'bebida') DEFAULT NULL,\n" + //
                  "  size ENUM('mediano', 'pequeno', 'grande') DEFAULT NULL,\n" + //
                  "  PRIMARY KEY (id)\n" + //
                  ")";

      public static final String CREATE_TABLE_INGREDIENTE = " CREATE TABLE ingrediente (\r\n" + //
                  "  ID INT NOT NULL AUTO_INCREMENT,\r\n" + //
                  "  nombre VARCHAR(255) DEFAULT NULL,\r\n" + //
                  "  PRIMARY KEY (ID)\r\n" + //
                  ");\r\n" + //
                  " ";

      public static final String CREATE_TABLE_PRODUCTO_INGREDIENTE = "CREATE TABLE producto_ingrediente (\r\n" + //
                  "  id INT NOT NULL AUTO_INCREMENT,\r\n" + //
                  "  id_producto INT NOT NULL,\r\n" + //
                  "  id_ingrediente INT NOT NULL,\r\n" + //
                  "  PRIMARY KEY (id),\r\n" + //
                  "  KEY producto_ingrediente_ibfk_2 (id_ingrediente),\r\n" + //
                  "  CONSTRAINT producto_ingrediente_ibfk_1 FOREIGN KEY (id_producto) REFERENCES producto (id) ON DELETE CASCADE,\r\n"
                  + //
                  "  CONSTRAINT producto_ingrediente_ibfk_2 FOREIGN KEY (id_ingrediente) REFERENCES ingrediente (ID) ON DELETE CASCADE\r\n"
                  + //
                  ");\r\n" + //
                  "";

      public static final String CREATE_TABLE_ALERGENO = "CREATE TABLE alergeno (\n" + //
                  "  ID INT NOT NULL AUTO_INCREMENT,\n" + //
                  "  nombre VARCHAR(255) DEFAULT NULL,\n" + //
                  "  PRIMARY KEY (ID)\n" + //
                  ");";

      public static final String CREATE_TABLE_INGREDIENTE_ALERGENO = "CREATE TABLE ingrediente_alergeno (\n" + //
                  "    id INT NOT NULL AUTO_INCREMENT,\n" + //
                  "    id_ingrediente INT NOT NULL,\n" + //
                  "    id_alergeno INT NOT NULL,\n" + //
                  "    PRIMARY KEY (id),\n" + //
                  "    UNIQUE KEY (id_ingrediente, id_alergeno),\n" + //
                  "    KEY ingrediente_alergeno_ibfk_2 (id_alergeno),\n" + //
                  "    CONSTRAINT ingrediente_alergeno_ibfk_1 FOREIGN KEY (id_ingrediente) REFERENCES ingrediente (ID) ON DELETE CASCADE,\n"
                  + //
                  "    CONSTRAINT ingrediente_alergeno_ibfk_2 FOREIGN KEY (id_alergeno) REFERENCES alergeno (ID) ON DELETE CASCADE\n"
                  + //
                  ");";

      public static final String CREATE_TABLE_LINEAPEDIDO = " CREATE TABLE LineaPedido (\n" + //
                  "    id INT NOT NULL AUTO_INCREMENT,\n" + //
                  "    cantidad INT NOT NULL,\n" + //
                  "    id_producto INT NOT NULL,\n" + //
                  "    id_pedido INT NOT NULL,\n" + //
                  "    PRIMARY KEY (id),\n" + //
                  "    CONSTRAINT fk_lineapedido_producto FOREIGN KEY (id_producto) REFERENCES Producto(id) ON DELETE CASCADE,\n"
                  + //
                  "    CONSTRAINT fk_lineapedido_pedido FOREIGN KEY (id_pedido) REFERENCES Pedido(id) ON DELETE CASCADE\n"
                  + //
                  ");";

      public static final String CREATE_TABLE_PEDIDO = " CREATE TABLE Pedido (\n" + //
                  "    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" + //
                  "    fecha DATE,\n" +
                  "    estadopedido ENUM('ENTREGADO', 'PENDIENTE', 'CANCELADO'),\n" +
                  "    cliente_id INT,\n" + //
                  "    FOREIGN KEY (cliente_id) REFERENCES Cliente(id),\n" + //
                  "    metodopago INT NULL " +
                  ");";

      public static void CREATE_ALL_TABLES() throws ClassNotFoundException, SQLException {
            try (Connection con = new Conexion().getConexion();
                        Statement stmt = con.createStatement()) {

                  stmt.execute("SET FOREIGN_KEY_CHECKS = 0");

                  stmt.execute(DROP_INGREDIENTE_ALERGENO);
                  stmt.execute(DROP_PRODUCTO_INGREDIENTE);
                  stmt.execute(DROP_LINEAPEDIDO);
                  stmt.execute(DROP_ALERGENO);
                  stmt.execute(DROP_INGREDIENTE);
                  stmt.execute(DROP_PRODUCTO);
                  stmt.execute(DROP_TABLE_CLIENTE);
                  stmt.execute(DROP_PEDIDO);

                  stmt.execute(CREATE_TABLE_CLIENTE);
                  stmt.execute(CREATE_TABLE_PRODUCTO);
                  stmt.execute(CREATE_TABLE_LINEAPEDIDO);
                  stmt.execute(CREATE_TABLE_INGREDIENTE);
                  stmt.execute(CREATE_TABLE_ALERGENO);
                  stmt.execute(CREATE_TABLE_PRODUCTO_INGREDIENTE);
                  stmt.execute(CREATE_TABLE_INGREDIENTE_ALERGENO);
                  stmt.execute(CREATE_TABLE_PEDIDO);

                  stmt.execute("SET FOREIGN_KEY_CHECKS = 1");

                  System.out.println("Tablas creadas correctamente.");
            } catch (SQLException e) {
                  e.printStackTrace();
                  throw e;
            }
      }

}
