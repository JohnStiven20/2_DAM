package app.MetodosPagos;

import app.Interfaces.Pagable;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
@Entity
@DiscriminatorValue(value="PagarEfectivo")
public class PagarEfectivo extends  Pagable{

    @Override
    public int pagar() {
        return 1;
    }

 


}
