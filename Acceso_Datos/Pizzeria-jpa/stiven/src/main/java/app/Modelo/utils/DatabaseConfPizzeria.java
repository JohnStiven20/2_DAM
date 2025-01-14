// package app.Modelo.utils;

// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.SQLDataException;
// import java.sql.SQLException;
// import java.sql.Statement;
// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.List;

// import app.Controladores.ControladorCliente;
// import app.Controladores.ControladorProducto;
// import app.Enums.Size;
// import app.Modelo.Bebida;
// import app.Modelo.Cliente;
// import app.Modelo.Ingrediente;
// import app.Modelo.Pasta;
// import app.Modelo.Pizza;
// import app.Modelo.Producto;

// public class DatabaseConfPizzeria {

//     public static final String URL = "jdbc:mysql://localhost:3306/pizzeria";
//     public static final String USER = "root";
//     public static final String PASS = "admin";

//     public static final String DROP_TABLE_CLIENTE = "DROP TABLE IF EXISTS CLIENTE";
//     public static final String DROP_TABLE_PRODUCTO = "DROP TABLE IF EXISTS PRODUCTO";
//     public static final String DROP_TABLE_ALOGENO = "DROP TABLE IF EXISTS ALOGENO";
//     public static final String DROP_TABLE_INGREDIENTE = "DROP TABLE IF EXISTS INGREDIENTE";
//     public static final String DROP_TABLE_INGREDIENTE_ALOGENO = "DROP TABLE IF EXISTS INGREDIENTE_ALOGENO";
//     public static final String DROP_TABLE_PRODUCTO_INGREDIENTE = "DROP TABLE IF EXISTS PRODUCTO_INGREDIENTE";
//     public static final String DROP_TABLE_PEDIDO = "DROP TABLE IF EXISTS PEDIDO";
//     public static final String DROP_TABLE_LINEA_PEDIDO = "DROP TABLE IF EXISTS LINEA_PEDIDO";

//     /*
//      * 
//      *               LOS CAMBIOS DE LOS UPDATE PARA CAMBIAR LOS VALORES 
//      */


//     public static final String UPDATE_CLIENTE = "UPDATE cliente SET  cliente.direccion = ? , cliente.apellidos = ?, cliente.telefono = ?"
//     + "WHERE cliente.id = ?";

//     public static final String UPDATE_PEDIDO_ENTREGADO = "UPDATE pedido SET pedido.estado_pedido = ?, pedido.metodo_pago = ? "
//     + " WHERE pedido.id = ?";

//     public static final String UPDATE_PEDIDO_CANCELAR = "UPDATE pedido SET pedido.estado_pedido = ? "
//     + " WHERE pedido.id = ?";

//     public static final String UPDATE_PRODUCTO = "UPDATE producto SET producto.size = ?";


//     /*
//      * 
//      *    SELECT DIRIGIDO MEDIANTE ID Y NOMBRE
//      */

//      public static final String SELECT_INGREDIENTE_MEDIANTE_ID = "SELECT ingrediente.nombre"
//      + " FROM ingrediente "
//      + " WHERE ingrediente.id = ?";

//      public static final String SELECT_INGREDIENTE_MEDIANTE_NOMBRE = "SELECT ingrediente.id"
//      + " FROM ingrediente "
//      + " WHERE ingrediente.nombre = ?";

//      public  static final String SELECT_ALOGENO_MEDIANTE_ID = "SELECT alogeno.nombre "
//      + " FROM alogeno "
//      + " WHERE alogeno.id = ?";

//      public  static final String SELECT_ALOGENO_MEDIANTE_NOMBRE = "SELECT alogeno.id "
//      + " FROM alogeno "
//      + " WHERE alogeno.nombre = ?";

//      public static final String SELECT_PRODUCTO_MEDIANTE_NOMBRE_Y_SIZE = "SELECT id "
//      + " FROM producto "
//      + " WHERE producto.nombre = ? && producto.size = ?";

//      public  static final String SELECT_PEDIDO_MEDIANTE_ESTADO_PEDIDO = "SELECT pedido.id, pedido.estado_pedido, pedido.fecha "
//      + " FROM pedido "
//      + " WHERE pedido.estado_pedido = ? ";

//      public static final String SELECT_PEDIDO_MEDIANTE_CLIENTE_ID = "SELECT pedido.id, pedido.estado_pedido, pedido.fecha "
//      + "FROM cliente "
//      + "JOIN pedido ON pedido.cliente_id = cliente.id "
//      + "WHERE cliente.id = ? ";

//      public  static final String SELECT_PEDIDO_MEDIANTE_ESTADO_PEDIDO_NUEVO = "SELECT * "
//      + " FROM pedido "
//      + " JOIN cliente ON pedido.cliente_id = cliente.id"
//      + " WHERE pedido.estado_pedido = ? && cliente.id = ? ";

//      /*
//       * 
//       *
//       *
//       */

//     public static final String SELECT_CLIENTE = "SELECT cliente.id, cliente.dni , cliente.nombre, cliente.apellidos, cliente.direccion, cliente.password, cliente.admin, cliente.email" 
//     + " FROM cliente"
//     + " WHERE cliente.email = ?" ;

//     public static  final String SELECT_JOIN_INGREDIENTE_ALOGENO = "SELECT alogeno.nombre "
//     + " FROM alogeno "
//     + " JOIN ingrediente_alogeno ON ingrediente_alogeno.alogeno_id = alogeno.id "
//     + " JOIN ingrediente ON ingrediente_alogeno.ingrediente_id = ingrediente.id "
//     + " WHERE ingrediente.id = ?";

//     public static final String SELECT_JOIN_INGREDIENTE_ALOGENO_POR_ID = "SELECT ingrediente_alogeno.id "
//     + " FROM alogeno "
//     + " JOIN ingrediente_alogeno ON ingrediente_alogeno.alogeno_id = alogeno.id "
//     + " JOIN ingrediente ON ingrediente_alogeno.ingrediente_id = ingrediente.id "
//     + " WHERE ingrediente.id = ? AND alogeno.id = ?";

//     public  static final String SELECT_JOIN_PRODUCTO_INGREDIENTE_POR_ID = "SELECT ingrediente.id, ingrediente.nombre"
//     + " FROM producto "
//     + " JOIN producto_ingrediente ON producto.id = producto_ingrediente.producto_id "
//     + " JOIN ingrediente ON producto_ingrediente.ingrediente_id = ingrediente.id "
//     + " WHERE producto.id = ?";

//     public static final String SELECT_JOIN_LINEA_PEDIDO_POR_PEDIDO_ID = "SELECT linea_pedido.id , linea_pedido.cantidad ,producto.id, producto.nombre, producto.precio, producto.size, producto.tipo"
//     + " FROM pedido "
//     + " JOIN linea_pedido ON linea_pedido.pedido_id = pedido.id "
//     + " JOIN producto ON producto.id = linea_pedido.producto_id "
//     + " WHERE pedido.id = ?";

//     /**
//      * 
//      *               SELENCIONAR TODOS LOS VALORES DE LAS TABLAS CORRESPONDIENTES PARA CADA UNO
//      * 
//      */
    

//     public static final String SELECT_CLIENTE_ALL = "SELECT cliente.id, cliente.dni , cliente.nombre, cliente.apellidos, cliente.direccion, cliente.password, cliente.admin, cliente.email, cliente.telefono" 
//     + " FROM cliente";
//     public  static final String SELECT_PRODUCTO_ALL = "SELECT producto.id, producto.nombre, producto.precio, producto.size, producto.tipo"
//     + " FROM producto";
//     public  static final String SELECT_INGREDIENTE_ALL = "";

//     public static final String SELECT_ALOGENO_ALL = "";

//     public  static final String SELECT_LINEA_PEDIDO = "SELECT id, cantidad, producto_id, pedido_id "
//     + " FROM linea_pedido "
//     + " WHERE linea_pedido.pedido_id = ? ";



//     /**
//      *               CREACION DE LA TABLAS DE LA BASE DE DATOS  
//      * 
//      */

//     public static final String CREATE_TABLE_CLIENTE = "CREATE TABLE IF NOT EXISTS CLIENTE ("
//             + "  id INT AUTO_INCREMENT PRIMARY KEY, "
//             + "  dni VARCHAR (255) NOT NULL, "
//             + "  nombre VARCHAR(255) NOT NULL, "
//             + "  telefono VARCHAR (255) NULL, "
//             + "  apellidos VARCHAR(255) NOT NULL, "
//             + "  direccion VARCHAR(255) NULL, "
//             + "  password VARCHAR (255) NOT NULL, "
//             + "  admin BOOLEAN DEFAULT FALSE, "
//             + "  email VARCHAR(255) NOT NULL" 
//             + ")";

//     public static final String CREATE_TABLE_PRODUCTO = "CREATE TABLE IF NOT EXISTS PRODUCTO ("
//     + "  id INT AUTO_INCREMENT PRIMARY KEY, " 
//     + "  nombre VARCHAR(255) NOT NULL, "
//     + "  precio DECIMAL (10, 2), "
//     + "  size ENUM(\"ENANO\", \"MEDIANO\", \"GRANDE\") DEFAULT NULL, "
//     + "  tipo ENUM(\"PIZZA\", \"PASTA\", \"BEBIDA\") "
//     + ")";

//     public  static final String CREATE_TABLE_ALOGENO = "CREATE TABLE IF NOT EXISTS ALOGENO ("
//     + " id INT AUTO_INCREMENT PRIMARY KEY, "
//     + " nombre VARCHAR (255) " 
//     + ")";

//     public  static final String CREATE_TABLE_INGREDIENTE = "CREATE TABLE IF NOT EXISTS INGREDIENTE ( "
//     + " id INT AUTO_INCREMENT PRIMARY KEY, "
//     + " nombre VARCHAR (255) NULL"
//     + " )";
    

//     public static final String CREATE_TABLE_INGREDIENTE_ALOGENO = "CREATE TABLE IF NOT EXISTS INGREDIENTE_ALOGENO ( "
//     + " id INT AUTO_INCREMENT PRIMARY KEY," 
//     + " ingrediente_id INT NOT NULL," 
//     + " alogeno_id INT NOT NULL, "
//     + " CONSTRAINT unique_ingrediente_alogeno UNIQUE (ingrediente_id, alogeno_id),"
//     + " FOREIGN KEY (ingrediente_id) REFERENCES INGREDIENTE(id) ON DELETE CASCADE, "
//     + " FOREIGN KEY (alogeno_id) REFERENCES ALOGENO(id) ON DELETE CASCADE"
//     + " )";

//     public  static final String CREATE_TABLE_PRODUCTO_INGREDIENTE = "CREATE TABLE IF NOT EXISTS PRODUCTO_INGREDIENTE ( "
//     + " id INT AUTO_INCREMENT PRIMARY KEY, "
//     + " producto_id INT NOT NULL, "
//     + " ingrediente_id INT NOT NULL, "
//     + " FOREIGN KEY (producto_id) REFERENCES PRODUCTO(id) ON DELETE CASCADE, "
//     + " FOREIGN KEY (ingrediente_id) REFERENCES INGREDIENTE(id) ON DELETE CASCADE"
//     + ")";

//     public  static final String CREATE_TABLE_LINEA_PEDIDO = "CREATE TABLE IF NOT EXISTS LINEA_PEDIDO ( "
//     + " id INT AUTO_INCREMENT PRIMARY KEY, "
//     + " cantidad INT, "
//     + " pedido_id INT, "
//     + " producto_id INT, "
//     + " FOREIGN KEY (pedido_id) REFERENCES pedido (id) ON DELETE CASCADE, "
//     + " FOREIGN KEY (producto_id) REFERENCES producto (id) ON DELETE CASCADE"
//     + " )";

//     public static final String CREATE_TABLE_PEDIDO = "CREATE TABLE IF NOT EXISTS PEDIDO ( "
//     + " id INT AUTO_INCREMENT PRIMARY KEY, "
//     + " fecha DATE, "
//     + " estado_pedido ENUM(\"PEDIENTE\", \"ENTREGADO\", \"CANCELADO\"), "
//     + " cliente_id INT, "
//     + " metodo_pago ENUM(\"EFECTIVO\", \"TARJETA\"), "
//     + " FOREIGN KEY (cliente_id) REFERENCES cliente (id) ON DELETE CASCADE"
//     + " )";

//     /*
//      * 
//      *                 INSERCIONES DE LOS OBJETOS EN LA BASE DE DATOS
//      * 
//      */

//     public static final String INSERT_CLIENTE = "INSERT INTO CLIENTE (dni, nombre, telefono, apellidos, direccion, password, admin, email) VALUES (?, ?, ?, ?, ? , ? , ?, ?)";
//     public  static final String INSERT_PRODUCTO = "INSERT INTO PRODUCTO (nombre, precio, size, tipo)  VALUES (?,?,?, ?)";      
//     public  static final String INSERT_ALOGENO = "INSERT INTO ALOGENO (nombre) VALUES (?)"; 
//     public  static final String INSERT_INGREDIENTE = "INSERT INTO INGREDIENTE (nombre) VALUES (?)";
//     public  static final String INSERT_INGREDIENTE_ALOGENO = "INSERT INTO INGREDIENTE_ALOGENO (ingrediente_id, alogeno_id) VALUES (?,?)";
//     public  static final String INSERT_PRODUCTO_INGREDIENTE = "INSERT INTO PRODUCTO_INGREDIENTE (producto_id, ingrediente_id) VALUES (?,?)";
//     public static final String INSERT_PEDIDO = "INSERT INTO PEDIDO (fecha, estado_pedido, cliente_id) VALUES (?,?,?)";
//     public  static final String INSERT_LINEA_PEDIDO = "INSERT INTO LINEA_PEDIDO (cantidad, pedido_id, producto_id) VALUES (?,?,?)";


//     /**
//      * 
//      *             BORRADA LOS OBJETOS CONCRETOS MEDIANTE ID
//      */


//     public static final String DELETE_CLIENTE = "DELETE " 
//     + " FROM cliente "
//     + " WHERE cliente.id = ?";     
    
//     public static final String DELETE_PRODUCTO = "DELETE "
//     + " FROM producto "
//     + " WHERE producto.id = ?";
//     public static final String DELETE_PEDIDO = "DELETE FROM pedido WHERE id = ?";




//     /**
//      * 
//      * @throws SQLException
//      * 
//      *            LAS TABLAS PARA CREAR LA BASE DE DATOS Y TAMBIEN PARA CREARLAS
//      */


//     public static void createTable() throws SQLException {

//         try (Connection connection = getConnection()) {
//             Statement statement = connection.createStatement();
//             statement.execute(CREATE_TABLE_CLIENTE);
//             statement.execute(CREATE_TABLE_PRODUCTO);
//             statement.execute(CREATE_TABLE_PEDIDO);
//             statement.execute(CREATE_TABLE_LINEA_PEDIDO);
//             statement.execute(CREATE_TABLE_INGREDIENTE);
//             statement.execute(CREATE_TABLE_PRODUCTO_INGREDIENTE);
//             statement.execute(CREATE_TABLE_ALOGENO);
//             statement.execute(CREATE_TABLE_INGREDIENTE_ALOGENO);
//             System.out.println("Se ha creado la tablas 6");
//         }
//     }

//     public static void dropTables() throws SQLException {

//         try (Connection connection = getConnection()) {
//             Statement statement = connection.createStatement();
//             statement.execute("SET FOREIGN_KEY_CHECKS = 0");
//             statement.execute(DROP_TABLE_LINEA_PEDIDO);
//             statement.execute(DROP_TABLE_PEDIDO);
//             statement.execute(DROP_TABLE_CLIENTE);
//             statement.execute(DROP_TABLE_PRODUCTO);
//             statement.execute(DROP_TABLE_PRODUCTO_INGREDIENTE);
//             statement.execute(DROP_TABLE_ALOGENO);
//             statement.execute(DROP_TABLE_INGREDIENTE);
//             statement.execute(DROP_TABLE_INGREDIENTE_ALOGENO);
//             System.out.println("Se ha borrado la tabla cliente y producto");
//         }

//     }


//     public static  Connection getConnection() throws SQLException {
//         return DriverManager.getConnection(URL, USER, PASS);
//     }

//      public static void  cargarDatosEnLaBaseDatos() throws  SQLDataException {

//         try {

    
//                 ControladorCliente controladorCliente = new ControladorCliente();
//                 ControladorProducto controladorProducto = new ControladorProducto();
    
//                 ArrayList<Cliente> lista = new ArrayList<>();
                
    
    
//                 lista.add(new Cliente("48642965R", "Stiven", "602 381 142", "stiven.solano@gmail.com", "Travesía", "1234",null, true, "Solano"));
//                 lista.add(new Cliente("29854713P", "Lucía", "654 123 987", "lucia.martinez@gmail.com", "Avenida Central","5678", null, true, "Martínez"));
//                 lista.add(new Cliente("73985624T", "Carlos", "698 321 456", "carlos.perez@gmail.com", "Calle Luna", "9012", null, false, "Pérez"));
//                 lista.add(new Cliente("12369874L", "María", "611 456 789", "maria.garcia@gmail.com", "Plaza Mayor", "3456", null, true, "García"));
//                 lista.add(new Cliente("45612378D", "Ana", "622 987 654", "ana.lopez@gmail.com", "Camino Real", "7890", null,true, "López"));
//                 lista.add(new Cliente("87459623K", "Jorge", "633 258 147", "jorge.ramirez@gmail.com", "Carrera Norte","1123", null, false, "Ramírez"));
//                 lista.add(new Cliente("59874123M", "Elena", "644 369 852", "elena.sanchez@gmail.com", "Callejón Sur",
//                         "4456", null, true, "Sánchez"));
//                 lista.add(new Cliente("78912365Z", "Miguel", "655 147 258", "miguel.diaz@gmail.com", "Boulevard Este",
//                         "7789", null, false, "Díaz"));
//                 lista.add(new Cliente("14785236H", "Isabel", "666 321 987", "isabel.castro@gmail.com", "Paseo del Río",
//                         "1230", null, true, "Castro"));
//                 lista.add(new Cliente("96385247J", "Alberto", "677 852 963", "alberto.moreno@gmail.com", "Ronda Oeste",
//                         "4567", null, true, "Moreno"));
    
//                 ArrayList<Ingrediente> listaIngredietes = new ArrayList<>();
//                 ArrayList<Ingrediente> listaIngredietes1 = new ArrayList<>();
    
//                 ArrayList<Producto> listaProductos = new ArrayList<>();
//                 ArrayList<Producto> listaProductos1 = new ArrayList<>();
    
//                 List<String> alergenos = Arrays.asList(
//                         "Lactosa",
//                         "Mostaza",
//                         "Sésamo",
//                         "Sulfitos",
//                         "Gluten");
    
//                 List<String> otrosAlergenos = Arrays.asList(
//                         "Lactitol",
//                         "Trazas de frutos secos",
//                         "Pescado",
//                         "Apio",
//                         "Lactosa",
//                         "Crustáceos",
//                         "Moluscos",
//                         "Dióxido de azufre",
//                         "Lactitol",
//                         "Proteína de leche",
//                         "Gluten");
    
//                 List<String> alergenosUnicos = Arrays.asList(
//                         "Apio",
//                         "Dióxido de azufre",
//                         "Sésamo",
//                         "Lactitol",
//                         "Trazas de frutos secos",
//                         "Pescado",
//                         "Sulfitos",
//                         "Moluscos",
//                         "Lactosa",
//                         "Proteína de leche",
//                         "Mostaza",
//                         "Crustáceos",
//                         "Gluten");
    
//                 int suma1 = alergenos.size();
//                 int suma2 = otrosAlergenos.size();
//                 int suma3 = alergenosUnicos.size();
//                 System.out.println(suma1 + suma2 + suma3);
    
//                 listaIngredietes.add(new Ingrediente("Tomate", alergenos));
//                 listaIngredietes.add(new Ingrediente("Cebolla", otrosAlergenos));
//                 listaIngredietes.add(new Ingrediente("Patata", alergenos));
//                 listaIngredietes.add(new Ingrediente("Pimiento", otrosAlergenos));
//                 listaIngredietes1.add(new Ingrediente("Tarta", alergenosUnicos));
    
    
//                 Pasta producto2 = new Pasta("Macarrones", 80, listaIngredietes);
//                 Bebida bebida = new Bebida("Coca cola", 70, Size.GRANDE);
//                 Pizza pizza = new Pizza("Cuatro quesos", 7, Size.MEDIANO, listaIngredietes);
//                 Pizza pizza1 = new Pizza("La Pizza Nova", 90, Size.ENANO, listaIngredietes1);
    
//                 listaProductos.add(pizza1);
//                 listaProductos.add(producto2);
//                 listaProductos.add(bebida);
//                 listaProductos.add(pizza);
    
//                 listaProductos1.add(new Pizza("Bebida", 14, Size.GRANDE, listaIngredietes1));
    
                
    
    
//                 for (Producto product : listaProductos) {
//                     controladorProducto.save(product);
//                 }
    
//                 for (Producto elem : listaProductos1) {
//                     controladorProducto.save(elem);
//                 }
    
//                 for (Cliente cliente : lista) {
//                     controladorCliente.save(cliente);
//                 }
    
    
//             } catch (SQLException e) {
//                 System.out.println(e.getMessage());
//             }

//     }
// }
