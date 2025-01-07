package app;

import java.sql.SQLException;

import app.Controladores.dao.impl.JbcProductoDao;

public class MainProductos {
    public static void main(String[] args) {
        

        JbcProductoDao jbcProductoDao = new JbcProductoDao();

        try {
            jbcProductoDao.getAllProducts().forEach(x -> {

                System.out.println("---------------------------------------------------------------------------");
                System.out.println(x);
                System.out.println("---------------------------------------------------------------------------");

            });
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
