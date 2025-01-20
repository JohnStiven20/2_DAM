package app.MetodosPagos;

import app.Interfaces.Pagable;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
@Entity
@DiscriminatorValue(value="PagarTarjeta")
public class PagarTarjeta extends  Pagable {

    @Override
    public int pagar() {
        return 2;
    }

}
