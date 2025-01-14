package pizzeria;

import java.sql.SQLException;

import pizzeria.Controller.ContraladorCliente;
import pizzeria.Exceptions.ExceptionFoundCliente;
import pizzeria.Modelo.Cliente;

public class mainJdbc {
    public static void main(String[] args) {

        try {
            ContraladorCliente contraladorCliente = new ContraladorCliente();
            String dni = "48642965N";
            String nombre = "Jhon";
            String apellido = "Solano Macas";
            String telefono = "681207536";
            String email = "solanomacascristofer@gmail.com";
            String password = "12345";
            Boolean admin = false;
            Cliente cliente = new Cliente(dni, nombre, apellido, telefono, email, password, admin);
            contraladorCliente.registrarCliente(cliente);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ExceptionFoundCliente e) {
            e.printStackTrace();
        }

        

    }
}
