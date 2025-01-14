package pizzeria.Modelo;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;


@Entity
public class PagarTarjeta extends Pagable {
 
 
     public PagarTarjeta(){
     
     }
     
    @Override
    public int pagar() {
        return 2;
    }

}
