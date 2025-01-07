package app;

import java.sql.SQLDataException;
import java.sql.SQLException;

import app.Modelo.utils.DatabaseConfPizzeria;

public class MainPizzeria {


   public static void main(String[] args) throws SQLException {
    
       
     try {

        DatabaseConfPizzeria.dropTables();
        DatabaseConfPizzeria.createTable();
        DatabaseConfPizzeria.cargarDatosEnLaBaseDatos();
        
    } catch (SQLDataException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
   }
}
