package app;

import java.sql.SQLException;
import java.util.ArrayList;

import app.Controladores.ContraladorPedido;
import app.Controladores.ControladorCliente;
import app.Controladores.ControladorProducto;
import app.Enums.Size;
import app.Interfaces.Pagable;
import app.MetodosPagos.PagarTarjeta;
import app.Modelo.Cliente;
import app.Modelo.Ingrediente;
import app.Modelo.Pizza;
import app.Modelo.Producto;

public class MainFlujodeAplicacion {
    
    public static void main(String[] args) {

        ControladorCliente contraladorCliente = new ControladorCliente();
        ControladorProducto controladorProducto = new ControladorProducto();
        ContraladorPedido controladContraladorPedido = new ContraladorPedido();
      

        try {


            contraladorCliente.registerCustomer("48642965R", "Stiven", "602 381 142", "stivensolano.solano@gmail.com", "Travesía", "1234", false, "Solano");

            Cliente cliente = contraladorCliente.loginCustomer("stivensolano.solano@gmail.com");

            controladorProducto.catalogoProductos().forEach(x -> System.out.println(x));

            ArrayList<Ingrediente> lista = new ArrayList<>();

            lista.add(new Ingrediente("Tomate", null));

            controladorProducto.save(new Pizza("Macara", 789412, Size.GRANDE, lista));

            System.out.println("--------------------------------------------------------------------------");

            controladorProducto.catalogoProductos().forEach(x -> System.out.println(x));

            Producto producto = controladorProducto.catalogoProductos().stream().findFirst().get();

            controladContraladorPedido.addCarrito(producto, 10, cliente);

            Pagable metodo_pago = new PagarTarjeta();

            controladContraladorPedido.finalizarPedido(metodo_pago, cliente);


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    
        
        
    }
}
