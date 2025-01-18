package app.Modelo;

import app.Enums.Size;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue(value = "Bebida")
public class Bebida extends Producto {


    public Bebida(String nombre, double precio, Size size) {
        super(nombre, precio, size);
        
    }

    public Bebida(int id, String nombre, double precio, Size size) {
        super(id, nombre, precio, size);
    }

    public Bebida() {
    }
}
