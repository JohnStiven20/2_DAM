package pizzeria.Modelo;

import java.util.List;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("PASTA")
public class Pasta extends Producto {


    public Pasta(){
        super(); 
    }

    public Pasta(int id, String nombre, double precio, List<Ingredientes> ingredientes) {
        super(id, nombre, precio, ingredientes);
    }

    public Pasta(String nombre, double precio, List<Ingredientes> ingredientes) {
        super(nombre, precio, ingredientes);
    }

    @Override
    public String toString() {
        return "Pasta [id=" + id + ", nombre=" + nombre + ", precio=" + precio + ", ingredientes=" + ingredientes + "]";
    }

    public List<Ingredientes> getIngredientes() {
        return ingredientes;
    }

}
