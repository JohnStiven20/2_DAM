package app.Modelo;

import app.Enums.Size;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue(value = "bebida")
public class Bebida extends Producto {

    private Size size;

    public Bebida(String nombre, double precio, Size size) {
        super(nombre, precio);
        this.size = size;
    }

    public Bebida(int id, String nombre, double precio, Size size) {
        super(id, nombre, precio);
        this.size = size;
    }

    public Bebida() {
    }

    @Override
    public Size getSize() {
        return size;
    }

    @Override
    public void setSize(Size size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return super.toString() + " Bebida [size=" + size + "]";
    }

}
