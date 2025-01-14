package pizzeria.Modelo;

import java.util.List;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
@DiscriminatorValue("PIZZA")
public class Pizza extends Producto {

    @Enumerated(EnumType.STRING)
    private SizeApp size;

    Pizza() {
        super();
    }


    public Pizza(int id, String nombre, double precio, SizeApp size, List<Ingredientes> ingredientes) {
        super(id, nombre, precio, ingredientes);
        this.size = size;
    }

    public Pizza(String nombre, double precio, SizeApp size, List<Ingredientes> ingredientes) {
        super(nombre, precio, ingredientes);
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
        return "Pizza [id=" + id + ", size=" + size + ", nombre=" + nombre + ", precio=" + precio + " ," + ingredientes
                + " + ingredientes ]";
    }

    public List<Ingredientes> getIngredientes() {
        return ingredientes;
    }

}
