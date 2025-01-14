package app.Modelo;

import java.util.List;

import app.Enums.Size;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
@Entity
@DiscriminatorValue(value="Pasta")
public class Pasta extends Producto{

    public Pasta() {
    }

    public Pasta(int id, String nombre, double precio, Size size, List<Ingrediente> productos) {
        super(id, nombre, precio, size, productos);
    }

    public Pasta(String nombre, double precio, Size size, List<Ingrediente> productos) {
        super(nombre, precio, size, productos);
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
