package app.MetodosPagos;

import app.Interfaces.Pagable;

public class PagarTarjeta implements Pagable {

    @Override
    public void pagar(double cantidad) {
        System.out.println("Has pagado con tarjeta");
    }
    
}
