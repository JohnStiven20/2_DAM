package pizzeria.Modelo;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;


@Entity
public class PagarEfectivo extends Pagable {

    public PagarEfectivo() {

    }

    @Override
    public int pagar() {
        return 1;
    }

}
