package app.MetodosPagos;

import app.Interfaces.Pagable;

public class PagarEfectivo implements Pagable{

    @Override
    public void pagar(double cantidad) {
        System.out.println("Has pagado en efectivo");
    }
    
}
