package pizzeria;

import static pizzeria.utils.DatabaseConfig.CREATE_ALL_TABLES;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import pizzeria.Controller.ContraladorCliente;
import pizzeria.Controller.ContraladorPedido;
import pizzeria.Modelo.Bebida;
import pizzeria.Modelo.Cliente;
import pizzeria.Modelo.Ingredientes;
import pizzeria.Modelo.PagarEfectivo;
import pizzeria.Modelo.Pasta;
import pizzeria.Modelo.Pizza;
import pizzeria.Modelo.SizeApp;
import pizzeria.Modelo.Tipo;

public class main2 {

        public static void main(String[] args) {

                try {
                CREATE_ALL_TABLES();
                } catch (Exception e) {
                // TODO: handle exception
                }
                System.out.println(new Date().toString());
                System.out.println(Tipo.PIZZA.getValue());
                System.err.println(SizeApp.valueOf("GRANDE"));
        }
}
