package pizzeria.Modelo;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

// import pizzeria.Modelo.enums.SizeApp;

@Entity
@DiscriminatorValue("BEBIDA")
public class Bebida extends Producto {

    @Enumerated(EnumType.STRING)
    private SizeApp size;

    public Bebida() {
        super();
    }

    public Bebida(int id, String nombre, double precio, SizeApp size) {
        super(id, nombre, precio);
        this.size = size;

    }

    public Bebida(String nombre, double precio, SizeApp size) {
        super(nombre, precio);
        this.size = size;
    }

    public SizeApp getSize() {
        return size;
    }

    public void setSize(SizeApp size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Bebida [id=" + id + ", size=" + size + ", nombre=" + nombre + ", precio=" + precio + "]";
    }

}
