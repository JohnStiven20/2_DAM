package app.Modelo;

import java.util.List;

import app.Enums.Size;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue(value="Pizza")
public class Pizza  extends Producto{

    private Size size;

    public Pizza(String nombre, double precio, Size size, List<Ingrediente> productos) {
        super(nombre, precio, size, productos);
    }

    public Pizza(int id, String nombre, double precio, Size size, List<Ingrediente> productos) {
        super(id, nombre, precio, size, productos);
    }

    public Pizza() {
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
        return super.toString();
    }

   
}